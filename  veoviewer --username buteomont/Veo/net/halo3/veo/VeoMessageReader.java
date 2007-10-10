/*
 * VeoMessageReader.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import net.halo3.veo.messages.InputData;
import net.halo3.veo.messages.InvalidResponseException;
import net.halo3.veo.messages.Response;

/**
 * This class reads all incomming packets from the Veo and constructs the images
 * ready to be sent to the ImageHandler, or saves the packets and waits for them
 * to be picked up, keyed from the sequence of the request packet.
 * 
 * This class is likely full of race conditions, please point them out when you
 * see them!
 * 
 * TODO: Fix the thundering hurd problem, only wakeup people who are waiting for
 * a specific sequence character.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: VeoMessageReader.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 *  
 */
public class VeoMessageReader extends Thread implements VeoConstants {
    /** Reference to the Veo object. */
    Veo v;

    /** The mapping of frame numbers to VeoImage objects */
    HashMap imageMap;

    /** True if the thread is running */
    boolean running = true;

    /** A array of outstanding responses not yet claimed. */
    Vector responses;

    public VeoMessageReader(Veo v) {
        this.v = v;
        responses = new Vector();
        imageMap = new HashMap();
    }

    /**
     * Shutdown and interrupt the thread.
     */
    public void shutdown() {
        running = false;
        interrupt();
    }

    /**
     * Obtain a response for the the given sequence. Wait <tt> timeout </tt>
     * milliseconds before giving up and returning null.
     * 
     * 
     * @param sequence
     *            The sequence character used when sending the request.
     * @param timeout
     *            The timeout to wait.
     *  
     */
    public synchronized Response getResponse(byte sequence, int timeout) {
        int attempts = 0;

        while (attempts++ < 5) {
            if (responses.isEmpty())
                try {
                    wait(timeout / 5);
                } catch (InterruptedException ignore) {
                    // Ignore this.
                }

            // TODO: Make this a hash, so it's quicker?
            if (responses.isEmpty() == false) {
                for (int i = 0; i < responses.size(); i++) {
                    Response r = (Response) responses.get(i);
                    if (r.getData()[0] == sequence) {
                        responses.remove(i);
                        return r;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Process an Response which contains image information. This will add parts
     * of the image to the existing VeoImage object, or create it if this is the
     * first part. On the last part it will also dispatch the image to the
     * handler in the Veo.
     * 
     * @param response
     *            The response which was recieved.
     * 
     * @throws IOException
     *             If any IO error occurs such as EOF, etc.
     */

    public synchronized void processImageResponse(Response response)
            throws IOException {

        int type = response.getType();

        InputData data = response.getInputData();

        int frameNo = data.readShort() & 0xFFFF;
        short seqNo = data.readShort();
/*      byte rate = */data.readByte();

        //        System.out.println("type=" + type + " frameNo=" + frameNo + " seqNo="
        //                + seqNo);
        if (type == VEO_RESPONSE_BEGIN_IMAGE) {
            if (imageMap.containsKey(new Integer(frameNo))) {
                System.err
                        .println("got begin image for already existing image. "
                                + frameNo);
                return;
            }
            long timestamp = data.readLong();
            data.skip(6);
            short height = data.readShort();
            short width = data.readShort();
            VeoImage image = new VeoImage(timestamp, frameNo, height, width);
            imageMap.put(new Integer(frameNo), image);
            image.addPart(seqNo, data);
        } else if (type == VEO_RESPONSE_CONTINUE_IMAGE) {
            VeoImage image = (VeoImage) imageMap.get(new Integer(frameNo));
            if (image == null) {
                System.err.println("got continue for non-existant image. "
                        + frameNo);
                return;
            }
            image.addPart(seqNo, data);
        } else if (type == VEO_RESPONSE_END_IMAGE) {
            VeoImage image = (VeoImage) imageMap.get(new Integer(frameNo));
            if (image == null) {
                System.err.println("Got end for non-existant image." + frameNo);
                return;
            }
            image.addPart(seqNo, data);
            imageMap.remove(new Integer(frameNo));
            v.dispatchImage(image);
        }
    }

    /**
     * Process a standard response. This will add the response to the vector of
     * responses and notify any waiting threads.
     * 
     * @param response
     */
    public synchronized void processStandardResponse(Response response) {
        responses.add(response);
        notifyAll();
    }

    /**
     * The run thread method. Reads packet using Veo.readResponse() method and
     * then calls processStandardResponse or processImageResponse depending on
     * the type of the message.
     */
    public void run() {
        while (running) {
            Response response;
            try {
                response = v.readResponse();
                int type = response.getType();

                if (type == VEO_RESPONSE_BEGIN_IMAGE
                        || type == VEO_RESPONSE_CONTINUE_IMAGE
                        || type == VEO_RESPONSE_END_IMAGE)
                    processImageResponse(response);
                else if (type == VEO_RESPONSE_OK || type == VEO_RESPONSE_ERROR)
                    processStandardResponse(response);
            } catch (IOException e) {
                if (running == false)
                    continue;
                //running = false;
                //System.err.println("Exception in run() method of VeoMessageReader: " + e);
            } catch (InvalidResponseException e) {
                //System.err.println("Invalid Response: " + e);
            }
        }
    }

}