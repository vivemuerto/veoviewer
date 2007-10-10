/*
 * InputData.java
 * 
 * Created on Aug 13, 2004
 */
package net.halo3.veo.messages;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Simple extension to DataInputStream allowing it to read null terminated
 * strings.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: InputData.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 *  
 */
public class InputData extends DataInputStream {

    /**
     * Construct a InputData object referncing an underlying InputStream.
     * 
     * @param stream
     *            The underlying InputStream.
     */
    public InputData(InputStream stream) {
        super(stream);
    }

    /**
     * Method which is able to read a null terminated string of a specified size
     * from the underlying input stream.
     * 
     * @param n
     *            The maximum size of the string.
     * 
     * @return The string of size n or less.
     * 
     * @throws IOException
     *             if unable to read from the underlying data stream.
     */
    public String readString(int n) throws IOException {
        byte b[] = new byte[n];
        int totalRead = 0;

        while (totalRead < n) {
            int bytesRead = read(b);
            if (bytesRead == -1)
                throw new EOFException();
            totalRead += bytesRead;
        }

        for (int i = 0; i < n; i++)
            if (b[i] == 0)
                return new String(b, 0, i);

        return new String(b);
    }
}