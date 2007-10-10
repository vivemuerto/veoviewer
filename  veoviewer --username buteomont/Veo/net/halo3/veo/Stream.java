/*
 * Stream.java
 * 
 * Created on Aug 13, 2004
 * 
 */

package net.halo3.veo;

/**
 * Represents the an Available stream along with the minimum and maximum frame
 * rates.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: Stream.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class Stream {
    /** The index of the stream. */
    private byte index;

    /** The height of the images for this stream */
    private short height;

    /** The width of the images for this stream */
    private short width;

    /** The maximum time for this stream */
    private int maxTime;

    /** The minimum time for this stream */
    private int minTime;

    /**
     * Construct a new stream with the given parameters.
     * 
     * @param index
     *            The index of the stream in the list of streams.
     * @param height
     *            The height of images in this stream.
     * @param width
     *            The width of images in this stream.
     * @param maxTime
     *            The maximum time between images in this stream.
     * @param minTime
     *            The minimum time between images in this stream.
     */
    public Stream(byte index, short height, short width, int maxTime,
            int minTime) {
        this.index = index;
        this.height = height;
        this.width = width;
        this.maxTime = maxTime;
        this.minTime = minTime;
    }

    /**
     * Method to obtain the index of this stream.
     * 
     * @return The width in pixels.
     */
    public byte getIndex() {
        return index;
    }

    /*
     * Method to obtain the width of the images for this stream.
     * 
     * @return The width in pixels.
     */
    public short getWidth() {
        return width;
    }

    /**
     * Method to obtain the height of the images for this stream.
     * 
     * @return The height in pixels.
     */
    public short getHeight() {
        return height;
    }

    /**
     * Method to obtain the maximum time of the images for this stream.
     * 
     * @return The maximum time in unknown units.
     */
    public int getMaxTime() {
        return maxTime;
    }

    /**
     * Method to obtain the minimum time of the images for this stream.
     * 
     * @return The minimum time in unknown units.
     */

    public int getMinTime() {
        return minTime;
    }

    /**
     * Return a human readable string representing this stream.
     * 
     * @return The string.
     */
    public String toString() {
        return index + ": h=" + height + ", w=" + width + ", mintime="
                + minTime + " maxtime=" + maxTime;
    }
}