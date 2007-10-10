package net.halo3.veo.messages;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/*
 * Created on Aug 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author jr
 * 
 * Response is:
 * 
 * [length-hibyte][length-lowbyte][response][sequence][data...]
 *  
 */
public class Response {
    /** The acutal response data. Everything after the size, and the type */
    private byte[] packetData;

    private InputData inputData;

    /** The size of the packet, including data, size bytes and type */
    private int size;

    /** The type of the packet */
    private byte type;

    /**
     * Constructor a response from the given byte array. It is assumed that the
     * byte array is a valid packet, otherwise an exception could occur.
     * 
     * @param b
     *            The byte array containing the response.
     * @throws IOException
     *             If an error occurs on the underlying input stream.
     * @throws InvalidResponseException
     *             If the response is somehow not valid.
     */
    public Response(byte b[]) throws IOException, InvalidResponseException {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(b));

        if (b.length < 3)
            throw new InvalidResponseException("Invalid packet length "
                    + b.length);

        this.size = data.readShort();

        if (b.length != size)
            throw new InvalidResponseException(
                    "Invalid packet length, packet says " + size + " but only "
                            + b.length);

        this.type = data.readByte();
        packetData = new byte[data.available()];
        data.read(packetData);

        inputData = new InputData(new ByteArrayInputStream(packetData));
    }

    /**
     * Obtain the size of this packet.
     * 
     * @return The size of the packet.
     */
    public int getSize() {
        return size;
    }

    /**
     * Obtain the length of just the data contained in this packet.
     * 
     * @return The length in bytes of the data.
     */
    public int getDataSize() {
        return packetData.length;
    }

    /**
     * Obtain the type of this packet.
     * 
     * @return The byte value matching a constant in VeoConstants.
     */
    public byte getType() {
        return type;
    }

    /**
     * Obtain the DataOutputStream for the data contained in this packet. This
     * will allow you to do things like readInt, etc.
     * 
     * @return the InputData object.
     */
    public InputData getInputData() {
        return inputData;
    }

    /**
     * Return the data of the packet (everything after the type).
     * 
     * @return Byte array containing the data.
     */
    public byte[] getData() {
        return packetData;
    }
}