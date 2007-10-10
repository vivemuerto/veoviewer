/*
 * StartStreamRequest.java
 * 
 * Created on Aug 13, 2004
 * 
 */
package net.halo3.veo.messages;

import java.io.IOException;

/**
 * Class used to represent a StartStream request to the Veo camera.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: StartStreamRequest.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class StartStreamRequest extends Request {

    /**
     * Construct a start stream request for the specified stream.
     * 
     * @param audio
     *            true is audio also to be supplied (not implemented)
     * @param streamId
     *            The stream Id to be used as obtained from logon or
     *            selectStream.
     * @throws IOException
     *             If unable to construct the request.
     */
    public StartStreamRequest(boolean audio, int streamId) throws IOException {
        super(VEO_MSG_STREAM_START);
        outputData.writeInt(streamId);
        // TODO What is this byte for?
        outputData.writeShort((short) 0);
        outputData.writeByte(audio ? 1 : 0);
    }
}