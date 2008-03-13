/*
 * Veo.java
 * 
 * Created on Aug 13, 2004
 */

package net.halo3.veo;

import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Map;

import net.halo3.veo.messages.*;

/**
 * This is the VEO class used for controlling the Veo Observer series of
 * cameras. This has been tested with the wired version, but it is assumed that
 * it works just as well with the wireless version.
 * 
 * Basically, you construct a Veo object, login and then begin requesting
 * operations. The typical operations are all currently supported including
 * pan/tilt and obtaining a image stream from the camera.
 * 
 * Special thanks for Tobias Hoellrich (thoellri@adobe.com) for help discovering
 * the protocol and required communication details. Much of the code was a
 * direct translation from Tobias' Veo.pm perl module.
 * 
 * <code> 
 * 		Veo veo = new Veo("host",1600);
 * 		if (veo.login("admin","password") == false) { 
 * 			// Unable to login?
 * 		} else { 
 * 			// Select a 640x480 stream at 1 fps.
 * 			veo.selectStream(VEO_STREAM_640x480,1);
 * 			veo.setImageHandler(this);
 * 			veo.startStream();
 * 			if (veo.moveCamera(VEO_MOVE_UP) == false) 
 * 				// Unable to move? at limit?
 * 
 * 			....
 * 			veo.stopStream();
 * 			
 * 			// shutdown closes the connection and stops receiver thread.
 * 			veo.shutdown();
 * 		}
 * 
 * 	...
 *
 * 		// This method will get called when an image is avilable. 
 * 		public void handleImage(VeoImage image) {
 * 			myJButton.setIcon(new ImageIcon(image.getImage()); 
 * 		}
 * </code>
 * 
 * @author jr (jr@halo3.net)
 * @version $Id: Veo.java,v 1.1 2004/08/14 03:48:30 jr Exp $
 */

public class Veo implements VeoConstants {

	/** maximum steps from one extreme to the other */
	public final static int MAX_VERTICAL 	= 40;
	public final static int MAX_HORIZONTAL 	= 40;
	
    /** The default timeout to wait for a response */
    private static final int DEF_TIMEOUT = 10000;

    /** The protocol major version, populated upon login */
    private byte protocolVerMajor;

    /** The protocol minor version, populated upon logon */
    private byte protocolVerMinor;

    /** The current access level, 2 = admin? */
    private byte accessLevel;

    /** The streamId obtained at login, or via selectStream */
    protected int streamId;

    /** Format, obtained at login or selectStream. TODO: figure out values. */
    private byte format;

    /**
     * The time per frame obtained at login or via selectStream. TODO: figure
     * out unit
     */
    private int timePerFrame;

    /** The maximum brightness value, obtained at logon */
    private int maxBrightness;

    /** The listing of all streams, obtained at login. */
    private Stream[] streams;

    /** True if we are logged in */
    boolean loggedIn = false;

    /** Socket used to communication with the Veo */
    Socket socket;

    /** The current sequence number */
    byte seq = 0;

    /** The output stream to the Veo */
    DataOutputStream out;

    /** The input stream from the Veo */
    DataInputStream in;

    /** Veo message reader */
    VeoMessageReader reader;

    /** The handler which will recieve the images. */
    private VeoImageHandler handler;

    /** Print status and debug info to stdout. */
    private boolean verbose=true;
    
    private Map<Integer, String> veoMoveText=new Hashtable<Integer, String>();
    private Map<Integer, String> veoResponseText=new Hashtable<Integer, String>();
    private Map<Integer, String> veoMessageText=new Hashtable<Integer, String>();

    /**
     * Construct a Veo object and connect to the specified host and port. The
     * port is the web port + 1520. Keep this in mind if you have changed the
     * port the veo listens on.
     * 
     * This method will also start the VeoMessageReader thread.
     * 
     * @param host
     *            The host to connect to, IP or hostname.
     * @param port
     *            The port to connect to.
     * @throws UnknownHostException
     *             If unable to locate host.
     * @throws IOException
     *             If unable to connect or some other error occurs.
     */
    public Veo(String host, int port) throws UnknownHostException, IOException {
    	initVeoText();
        socket = new Socket(InetAddress.getByName(host), port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        reader = new VeoMessageReader(this);
        reader.start();
    }

    private void initVeoText()
		{
		veoMoveText.put(new Integer(0),"up");
		veoMoveText.put(new Integer(1),"full up");
		veoMoveText.put(new Integer(2),"down");
		veoMoveText.put(new Integer(3),"full down");
		veoMoveText.put(new Integer(4),"left");
		veoMoveText.put(new Integer(5),"full left");
		veoMoveText.put(new Integer(6),"right");
		veoMoveText.put(new Integer(7),"full right");
		
		veoResponseText.put(new Integer(0),"begin image");
		veoResponseText.put(new Integer(1),"continue image");
		veoResponseText.put(new Integer(2),"end image");
		veoResponseText.put(new Integer(4),"ok");
		veoResponseText.put(new Integer(5),"error");

		veoMessageText.put(new Integer(0x00),"logon");
		veoMessageText.put(new Integer(0x00),"stream 160x120");
		veoMessageText.put(new Integer(0x01),"stream 320x240");
		veoMessageText.put(new Integer(0x02),"stream 640x480");
		veoMessageText.put(new Integer(0x01),"stream start");
		veoMessageText.put(new Integer(0x02),"stream stop");
		veoMessageText.put(new Integer(0x03),"locate net");
		veoMessageText.put(new Integer(0x04),"setup net");
		veoMessageText.put(new Integer(0x05),"get bright");
		veoMessageText.put(new Integer(0x06),"set bright");
		veoMessageText.put(new Integer(0x07),"get light");
		veoMessageText.put(new Integer(0x08),"set light");
		veoMessageText.put(new Integer(0x09),"get camera info");
		veoMessageText.put(new Integer(0x0a),"set camera info");
		veoMessageText.put(new Integer(0x0b),"get email property");
		veoMessageText.put(new Integer(0x0c),"set email property");
		veoMessageText.put(new Integer(0x0d),"get user accounts");
		veoMessageText.put(new Integer(0x0e),"set user account");
		veoMessageText.put(new Integer(0x0f),"delete user");
		veoMessageText.put(new Integer(0x10),"replace firm length");
		veoMessageText.put(new Integer(0x11),"replace firm data");
		veoMessageText.put(new Integer(0x12),"get motion detect");
		veoMessageText.put(new Integer(0x13),"set motion detect");
		veoMessageText.put(new Integer(0x14),"select stream");
		veoMessageText.put(new Integer(0x15),"move");
		veoMessageText.put(new Integer(0x16),"reset");
		veoMessageText.put(new Integer(0x17),"get status light");
		veoMessageText.put(new Integer(0x18),"set status light");
		}

	/**
     * Login to the Veo camera with the provided userid and password.
     * 
     * 
     * @param userid
     *            The userid to login with.
     * @param password
     *            The password to login with.
     * 
     * @return true on success, false on login failure.
     * @throws IOException
     *             If unable to send login request.
     */
    public boolean login(String userid, String password) throws IOException {
    	if (isVerbose()) System.out.println("Logging in");
        LoginRequest m = new LoginRequest(userid, password);
        Response r = sendRequest(m, DEF_TIMEOUT);

        if (r != null && r.getType() == VEO_RESPONSE_OK) {
            loggedIn = true;
            InputData in = r.getInputData();
            in.skip(1);
            protocolVerMajor = in.readByte();
            protocolVerMinor = in.readByte();
            accessLevel = in.readByte();
            streamId = in.readInt();
            format = in.readByte();
            timePerFrame = in.readInt();
            maxBrightness = in.readInt();
            int streamCount = (in.readByte() & 0xFF);

            streams = new Stream[streamCount];

            for (byte i = 0; i < streamCount; i++) {
                short w = in.readShort();
                short h = in.readShort();
                int maxTime = in.readInt();
                int minTime = in.readInt();
                Stream s = new Stream(i, h, w, maxTime, minTime);
                streams[i] = s;
            }
            return true;
        }
        return false;
    }

    /**
     * Obtain the listing of valid streams;
     * 
     * @return The array of stream objects.
     */
    public Stream[] getStreams() {
        return streams;
    }

    /**
     * Select a speicifc stream object.
     * 
     * The streams appear to always be in the same order. This allows you to use
     * the constants. It also allows you to select the frames per second you
     * would like to have the frames delivered at. If the frames per second is
     * too high for that stream it will automatically be adjusted down to the
     * maximum value.
     * 
     * @see VeoConstants#VEO_STREAM_160x120
     * @see VeoConstants#VEO_STREAM_320x120
     * @see VeoConstants#VEO_STREAM_640x480
     * 
     * @param index
     *            The index in the stream.
     * @param framesPerSec
     *            The number of frames per second you would like.
     * 
     * @return True if the stream was succesfully selected, false on failure.
     * 
     * 
     * @throws IOException
     *             If unable to select the stream due to IO error.
     */
    public boolean selectStream(int index, int framesPerSec) throws IOException {
    	if (isVerbose()) System.out.println("Selecting stream "+index+" at "+framesPerSec+" frames per second");
        Stream s = streams[index];
        return selectStream(s, framesPerSec);
    }

    /**
     * Select a specific stream by the Stream object. Use the objects obtained
     * from <tt>getStreams()</tt>
     * 
     * @param s
     *            The stream you would like to select.
     * @param framesPerSec
     *            The number of frames per second you would like to have.
     * 
     * @return true if the stream was successfully selected, false on failure.
     * @throws IOException
     *             If unable to select the stream due to IO error.
     */
    public boolean selectStream(Stream s, int framesPerSec) throws IOException {
        if (framesPerSec > 10)
            framesPerSec = 10;
        else if (framesPerSec <= 0)
            framesPerSec = 1;

        int delay = VEO_RATE_DELAYS[framesPerSec - 1];

        if (delay < s.getMinTime())
            delay = s.getMinTime();
        else if (delay > s.getMaxTime())
            delay = s.getMaxTime();

        Request request = new Request(VEO_MSG_SELECT_STREAM);
        OutputData output = request.getOutputData();
        //System.out.println(s);
        output.writeByte(s.getIndex());
        output.writeInt(delay);
        Response response = sendRequest(request, 5000);
        if (response != null && response.getType() == VEO_RESPONSE_OK) {

            InputData input = response.getInputData();
            input.skip(1); // Skip sequence.
            streamId = input.readInt();
            format = input.readByte();
            timePerFrame = input.readInt();
            return true;
        }

        return false;

    }

    /**
     * Method to send the request to the VEO camera.
     * 
     * @param r
     *            The request to be send.
     * @param timeout
     *            The number of milliseconds to wait for a response.
     * @return The response or null on timeout.
     * 
     * @throws IOException
     *             If unable to send the request or recieve the response due to
     *             IO error.
     */
    protected Response sendRequest(Request r, int timeout) throws IOException {
        byte seq = this.seq++;
        byte[] data = r.toByteArray(seq);
        out.write(data);
        return reader.getResponse(seq, DEF_TIMEOUT);
    }

    /**
     * Actually read a response from the network. This method first reads the
     * size of the packet, and then attempts to read the entire packet.
     * 
     * @return The packet wrapped in a Response object.
     * 
     * @throws IOException
     *             If unable to read the packet due to some IO error.
     * 
     * @throws InvalidResponseException
     *             If the packet was somehow invalid, wrong length, etc.
     */
    protected Response readResponse() throws IOException,
            InvalidResponseException {
        byte[] buffer;
        int packetSize = in.readShort();
        if(packetSize < 0) return null;
        buffer = new byte[packetSize];
        int bytesRead = 0;
        int totalBytesRead = 2;
        // Put back in the packetSize...
        buffer[0] = (byte) (packetSize >> 8);
        buffer[1] = (byte) (packetSize & 0xFF);

        while (totalBytesRead < packetSize) {
//            int bytesLeft = packetSize - totalBytesRead;
            bytesRead = in.read(buffer, totalBytesRead, packetSize
                    - totalBytesRead);

            if (bytesRead == -1)
                return null;

            totalBytesRead += bytesRead;
        }
        return new Response(buffer);
    }

    /**
     * Move the camera a certain direction depending on the argument.
     * 
     * @see VeoConstants#VEO_MOVE_DOWN
     * @see VeoConstants#VEO_MOVE_FULL_DOWN
     * @see VeoConstants#VEO_MOVE_UP
     * @see VeoConstants#VEO_MOVE_FULL_UP
     * @see VeoConstants#VEO_MOVE_LEFT
     * @see VeoConstants#VEO_MOVE_FULL_LEFT
     * @see VeoConstants#VEO_MOVE_RIGHT
     * @see VeoConstants#VEO_MOVE_FULL_RIGHT
     * 
     * @param direction
     *            on of the VEO_MOVE_* constants indicating direction.
     * @return True if moved, false if moved failed possibly due to max
     *         position.
     * 
     * @throws IOException
     *             If unable to send the request or recieve the response due to
     *             IO error.
     */

    public boolean moveCamera(byte direction) throws IOException {
		if (isVerbose()) System.out.println("Moving camera "+veoMoveText.get(new Integer(direction)));
        Request m = new MoveRequest(direction);
        Response r = sendRequest(m, 5000);

        if (r != null && r.getType() == VEO_RESPONSE_OK)
            return true;

        return false;
    }

    /**
     * Start the stream. This will cause the <tt>VeoImageHandler</tt> to start
     * recieving VeoImage objects.
     * 
     * @return true if the stream was succefully started.
     * 
     * @throws IOException
     *             If unable to send the request or recieve the response due to
     *             IO error.
     */
    public boolean startStream() throws IOException {
		if (isVerbose()) System.out.println("Starting stream");
        Request m = new StartStreamRequest(false, streamId);
        Response r = sendRequest(m, DEF_TIMEOUT);
        if (r != null && r.getType() == VEO_RESPONSE_OK)
            return true;
        return false;
    }

    /**
     * Shutdown the veo object and VeoMessageReader thread.
     * 
     * @throws IOException
     *             if unable to close the socket.
     */
    public void shutdown() throws IOException {
		if (isVerbose()) System.out.println("Disconnecting");
        reader.shutdown();
        socket.close();
    }

    /**
     * Dispatch the new image to the requested image handler.
     * 
     * @param image
     *            The newly recieved image.
     */
    public void dispatchImage(VeoImage image) {
        if (handler != null)
            handler.processImage(image);
    }

    /**
     * Stop a currently running stream. This causes the calls to the
     * VeoImageHandler to stop.
     * 
     * @throws IOException
     *             If unable to send the request or recieve the response due to
     *             IO error.
     */
    public boolean stopStream() throws IOException {
		if (isVerbose()) System.out.println("Stopping stream");
        Request m = new StopStreamRequest();
        Response r = sendRequest(m, 5000);
        if (r != null && r.getType() == VEO_RESPONSE_OK)
            return true;
        return false;
    }

    /**
     * Set the class which will recieve the newly obtained images.
     * 
     * @see VeoImageHandler
     * 
     * @param handler
     *            the new class implementing the VeoImageHandler.
     */
    public void setImageHandler(VeoImageHandler handler) {
        this.handler = handler;
    }

	public byte getAccessLevel()
		{
		return accessLevel;
		}

	public void setAccessLevel(byte accessLevel)
		{
		this.accessLevel=accessLevel;
		}

	public byte getFormat()
		{
		return format;
		}

	public void setFormat(byte format)
		{
		this.format=format;
		}

	public int getMaxBrightness()
		{
		return maxBrightness;
		}

	public void setMaxBrightness(int maxBrightness)
		{
		this.maxBrightness=maxBrightness;
		}

	public byte getProtocolVerMajor()
		{
		return protocolVerMajor;
		}

	public void setProtocolVerMajor(byte protocolVerMajor)
		{
		this.protocolVerMajor=protocolVerMajor;
		}

	public byte getProtocolVerMinor()
		{
		return protocolVerMinor;
		}

	public void setProtocolVerMinor(byte protocolVerMinor)
		{
		this.protocolVerMinor=protocolVerMinor;
		}

	public int getTimePerFrame()
		{
		return timePerFrame;
		}

	public void setTimePerFrame(int timePerFrame)
		{
		this.timePerFrame=timePerFrame;
		}

	public boolean isVerbose()
		{
		return verbose;
		}

	public void setVerbose(boolean verbose)
		{
		this.verbose=verbose;
		}
}