/*
 * LoginRequest.java
 * 
 * Created on Aug 13, 2004
 *
 */

package net.halo3.veo.messages;

import java.io.IOException;

/**
 * Class used to represent a login request to the Veo camera.
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: LoginRequest.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public class LoginRequest extends Request {

    /**
     * Construct a login request with the provided userid and password.
     * 
     * @param user
     *            The user to login with.
     * @param password
     *            The password to use.
     * @throws IOException
     *             If unable to construct the request.
     */
    public LoginRequest(String user, String password) throws IOException {
        super(VEO_MSG_LOGON);
        outputData.writeString(64, user);
        outputData.writeString(64, password);
    }

}