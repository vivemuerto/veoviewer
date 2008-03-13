package org.buteomont.util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.net.ServerSocketFactory;

import net.halo3.veo.Veo;


public class ImageServer extends Thread
	{
	private int port;
	private ImageSaver saver;
	private CommandListener commandListener;
	private boolean verbose=true;
	private boolean started=true;
	private String imageName="image.jpg";
	
public ImageServer(int port, ImageSaver saver, CommandListener commandListener, boolean verbose)
		{
		this.port=port;
		this.saver=saver;
		this.verbose=verbose;
		this.commandListener=commandListener;
		}



	public void run()
		{
		ServerSocket socket=null; 
		Socket connection=null;
        OutputStream out=null;
		PrintStream pout=null;
		try
			{
			socket=ServerSocketFactory.getDefault()
				.createServerSocket(getPort());
			}
		catch (IOException e)
			{
			e.printStackTrace();
			System.out.println("Unable to start image server.");
			setStarted(false);
			}
		
		while (isStarted())
			{
			// read the first line of the request (ignore the rest)
			String request=null;
			try
				{
				connection=socket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				out=new BufferedOutputStream(connection.getOutputStream());
				pout=new PrintStream(out);

				request=in.readLine();
				if (request==null)
				    continue;
				log(connection, request);
				while (true) {
				    String misc = in.readLine();
				    if (misc==null || misc.length()==0)
				        break;
				}
				}
			catch (Exception e1)
				{
				e1.printStackTrace();
				continue;
				}

            // check that the request is well-formed and 
            //permitted. Then send back the image.
            // parse the line
            if (!request.startsWith("GET") || request.length()<14 ||
                !(request.endsWith("HTTP/1.0") || request.endsWith("HTTP/1.1"))) 
            	{
                // bad request
                errorReport(pout, connection, "400", "Bad Request", 
                            "Your browser sent a request that " + 
                            "this server could not understand.");
            	}
            else 
            	{
                String req = request.substring(5, request.length()-9).trim();
                String parms=null;
                int qm=req.indexOf("?");
                if (qm>0)
                		{
                		parms=req.substring(qm+1);
                		req=req.substring(0, qm);
                		}
                if (parms!=null)
                	doCommand(parms);
                if (req.equals(getImageName()))
                	{
                    // send image
                    pout.print("HTTP/1.0 200 OK\r\n" +
                               "Content-Type: image/jpeg\r\n" +
                               "Date: " + new Date() + "\r\n" +
                               "Server: VeoViewer 1.0\r\n\r\n");
                    saver.saveImage(pout);
//                    log(connection, "200 OK");
	            	}
	            try 
	            	{
	                out.flush();
	                if (connection != null) connection.close(); 
	            	}
	            catch (Exception e) 
	            	{
	            	System.err.println(e); 
					continue;
	            	}
            	}
			}
		if (isVerbose()) System.out.println("Stopping image server");
		connection=null;
		try
			{
			socket.close();
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		socket=null;
		}


		
	private void doCommand(String parms)
		{
		commandListener.command(parms);
		}



	private void errorReport(PrintStream pout, Socket connection,
	    String code, String title, String msg)
		{
		pout.print("HTTP/1.0 " + code + " " + title + "\r\n" +
		"\r\n" +
		"<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\r\n" +
		"<TITLE>" + code + " " + title + "</TITLE>\r\n" +
		"</HEAD><BODY>\r\n" +
		"<H1>" + title + "</H1>\r\n" + msg + "<P>\r\n" +
		"<HR><ADDRESS>FileServer 1.0 at " + 
		connection.getLocalAddress().getHostName() + 
		" Port " + connection.getLocalPort() + "</ADDRESS>\r\n" +
		"</BODY></HTML>\r\n");
		log(connection, code + " " + title);
		}
	
	private void log(Socket connection, String msg)
		{
		if (isVerbose()) 
			System.out.println(new Date() + " [" + connection.getInetAddress().getHostAddress() + 
                   ":" + connection.getPort() + "] " + msg);
		}



	public boolean isVerbose()
		{
		return verbose;
		}



	public void setVerbose(boolean verbose)
		{
		this.verbose=verbose;
		}



	public int getPort()
		{
		return port;
		}



	public void setPort(int port)
		{
		this.port=port;
		}



	public ImageSaver getSaver()
		{
		return saver;
		}



	public void setSaver(ImageSaver saver)
		{
		this.saver=saver;
		}



	public boolean isStarted()
		{
		return started;
		}



	public void setStarted(boolean started)
		{
		this.started=started;
		}



	public String getImageName()
		{
		return imageName;
		}



	public void setImageName(String imageName)
		{
		this.imageName=imageName;
		}
	}
