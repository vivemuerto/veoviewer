/*
 * Request.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo.messages;

import java.io.ByteArrayOutputStream;

import net.halo3.veo.VeoConstants;

/**
 * 
 * Class used to represent a Request to the Veo Camera.
 * 
 * <code> 
 * 		Request r = new Request(SOME_CODE);
 * 		r.getOutputData().addInt(0xABCD); // Add 4 bytes to output.
 * </code>
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: Request.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class Request implements VeoConstants {
    /** The message code for this message. */
    private byte msg;

    /** The storage of the acutal data output */
    private ByteArrayOutputStream output;

    /** OutputData class for writing to the data output */
    protected OutputData outputData;

    /**
     * Construct a message object to be sent to the Veo Camera.
     * 
     * @param msg
     *            The message arguments, typically a constants.
     * @see VeoConstants#VEO_MSG_LOGON and friends.
     */
    public Request(byte msg) {
        output = new ByteArrayOutputStream();
        outputData = new OutputData(output);
        this.msg = msg;
    }

    /**
     * Obtain the output data object used to write data to this message
     * 
     * @return The outputdata object.
     */
    public OutputData getOutputData() {
        return outputData;
    }

    /**
     * Return the Message as a byte array which is ready to be sent to the
     * camera. The sequence of all messages is:
     * 
     * [size-hibyte][size-lowbyte][msg][sequence][data....]
     * 
     * @param sequence
     *            The sequence used in this message. Needs to come from outside,
     *            so that the caller can obtain response.
     * @return The packet ready to be sent.
     */
    public byte[] toByteArray(byte sequence) {
        int size = output.size() + 4;
        byte[] packet = new byte[size];
        packet[0] = (byte) ((size >> 8) & 0xFF);
        packet[1] = (byte) (size & 0xFF);
        packet[2] = msg;
        packet[3] = sequence;
        System.arraycopy(output.toByteArray(), 0, packet, 4, size - 4);
        return packet;
    }
}