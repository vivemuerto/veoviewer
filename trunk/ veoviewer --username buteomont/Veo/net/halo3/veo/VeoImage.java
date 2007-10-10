/*
 * VeoImage.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Class representing a partial or complete Image as received from the camera.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: VeoImage.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class VeoImage {
    /** The actual image contents */
    private ByteArrayOutputStream image;

    /** DataOutputStream allowing writeShort,etc */
    private DataOutputStream data;

    /** The height of this image */
    private int height;

    /** The width of this image */
    private int width;

    /** The timestamp for this image */
//    private long timestamp;

    /** The frame number. */
//    private int frame;

    /**
     * Construct a VeoImage with the specified parameters. First places a
     * correct and fixed up JPEG header.
     * 
     * @param timestamp
     *            The timestamp of the image in unknown units.
     * @param frame
     *            The frame number, an incrementing number for each frame.
     * @param height
     *            The height of the image in pixels.
     * @param width
     *            The width of the image in pixels.
     * 
     * @throws IOException
     *             If unable to create the required data structures or construct
     *             the JPEG header.
     */
    public VeoImage(long timestamp, int frame, int height, int width)
            throws IOException {
//        this.frame = frame;
        this.height = height;
        this.width = width;
//        this.timestamp = timestamp;
        image = new ByteArrayOutputStream();
        data = new DataOutputStream(image);

        for (int i = 0; i < VeoConstants.JPG_HEADER.length; i++) {
            // The height and width is at 0xA0
            if (i == 0xA0) {
                data.writeShort(height);
                data.writeShort(width);
                i += 4;
            }
            data.writeByte(VeoConstants.JPG_HEADER[i]);
        }
    }

    /**
     * Add a recieved part of the image.
     * 
     * @param sequence
     *            The sequence number of this part.
     * 
     * @param data
     *            DataInputStream containing the data.
     * 
     * @throws IOException
     *             If unable to obtain or save the data.
     */
    public void addPart(int sequence, DataInputStream data) throws IOException {
        byte[] buf = new byte[data.available()];
        data.read(buf);
        image.write(buf);
    }

    /**
     * Obtain an image as decoded by Toolkit.getDefaultToolkit().createImage(..)
     * 
     * @see Image
     * @return The Image object.
     */
    public Image getImage() {
        return Toolkit.getDefaultToolkit().createImage(image.toByteArray());
    }

    /**
     * Obtain the raw jpeg image data.
     * 
     * @return A byte array containing the raw image data.
     */
    public byte[] getImageData() {
        return image.toByteArray();
    }

    /**
     * Obtain the height of this image.
     * 
     * @return The height in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtain the width of this image.
     * 
     * @return The width in pixels
     */
    public int getWidth() {
        return width;
    }
}