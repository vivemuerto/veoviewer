/*
 * OutputData.java 
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo.messages;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Simple extension to DataOutputStream allowing it to write null terminated
 * strings.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: OutputData.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 *  
 */
public class OutputData extends DataOutputStream {

    /**
     * Construct a OutputData object referncing an underlying InputStream.
     * 
     * @param stream
     *            The underlying InputStream.
     */
    public OutputData(OutputStream stream) {
        super(stream);
    }

    /**
     * Method which writes a null terminated string to the underlying data
     * stream. The string will be null padded. At least one null is required.
     * 
     * @param size
     *            The maximum size of the string.
     * @param s
     *            The string to be written.
     * @throws IOException
     *             If unable to write to the underlying output stream.
     * @throws IllegalArgumentException
     *             if length of the string is >= size.
     */
    public void writeString(int size, String s) throws IOException {
        int len = s.length();
        if (len >= size)
            throw new IllegalArgumentException(
                    "String length cannot be greater or equal to size");

        writeBytes(s);

        for (int i = 0; i < size - len; i++)
            writeByte(0);
    }
}