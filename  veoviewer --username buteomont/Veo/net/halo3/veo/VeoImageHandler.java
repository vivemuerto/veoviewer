/*
 * VeoImageHandler.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo;

/**
 * 
 * Interface a class would implement wishing to recieve the images as they show
 * up from the camera.
 * 
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: VeoImageHandler.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */
public interface VeoImageHandler {

    /**
     * Method which is called when a new image is recieved.
     * 
     * @param image
     *            The newly recieved image.
     */
    void processImage(VeoImage image);
}