/*
 * MoveRequest.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo.messages;

import java.io.IOException;

/**
 * Class used to represent a Move request to the Veo camera.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: MoveRequest.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class MoveRequest extends Request {

    /**
     * Construct a move request in the specified direction.
     * 
     * @param direction
     *            The direction to move.
     * 
     * @throws IOException
     *             If unable to construct request.
     */
    public MoveRequest(byte direction) throws IOException {
        super(VEO_MSG_MOVE);
        outputData.writeByte(direction);
    }

}