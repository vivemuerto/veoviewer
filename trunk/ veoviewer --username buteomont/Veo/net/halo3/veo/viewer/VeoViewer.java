package net.halo3.veo.viewer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.halo3.veo.*;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

import org.buteomont.PulsedVeo;
import org.buteomont.util.*;

/**
 * This code was generated using CloudGarden's Jigloo SWT/Swing GUI Builder,
 * which is free for non-commercial use. If Jigloo is being used commercially
 * (ie, by a for-profit company or business) then you should purchase a license -
 * please visit www.cloudgarden.com for details.
 */
public class VeoViewer extends javax.swing.JFrame implements VeoImageHandler, ImageSaver, PulseListener
	{
	public static final String INI_FILE_NAME = "veo.ini";  //  @jve:decl-index=0:
	private JButton	btnRight;
	private JButton	btnLeft;
	private JButton	btnDown;
	private JButton	btnUp;
	private JPanel	mainPanel;
	String			username	="admin";  //  @jve:decl-index=0:
	String			password	="password";  //  @jve:decl-index=0:
	String			hostname	="10.10.6.91";  //  @jve:decl-index=0:
	String			port		="1600";  //  @jve:decl-index=0:
	PulsedVeo		veo			=null;  //  @jve:decl-index=0:
	private boolean	streaming	=false;
	private JSlider horizSlider = null;
	private JPanel contentPanel = null;
	private JSlider vertSlider = null;

	private int		horzPosition=20;
	private int		vertPosition=20;
	private JPanel horzAdjPanel = null;
	private JPanel vertAdjPanel = null;
	
	public final static int MAX_VERTICAL 	= 40;
	public final static int MAX_HORIZONTAL 	= 40;
	private JPanel imagePanel = null;
	
	private Image imgFromCamera=null;  //  @jve:decl-index=0:
	private Image lastImgFromCamera=null;
	
	private ButtonGroup resGroup = null;  //  @jve:decl-index=0:
	private JMenuBar veoMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem saveImageMenuItem = null;
	private JMenu viewMenu = null;
	private JMenu streamMenu = null;
	private JRadioButtonMenuItem loResRadioButtonMenuItem = null;
	private JRadioButtonMenuItem medResRadioButtonMenuItem = null;
	private JRadioButtonMenuItem hiResRadioButtonMenuItem = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem connectMenuItem = null;
	private JMenuItem startStreamMenuItem = null;
	private JCheckBoxMenuItem maintainRatioCheckBoxMenuItem = null;
	private JMenuItem optionsMenuItem = null;
	private int serverPortNumber=1809;
	private boolean serverEnabled=true;
	private boolean verbose=true;
	private ImageServer imageServer;
	private boolean autologin=false;
	private boolean manualLogin=false;	//if connect/disconnect menu item selected
	private boolean serverTimestampEnabled=true;
	private Font serverImageFont;  //  @jve:decl-index=0:
	private static final String[] MONTHS=new String[]{"Jan","Feb","Mar",
		"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	private JCheckBoxMenuItem verboseCheckBoxMenuItem = null;
	private Pulse horzPulse=null;  //  @jve:decl-index=0:
	private Pulse vertPulse=null;  //  @jve:decl-index=0:
	private Map settings=null;	// holds all INI settings
	
	
	public VeoViewer()
		{
		initialize();
		}

	/**
	 * Initializes the GUI.
	 */
	public void initialize()
		{
		try
			{
			mainPanel=new JPanel();
			btnUp=new JButton();
			btnDown=new JButton();
			btnLeft=new JButton();
			btnRight=new JButton();

			GridBagLayout thisLayout=new GridBagLayout();
			this.getContentPane().setLayout(thisLayout);
			thisLayout.columnWidths=new int[] { 1 };
			thisLayout.rowHeights=new int[] { 1, 1 };
			thisLayout.columnWeights=new double[] { 0.1 };
			thisLayout.rowWeights=new double[] { 0.1, 0.1 };
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.setJMenuBar(getVeoMenuBar());

			resGroup=new ButtonGroup();
			resGroup.add(getLoResRadioButtonMenuItem());
			resGroup.add(getMedResRadioButtonMenuItem());
			resGroup.add(getHiResRadioButtonMenuItem());
			getMedResRadioButtonMenuItem().setSelected(true);

			this.setContentPane(getContentPanel());
			this.setTitle("VeoViewer");
			this.setName("VeoViewer");
			this.setSize(new Dimension(339, 316));
			Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(d.width/2-170, d.height/2-158);
			this.setEnabled(true);
			this.addComponentListener(new ComponentListener()
				{
				public void componentResized(ComponentEvent e)
					{
					if (getMaintainRatioCheckBoxMenuItem().isSelected())
						{
						adjustWindow();
						}
					}
				public void componentHidden(ComponentEvent e){}
				public void componentMoved(ComponentEvent e){}
				public void componentShown(ComponentEvent e){}
				});
			this.addWindowListener(new WindowListener()
				{
				public void windowClosing(WindowEvent e)
					{
					shutdown();
					}
				public void windowActivated(WindowEvent e){}
				public void windowClosed(WindowEvent e){}
				public void windowDeactivated(WindowEvent e){}
				public void windowDeiconified(WindowEvent e){}
				public void windowIconified(WindowEvent e){}
				public void windowOpened(WindowEvent e){}
				});

			mainPanel.setLayout(new BorderLayout());
			mainPanel.setVisible(true);
			mainPanel.setPreferredSize(new java.awt.Dimension(525, 40));
			mainPanel.setName("VeoViewer");

			mainPanel.add(getHorzAdjPanel(), BorderLayout.SOUTH);
			mainPanel.add(getVertAdjPanel(), BorderLayout.EAST);
			mainPanel.add(getImagePanel(), BorderLayout.CENTER);

			btnUp.setEnabled(false);
			btnUp.setIcon(new ImageIcon(getClass().getResource("/up.gif")));
			btnUp.setPreferredSize(new Dimension(19, 19));
			btnUp.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
						{
						btnUpActionPerformed(evt);
						}
				});

			btnDown.setEnabled(false);
			btnDown.setIcon(new ImageIcon(getClass().getResource("/down.gif")));
			btnDown.setPreferredSize(new Dimension(19, 19));
			btnDown.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
						{
						btnDownActionPerformed(evt);
						}
				});

			btnLeft.setEnabled(false);
			btnLeft.setIcon(new ImageIcon(getClass().getResource("/left.gif")));
			btnLeft.setPreferredSize(new Dimension(19, 19));
			btnLeft.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
						{
						btnLeftActionPerformed(evt);
						}
				});

			btnRight.setEnabled(false);
			btnRight.setIcon(new ImageIcon(getClass().getResource("/right.gif")));
			btnRight.setPreferredSize(new Dimension(19, 19));
			btnRight.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
						{
						btnRightActionPerformed(evt);
						}
				});
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		
		//get all of the settings from the initialization file
		initFromIni();
		connectDisconnect();
		if (isServerEnabled()) startServer();
		}

	private void initFromIni()
		{
		String serverFontFace=null;
		String serverFontSize=null;
		String serverFontStyle=null;
		Initializer init=new Initializer(INI_FILE_NAME);
		try
			{
			init.hydrate();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		settings=init.getData();
		for (Iterator i=settings.keySet().iterator();i.hasNext(); )
			{
			String var=(String)i.next();
			if (var.equalsIgnoreCase("verbose"))
				setVerbose(Boolean.valueOf((String)settings.get(var)).booleanValue());
			else if (var.equalsIgnoreCase("username"))
				username=(String)settings.get(var);
			else if (var.equalsIgnoreCase("password"))
				password=(String)settings.get(var);
			else if (var.equalsIgnoreCase("hostname"))
				hostname=(String)settings.get(var);
			else if (var.equalsIgnoreCase("port"))
				port=(String)settings.get(var);
			else if (var.equalsIgnoreCase("width"))
				setSize(Integer.valueOf((String)settings.get(var)).intValue(),getHeight());
			else if (var.equalsIgnoreCase("height"))
				setSize(getWidth(),Integer.valueOf((String)settings.get(var)).intValue());
			else if (var.equalsIgnoreCase("xLocation"))
				setLocation(Integer.valueOf((String)settings.get(var)).intValue(),(int)getLocation().getY());
			else if (var.equalsIgnoreCase("yLocation"))
				setLocation((int)getLocation().getX(),Integer.valueOf((String)settings.get(var)).intValue());
			else if (var.equalsIgnoreCase("serverEnabled"))
				setServerEnabled(Boolean.valueOf((String)settings.get(var)).booleanValue());
			else if (var.equalsIgnoreCase("autologin"))
				setAutologin(Boolean.valueOf((String)settings.get(var)).booleanValue());
			else if (var.equalsIgnoreCase("maintainAspect"))
				getMaintainRatioCheckBoxMenuItem().setSelected(Boolean.valueOf((String)settings.get(var)).booleanValue());

			// auto-motion info
			else if (var.equalsIgnoreCase("horzRate"))
				makeHorzPulse(Integer.valueOf((String)settings.get(var)).intValue());
			else if (var.equalsIgnoreCase("vertRate"))
				makeVertPulse(Integer.valueOf((String)settings.get(var)).intValue());
			
			//server info
			else if (var.equalsIgnoreCase("serverPortNumber"))
				setServerPortNumber(Integer.valueOf((String)settings.get(var)).intValue());
			else if (var.equalsIgnoreCase("serverFontFace"))
				serverFontFace=(String)settings.get(var);
			else if (var.equalsIgnoreCase("serverFontSize"))
				serverFontSize=(String)settings.get(var);
			else if (var.equalsIgnoreCase("serverFontStyle"))
				serverFontStyle=(String)settings.get(var);
			else if (var.equalsIgnoreCase("serverImageName") && getImageServer()!=null)
				getImageServer().setImageName((String)settings.get(var));

			else if (var.equalsIgnoreCase("resolution"))
				{
				String res=(String)settings.get(var);
				if (res.equalsIgnoreCase("160x120")) getLoResRadioButtonMenuItem().doClick();
				else if (res.equalsIgnoreCase("320x240")) getMedResRadioButtonMenuItem().doClick();
				else if (res.equalsIgnoreCase("640x480")) getHiResRadioButtonMenuItem().doClick();
				}
			}
		if (serverFontFace!=null && serverFontSize!=null && serverFontStyle!=null)
			setServerImageFont(new Font(serverFontFace.substring(0, serverFontFace.indexOf(".")),
										Integer.parseInt(serverFontStyle),
										Integer.parseInt(serverFontSize)));
		}

	private void makeVertPulse(int rate)
		{
		vertPulse=new Pulse(rate,"V");
		if (veo!=null) 
			{
			vertPulse.addListener(veo);
			vertPulse.addListener(this);
			}
		vertPulse.start();
		}

	private void makeHorzPulse(int rate)
		{
		horzPulse=new Pulse(rate,"H");
		if (veo!=null) 
			{
			horzPulse.addListener(veo);
			horzPulse.addListener(this);
			}
		horzPulse.start();
		}

	private void saveToIni()
		{
		if (isVerbose()) System.out.println("Writing settings to "+INI_FILE_NAME);
		Initializer init=new Initializer(INI_FILE_NAME);
		settings=new Hashtable<String, String>();
		init.setData(settings);
		settings.put("verbose", Boolean.toString(isVerbose()));
		settings.put("serverEnabled", Boolean.toString(isServerEnabled()));
		settings.put("autologin", Boolean.toString(isAutologin()));
		settings.put("maintainAspect", Boolean.toString(getMaintainRatioCheckBoxMenuItem().isSelected()));
		settings.put("serverPortNumber", getServerPortNumber()+"");
		settings.put("username", username);
		settings.put("password", password);
		settings.put("hostname", hostname);
		settings.put("port", port);
		settings.put("width",getWidth()+"");
		settings.put("height",getHeight()+"");
		settings.put("xLocation", ((int)getLocation().getX())+"");
		settings.put("yLocation", ((int)getLocation().getY())+"");
		if (getServerImageFont()!=null)
			{
			settings.put("serverFontFace", getServerImageFont().getFontName());
			settings.put("serverFontSize", getServerImageFont().getSize()+"");
			settings.put("serverFontStyle", getServerImageFont().getStyle()+"");
			}
		String res=null;
		if (getLoResRadioButtonMenuItem().isSelected()) res="160x120";
		else if (getMedResRadioButtonMenuItem().isSelected()) res="320x240";
		else if (getHiResRadioButtonMenuItem().isSelected()) res="640x480";
		settings.put("resolution", res);
		if (horzPulse!=null) settings.put("horzRate", horzPulse.getRate()+"");
		if (vertPulse!=null) settings.put("vertRate", vertPulse.getRate()+"");
		if (getImageServer()!=null) settings.put("serverImageName", getImageServer().getImageName());
		try
			{
			init.persist();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}
	
	private void shutdown()
		{
		if (veo != null)
			{
			try
				{
				if (streaming) veo.stopStream();
				veo.shutdown();
				}
			catch (IOException e1)
				{
				e1.printStackTrace();
				}
			}
		saveToIni();
		if (isVerbose()) System.out.println("Exiting.");
		}
	
	private void adjustWindow()
		{
		Dimension ipSize=getImagePanel().getSize();
		Dimension winSize=getSize();
		setSize((int)(ipSize.getHeight()*(4d/3d))+getVertAdjPanel().getWidth(),
				(int)winSize.getHeight());
		}

	/**
	 * This method initializes horizSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getHorizSlider()
		{
		if (horizSlider==null)
			{
			horizSlider=new JSlider();
			horizSlider.setMaximum(MAX_HORIZONTAL);
			horizSlider.setEnabled(false);
			horizSlider.setToolTipText("20");
			horizSlider.setValue(MAX_HORIZONTAL/2);
			horizSlider.addChangeListener(new ChangeListener()
				{
				public void stateChanged(ChangeEvent e)
					{
					int newVal=((JSlider)e.getSource()).getValue();
					horizSlider.setToolTipText(newVal+"");
					try
						{
						while (Math.abs(newVal)-horzPosition!=0)
							{
							if (newVal>horzPosition) 
								{
								veo.moveCamera(Veo.VEO_MOVE_RIGHT);
								updateStatus(false,true,false);
								}
							else if (newVal<horzPosition)
								{
								veo.moveCamera(Veo.VEO_MOVE_LEFT);
								updateStatus(false,false,false);
								}
							Thread.sleep(50);
							}
						}
					catch (IOException e1)
						{
						e1.printStackTrace();
						}
					catch (InterruptedException e2)
						{
						e2.printStackTrace();
						} 
					}
				});
			}
		return horizSlider;
		}

	protected void updateStatus(boolean verticalAxis, boolean increase, boolean alsoUpdateSlider)
		{
		int dir=-1;
		if (increase) dir=1;
		if (verticalAxis) 
			{
			vertPosition+=dir;
			if (vertPosition>MAX_VERTICAL) vertPosition=MAX_VERTICAL;
			if (vertPosition<0) vertPosition=0;
			if (alsoUpdateSlider)
				getVertSlider().setValue(vertPosition);
			}
		else 
			{
			horzPosition+=dir;
			if (horzPosition>MAX_HORIZONTAL) horzPosition=MAX_HORIZONTAL;
			if (horzPosition<0) horzPosition=0;
			if (alsoUpdateSlider)
				getHorizSlider().setValue(horzPosition);
			}
		}

	/**
	 * This method initializes contentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContentPanel()
		{
		if (contentPanel==null)
			{
			contentPanel=new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(mainPanel, BorderLayout.CENTER);
			}
		return contentPanel;
		}

	/**
	 * This method initializes vertSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getVertSlider()
		{
		if (vertSlider==null)
			{
			vertSlider=new JSlider();
			vertSlider.setOrientation(JSlider.VERTICAL);
			vertSlider.setValue(MAX_VERTICAL/2);
			vertSlider.setEnabled(false);
			vertSlider.setToolTipText("20");
			vertSlider.setMaximum(MAX_VERTICAL);
			vertSlider.addChangeListener(new ChangeListener()
				{
				public void stateChanged(ChangeEvent e)
					{
					int newVal=((JSlider)e.getSource()).getValue();
					vertSlider.setToolTipText(newVal+"");
					try
						{
						while (Math.abs(newVal)-vertPosition!=0)
							{
							if (newVal>vertPosition) 
								{
								veo.moveCamera(Veo.VEO_MOVE_UP);
								updateStatus(true,true,false);
								}
							else if (newVal<vertPosition)
								{
								veo.moveCamera(Veo.VEO_MOVE_DOWN);
								updateStatus(true,false,false);
								}
							Thread.sleep(50);
							}
						}
					catch (IOException e1)
						{
						e1.printStackTrace();
						}
					catch (InterruptedException e2)
						{
						e2.printStackTrace();
						} 
					}
				});
			}
		return vertSlider;
		}

	/**
	 * This method initializes horzAdjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getHorzAdjPanel()
		{
		if (horzAdjPanel==null)
			{
			horzAdjPanel=new JPanel();
			horzAdjPanel.setLayout(new BorderLayout());
			horzAdjPanel.add(getHorizSlider(), BorderLayout.CENTER);
			horzAdjPanel.add(btnRight, BorderLayout.EAST);
			horzAdjPanel.add(btnLeft, BorderLayout.WEST);
			}
		return horzAdjPanel;
		}

	/**
	 * This method initializes vertAdjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getVertAdjPanel()
		{
		if (vertAdjPanel==null)
			{
			vertAdjPanel=new JPanel();
			vertAdjPanel.setLayout(new BorderLayout());
			vertAdjPanel.add(getVertSlider(), BorderLayout.CENTER);
			vertAdjPanel.add(btnDown, BorderLayout.SOUTH);
			vertAdjPanel.add(btnUp, BorderLayout.NORTH);
			}
		return vertAdjPanel;
		}

	/**
	 * This method initializes imagePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getImagePanel()
		{
		if (imagePanel==null)
			{
			imagePanel=new JPanel()
				{
				public void paint(Graphics g)
					{
					Image img=getGoodCameraImage();
					if (img!=null)
						{
						int h=img.getHeight(null);
						int w=img.getWidth(null);
						Graphics2D gg=(Graphics2D)g;
						if (h>0 && w>0)
							{
							gg.drawImage(img, 0, 0, getWidth(), getHeight(), this);
							}
						else super.paint(g);
						}
					else
						{
						super.paint(g);
						}
					}

				};
			imagePanel.setLayout(null);
			imagePanel.setPreferredSize(new Dimension(640, 480));
			}
		return imagePanel;
		}

	public void saveImage(File file)
		{
		try
			{
			saveImage(new FileOutputStream(file));
			}
		catch (FileNotFoundException e)
			{
			e.printStackTrace();
			}
		}
	public void saveImage(OutputStream stream)
		{
		Image image=getGoodCameraImage();
		if (image!=null)
			{
			int w = image.getWidth(null);
	        int h = image.getHeight(null);
	        BufferedImage bi=null;
			try
				{
				bi=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2 = bi.createGraphics();
				g2.drawImage(image, 0, 0, null);
				if (isServerTimestampEnabled())
					drawTimestamp(g2, w, h);
				g2.dispose();
		        ImageIO.write(bi, "jpg", stream);
				}
	        catch(IOException ioe)
		        {
		        System.out.println("write: " + ioe.getMessage());
		        }
			catch (RuntimeException e)
				{
				e.printStackTrace();
				// don't know why this happens occasionally - restart 
				// the image server
				if (isServerEnabled())
					{
					setServerEnabled(false);
					setServerEnabled(true);
					startServer();
					}
				}
			}
		else
			System.out.println("Image not written - it is null.");
		}

	private void drawTimestamp(Graphics2D gg, int width, int height)
		{
		Calendar cal=Calendar.getInstance();
		String hour=""+cal.get(Calendar.HOUR_OF_DAY);
		String minute="00"+cal.get(Calendar.MINUTE);
		minute=minute.substring(minute.length()-2);
		StringBuffer ts=new StringBuffer();
		ts.append(MONTHS[cal.get(Calendar.MONTH)]).append(" ")
		.append(cal.get(Calendar.DAY_OF_MONTH)).append(", ")
		.append(cal.get(Calendar.YEAR)).append(" ")
		.append(hour).append(":")
		.append(minute);
		if (getServerImageFont()!=null)
			gg.setFont(getServerImageFont());
		else
			gg.setFont(gg.getFont().deriveFont(Font.BOLD, gg.getFont().getSize2D()));
		gg.setColor(Color.GREEN);
		Rectangle2D box=gg.getFontMetrics().getStringBounds(ts.toString(), gg);
		int x=width-(int)box.getWidth()-5;
		int y=height-5; //(int)box.getHeight();
		gg.drawString(ts.toString(), x, y);
		}
	
	
	/**
	 * This method initializes veoMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getVeoMenuBar()
		{
		if (veoMenuBar==null)
			{
			veoMenuBar=new JMenuBar();
			veoMenuBar.add(getFileMenu());
			veoMenuBar.add(getViewMenu());
			}
		return veoMenuBar;
		}

	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu()
		{
		if (fileMenu==null)
			{
			fileMenu=new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getConnectMenuItem());
			fileMenu.add(getStartStreamMenu());
			fileMenu.add(getSaveImageMenuItem());
			fileMenu.add(getExitMenuItem());
			}
		return fileMenu;
		}

	/**
	 * This method initializes saveImageMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveImageMenuItem()
		{
		if (saveImageMenuItem==null)
			{
			saveImageMenuItem=new JMenuItem();
			saveImageMenuItem.setText("Save Image...");
			saveImageMenuItem.setEnabled(false);
			saveImageMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					saveImage(new File("C:\\Documents and Settings\\David\\Desktop\\temp\\veoImage.jpg"));
					}
				});
			}
		return saveImageMenuItem;
		}

	/**
	 * This method initializes viewMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getViewMenu()
		{
		if (viewMenu==null)
			{
			viewMenu=new JMenu();
			viewMenu.setText("View");
			viewMenu.add(getMaintainRatioCheckBoxMenuItem());
			viewMenu.add(getVerboseCheckBoxMenuItem());
			viewMenu.add(getStreamMenu());
			viewMenu.add(getOptionsMenuItem());
			}
		return viewMenu;
		}

	/**
	 * This method initializes streamMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getStreamMenu()
		{
		if (streamMenu==null)
			{
			streamMenu=new JMenu();
			streamMenu.setText("Stream");
			streamMenu.add(getLoResRadioButtonMenuItem());
			streamMenu.add(getMedResRadioButtonMenuItem());
			streamMenu.add(getHiResRadioButtonMenuItem());
			}
		return streamMenu;
		}

	/**
	 * This method initializes loResRadioButtonMenuItem	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getLoResRadioButtonMenuItem()
		{
		if (loResRadioButtonMenuItem==null)
			{
			loResRadioButtonMenuItem=new JRadioButtonMenuItem();
			loResRadioButtonMenuItem.setText("160x120");
			loResRadioButtonMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent evt)
					{
					changeStream(VeoConstants.VEO_STREAM_160x120);
					}
				});
			}
		return loResRadioButtonMenuItem;
		}

	/**
	 * This method initializes medResRadioButtonMenuItem	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getMedResRadioButtonMenuItem()
		{
		if (medResRadioButtonMenuItem==null)
			{
			medResRadioButtonMenuItem=new JRadioButtonMenuItem();
			medResRadioButtonMenuItem.setText("320x240");
			medResRadioButtonMenuItem.setSelected(true);
			medResRadioButtonMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent evt)
					{
					changeStream(VeoConstants.VEO_STREAM_320x240);
					}
				});
			}
		return medResRadioButtonMenuItem;
		}

	/**
	 * This method initializes hiResRadioButtonMenuItem	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getHiResRadioButtonMenuItem()
		{
		if (hiResRadioButtonMenuItem==null)
			{
			hiResRadioButtonMenuItem=new JRadioButtonMenuItem();
			hiResRadioButtonMenuItem.setText("640x480");
			hiResRadioButtonMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent evt)
					{
					changeStream(VeoConstants.VEO_STREAM_640x480);
					}
				});
			}
		return hiResRadioButtonMenuItem;
		}

	/**
	 * This method initializes exitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem()
		{
		if (exitMenuItem==null)
			{
			exitMenuItem=new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent e)
					{
					shutdown();
					System.exit(0);
					}
				});
			}
		return exitMenuItem;
		}

	/**
	 * This method initializes connectMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getConnectMenuItem()
		{
		if (connectMenuItem==null)
			{
			connectMenuItem=new JMenuItem();
			connectMenuItem.setText("Connect...");
			connectMenuItem.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
						{
						setManualLogin(true);
						connectDisconnect();
						}
				});
			}
		return connectMenuItem;
		}

	/**
	 * This method initializes startStreamMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenuItem getStartStreamMenu()
		{
		if (startStreamMenuItem==null)
			{
			startStreamMenuItem=new JMenuItem();
			startStreamMenuItem.setText("Start Streaming");
			startStreamMenuItem.setEnabled(false);
			startStreamMenuItem.addActionListener(new ActionListener()
				{
				public void actionPerformed(ActionEvent evt)
					{
					startStopStreaming();
					}
				});
			}
		return startStreamMenuItem;
		}

	/**
	 * This method initializes maintainRatioCheckBoxMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getMaintainRatioCheckBoxMenuItem()
		{
		if (maintainRatioCheckBoxMenuItem==null)
			{
			maintainRatioCheckBoxMenuItem=new JCheckBoxMenuItem();
			maintainRatioCheckBoxMenuItem.setSelected(true);
			maintainRatioCheckBoxMenuItem.setText("Maintain Aspect Ratio");
			maintainRatioCheckBoxMenuItem.addChangeListener(new ChangeListener()
				{
				public void stateChanged(ChangeEvent e)
					{
					if (maintainRatioCheckBoxMenuItem.isSelected())
						adjustWindow();
					}
				});
			}
		return maintainRatioCheckBoxMenuItem;
		}

	/**
	 * This method initializes optionsMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOptionsMenuItem()
		{
		if (optionsMenuItem==null)
			{
			optionsMenuItem=new JMenuItem();
			optionsMenuItem.setText("Options...");
			optionsMenuItem.addActionListener(new java.awt.event.ActionListener()
				{
				public void actionPerformed(java.awt.event.ActionEvent e)
					{
					updateOptions();
					}
				});
			}
		return optionsMenuItem;
		}

	protected void updateOptions()
		{
		OptionsDialog dialog=new OptionsDialog(this);
		int x=getX();
		int y=getY();
		x+=getWidth()/2;
		y+=getHeight()/2;
		dialog.setLocation(x-dialog.getWidth()/2, y-dialog.getHeight()/2);
		dialog.setServerEnabled(isServerEnabled());
		dialog.setServerPortNumber(getServerPortNumber());
		dialog.getTimestampOptionCheckBox().setSelected(isServerTimestampEnabled());
		if (getServerImageFont()!=null)
			dialog.getFontEditor().setValue(getServerImageFont());
		dialog.show();
		}

	public void collectOptions(OptionsDialog dialog)
		{
		if (dialog.wasCancelled()==false)
			{
			if (isServerEnabled()!=dialog.isServerEnabled()
				|| getServerPortNumber()!=dialog.getServerPortNumber())
				{
				setServerPortNumber(dialog.getServerPortNumber());
				setServerEnabled(dialog.isServerEnabled());
				if (isServerEnabled()) startServer();
				}
			setServerTimestampEnabled(dialog.getTimestampOptionCheckBox().isSelected());
			if (isServerTimestampEnabled())
				setServerImageFont(dialog.getTimestampFont());
			setServerTimestampEnabled(dialog.getTimestampOptionCheckBox().isSelected());
			getImageServer().setImageName(dialog.getImageNameTextField().getText());
			setAutoMotion(dialog);
			}
		}

	private void startServer()
		{
		if (isVerbose()) System.out.println("Starting image server");
		imageServer=new ImageServer(getServerPortNumber(),this,isVerbose());
		String name=(String)settings.get("serverImageName");
		if (name!=null) getImageServer().setImageName(name);
		getImageServer().start();
		}

	/**
	 * This method initializes verboseCheckBoxMenuItem	
	 * 	
	 * @return javax.swing.JCheckBoxMenuItem	
	 */
	private JCheckBoxMenuItem getVerboseCheckBoxMenuItem()
		{
		if (verboseCheckBoxMenuItem==null)
			{
			verboseCheckBoxMenuItem=new JCheckBoxMenuItem();
			verboseCheckBoxMenuItem.setText("Verbose Messages");
			verboseCheckBoxMenuItem.setSelected(isVerbose());
			verboseCheckBoxMenuItem.addItemListener(new java.awt.event.ItemListener()
				{
					public void itemStateChanged(java.awt.event.ItemEvent e)
						{
						setVerbose(verboseCheckBoxMenuItem.isSelected());
						}
				});
			}
		return verboseCheckBoxMenuItem;
		}

	/** Auto-generated main method */
	public static void main(String[] args)
		{
		try
			{
			VeoViewer inst=new VeoViewer();
			inst.setVisible(true);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	/** Auto-generated event handler method */
	protected void startStopStreaming()
		{
		try
			{
			if (streaming)
				{
				if (veo.stopStream())
					{
					veo.setImageHandler(this);
					getStartStreamMenu().setText("Start Streaming");
					streaming=false;
					}
				else
					{
					JOptionPane.showMessageDialog(this, "Unable to stop start stream!");
					}
				}
			else
				{
				if (veo.startStream())
					{
					veo.setImageHandler(this);
					getStartStreamMenu().setText("Stop Streaming");
					streaming=true;
					}
				else
					{
					JOptionPane.showMessageDialog(this, "Unable to start stream!");
					}
				}
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/*
	 * (non-Javadoc)
	 * 
	 * @see VeoImageHandler#processImage(VEOImage)
	 */
	public void processImage(VeoImage image)
		{
		saveCameraImage(image.getImage());
		getImagePanel().repaint();
		}

	/** Auto-generated event handler method */
	protected void btnUpActionPerformed(ActionEvent evt)
		{
		try
			{
			if (vertPosition<MAX_VERTICAL)
				veo.moveCamera(Veo.VEO_MOVE_UP);
			else
				veo.moveCamera(Veo.VEO_MOVE_FULL_UP);
			updateStatus(true,true,true);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/** Auto-generated event handler method */
	protected void btnDownActionPerformed(ActionEvent evt)
		{
		try
			{
			if (vertPosition>0)
				veo.moveCamera(Veo.VEO_MOVE_DOWN);
			else
				veo.moveCamera(Veo.VEO_MOVE_FULL_DOWN);
			updateStatus(true,false,true);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/** Auto-generated event handler method */
	protected void btnLeftActionPerformed(ActionEvent evt)
		{
		try
			{
			if (horzPosition>0)
				veo.moveCamera(Veo.VEO_MOVE_LEFT);
			else
				veo.moveCamera(Veo.VEO_MOVE_FULL_LEFT);
			updateStatus(false,false,true);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/** Auto-generated event handler method */
	protected void btnRightActionPerformed(ActionEvent evt)
		{
		try
			{
			if (horzPosition<MAX_HORIZONTAL)
				veo.moveCamera(Veo.VEO_MOVE_RIGHT);
			else
				veo.moveCamera(Veo.VEO_MOVE_FULL_RIGHT);
			updateStatus(false,true,true);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/** Auto-generated event handler method */
	protected void connectDisconnect()
		{
		boolean loggedIn=false;
		if (veo!=null)
			{
			try
				{
				veo.shutdown();
				}
			catch (IOException e)
				{
				JOptionPane.showMessageDialog(this, "Exception during shutdown: "+e);
				}
			enableUI(false);
			streaming=false;
			veo=null;
			horzPulse.setGoing(false);
			horzPulse=null;
			vertPulse.setGoing(false);
			vertPulse=null;
			}
		else
			{
			while (loggedIn==false)
				{
				boolean ok=false;
				if (!isAutologin() || isManualLogin())
					{
					ConnectDialog dialog=new ConnectDialog(this);
					int x=getX();
					int y=getY();
					x+=getWidth()/2;
					y+=getHeight()/2;
					dialog.setLocation(x-dialog.getWidth()/2, y-dialog.getHeight()/2);
					dialog.setUser(username);
					dialog.setPassword(password);
					dialog.setHost(hostname);
					dialog.setPort(port);
					dialog.getAutoLoginCheckBox().setSelected(isAutologin());
					dialog.setVisible(true);
					if (dialog.wasCanceled()==false)
						{
						username=dialog.getUser();
						password=dialog.getPassword();
						port=dialog.getPort();
						hostname=dialog.getHost();
						setAutologin(dialog.getAutoLoginCheckBox().isSelected());
						ok=true;
						}
					else return;
					}
				else ok=true;
				if (ok)
					{
					try
						{
//						veo=new Veo(hostname, Integer.parseInt(port));
						veo=new PulsedVeo(hostname, Integer.parseInt(port), MAX_HORIZONTAL);
						loggedIn=veo.login(username, password);
						if (loggedIn==false)
							{
							JOptionPane.showMessageDialog(this, "Login Failed!");
							setManualLogin(true); //force the login dialog
							}
						else
							{
							setManualLogin(false); //was temporary anyway
							veo.stopStream(); // in case it was already running;
							veo.setImageHandler(this);
							enableUI(true);
							int streamCode=VeoConstants.VEO_STREAM_320x240;
							if (getLoResRadioButtonMenuItem().isSelected()) streamCode=VeoConstants.VEO_STREAM_160x120;
							if (getHiResRadioButtonMenuItem().isSelected()) streamCode=VeoConstants.VEO_STREAM_640x480;
							veo.selectStream(streamCode, 1);
							startStopStreaming();
							if (horzPulse!=null) 
								{
								horzPulse.addListener(veo);
								horzPulse.addListener(this);
								}
							if (vertPulse!=null) 
								{
								vertPulse.addListener(veo);
								vertPulse.addListener(this);
								}
							}
						}
					catch (NumberFormatException e)
						{
						JOptionPane.showMessageDialog(this, "Invalid port number "+port);
						e.printStackTrace();
						}
					catch (UnknownHostException e)
						{
						JOptionPane.showMessageDialog(this, "Unknown Host: "+hostname);
						e.printStackTrace();
						}
					catch (IOException e)
						{
						JOptionPane.showMessageDialog(this, "Exception: "+e);
						e.printStackTrace();
						}
					}
				}
			}
		}

	private void setAutoMotion(OptionsDialog dialog)
		{
		if (veo==null) return;
		if (horzPulse!=null)
			{
			horzPulse.setGoing(false);
			horzPulse=null;
			}
		if (dialog.isAutoHorzMotion())
			{
			makeHorzPulse(dialog.getAutoHorzRate());
			}
		if (vertPulse!=null)
			{
			vertPulse.setGoing(false);
			vertPulse=null;
			}
		if (dialog.isAutoVertMotion())
			{
			makeVertPulse(dialog.getAutoVertRate());
			}
		
		}

	private void enableUI(boolean enable)
		{
		getConnectMenuItem().setText(enable?"Disconnect":"Connect...");
		getStartStreamMenu().setText(enable?"Stop Streaming":"Start Streaming");
		btnUp.setEnabled(enable);
		btnDown.setEnabled(enable);
		btnLeft.setEnabled(enable);
		btnRight.setEnabled(enable);
		getHorizSlider().setEnabled(enable);
		getVertSlider().setEnabled(enable);
		getStartStreamMenu().setEnabled(enable);
		getLoResRadioButtonMenuItem().setEnabled(enable);
		getMedResRadioButtonMenuItem().setEnabled(enable);
		getHiResRadioButtonMenuItem().setEnabled(enable);
		getSaveImageMenuItem().setEnabled(enable);
		}

	private Image getCameraImage()
		{
		synchronized (this)
			{
			return imgFromCamera;
			} 
		}
	private Image getGoodCameraImage()
		{
		synchronized (this)
			{
			Image img=getCameraImage();
			if (img==null || img.getHeight(null)<1 || img.getWidth(null)<1)
				img=getOldCameraImage();
			if (img==null || img.getHeight(null)<1 || img.getWidth(null)<1)
				img=null;
			return img;
			} 
		}
	private Image getOldCameraImage()
		{
		synchronized (this)
			{
			return lastImgFromCamera;
			} 
		}
	private void saveCameraImage(Image cameraImage)
		{
		synchronized (this)
			{
			if (imgFromCamera !=null
				&& imgFromCamera.getHeight(null)>0 
				&& imgFromCamera.getWidth(null)>0)
				{
				lastImgFromCamera=imgFromCamera;
				}
			imgFromCamera=cameraImage;
			}

		}

	private void changeStream(int stream)
		{
		boolean wasStreaming=streaming;
		if (veo!=null)
			{
			try
				{
				if (wasStreaming) veo.stopStream();
				boolean success=veo.selectStream(stream, 1);
				if (wasStreaming) veo.startStream();
				if (!success) System.out.println("Failed to change stream."); 
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		}

	public int getServerPortNumber()
		{
		return serverPortNumber;
		}

	public void setServerPortNumber(int serverPortNumber)
		{
		this.serverPortNumber=serverPortNumber;
		}

	public boolean isServerEnabled()
		{
		return serverEnabled;
		}

	public void setServerEnabled(boolean serverEnabled)
		{
		this.serverEnabled=serverEnabled;
		if (!serverEnabled && getImageServer()!=null)
			{
			getImageServer().setStarted(false);
			}
		}

	public boolean isVerbose()
		{
		return verbose;
		}

	public void setVerbose(boolean verbose)
		{
		this.verbose=verbose;
		if (veo!=null) veo.setVerbose(verbose);
		updateUI();
		}

	private void updateUI()
		{
		if (getVerboseCheckBoxMenuItem().isSelected()!=isVerbose())
			getVerboseCheckBoxMenuItem().setSelected(isVerbose());
		}

	public boolean isAutologin()
		{
		return autologin;
		}

	public void setAutologin(boolean autologin)
		{
		this.autologin=autologin;
		}

	public boolean isManualLogin()
		{
		return manualLogin;
		}

	public void setManualLogin(boolean manualLogin)
		{
		this.manualLogin=manualLogin;
		}

	public boolean isServerTimestampEnabled()
		{
		return serverTimestampEnabled;
		}

	public void setServerTimestampEnabled(boolean serverTimestampEnabled)
		{
		this.serverTimestampEnabled=serverTimestampEnabled;
		}

	public Font getServerImageFont()
		{
		return serverImageFont;
		}

	public void setServerImageFont(Font serverImageFont)
		{
		this.serverImageFont=serverImageFont;
		}

	public Pulse getHorzPulse()
		{
		return horzPulse;
		}

	public Pulse getVertPulse()
		{
		return vertPulse;
		}

	public void pulsed(Object source, String id)
		{
		// Veo already did the move, update the sliders
		updateStatus(id.equals("V"), 
					 id.equals("V")?veo.isUp():veo.isRight(),
					 true);
		}

	public String getImageName()
		{
		String name=null;
		if (getImageServer()!=null)
			name=getImageServer().getImageName();
		return name;
		}

	public ImageServer getImageServer()
		{
		return imageServer;
		}
	}  //  @jve:decl-index=0:visual-constraint="10,10"