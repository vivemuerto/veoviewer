/*
 * InvalidResponseException.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo.messages;

/**
 * Class used to represent an invalid response read from the Veo.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: InvalidResponseException.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class InvalidResponseException extends Exception {

    /**
     * Construct a InvalidResponseException with the specified message.
     * 
     * @param msg
     *            The message.
     */
    public InvalidResponseException(String msg) {
        super(msg);
    }
}