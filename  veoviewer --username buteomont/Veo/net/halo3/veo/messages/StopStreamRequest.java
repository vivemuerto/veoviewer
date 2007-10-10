/*
 * StopStreamRequest.java
 * 
 * Created on Aug 13, 2004
 * 
 */
package net.halo3.veo.messages;

/**
 * Class used to represent a StopStream request to the Veo camera.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: StopStreamRequest.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class StopStreamRequest extends Request {

    /**
     * Create a StopStream request.
     */
    public StopStreamRequest() {
        super(VEO_MSG_STREAM_STOP);
    }

}