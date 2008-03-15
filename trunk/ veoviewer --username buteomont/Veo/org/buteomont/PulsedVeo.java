package org.buteomont;

import java.io.IOException;
import java.net.UnknownHostException;

import net.halo3.veo.Veo;

import org.buteomont.util.CommandListener;
import org.buteomont.util.PulseListener;

public class PulsedVeo extends Veo implements PulseListener, CommandListener
	{
	private int hStep=0;
	private int vStep=0;
	private int max=0;
	private boolean up;
	private boolean right;
	public static final int MODE_STEPS=0;
	public static final int MODE_PIXELS=1;
	
	public PulsedVeo(String host, int port, int maxSteps) throws UnknownHostException, IOException
		{
		super(host, port);
		setMax(maxSteps);
		setHStep(maxSteps/2);
		setVStep(maxSteps/2);
		}

	public synchronized void pulsed(Object source, String id)
		{
		if (id.equals("H"))
			{
			if (hStep>=max)
				{
				hStep=0;
				right=!right;
				}
			try
				{
				if (right) moveCamera(VEO_MOVE_RIGHT);
				else moveCamera(VEO_MOVE_LEFT);
				hStep++;
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		else if (id.equals("V"))
			{
			if (vStep>=max)
				{
				vStep=0;
				up=!up;
				}
			try
				{
				if (up) moveCamera(VEO_MOVE_UP);
				else moveCamera(VEO_MOVE_DOWN);
				vStep++;
				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}

		}

	public int getMax()
		{
		return max;
		}

	public void setMax(int max)
		{
		this.max=max;
		}

	public int getHStep()
		{
		return hStep;
		}

	public void setHStep(int step)
		{
		this.hStep=step;
		}

	public int getVStep()
		{
		return vStep;
		}

	public void setVStep(int step)
		{
		this.vStep=step;
		}

	public boolean isRight()
		{
		return right;
		}

	public boolean isUp()
		{
		return up;
		}

	public void command(String commandString)
		{
		int mode=MODE_STEPS;
		String[] nvp=commandString.toLowerCase().split("&");
		for (String name:nvp)
			{
			String[] orders=name.split("=");
			if (orders.length>1)
				{
				char cmd=orders[0].charAt(0);
				int quan=0;
				try
					{
					if (isANumber(orders[1]))
						{
						String[] val=orders[1].split("\\.");
						if (val.length>0)
							quan=Integer.parseInt(val[0]);
						else
							quan=Integer.parseInt(orders[1]);
						}
					float numerator=1;
					float denominator=1;
					switch (cmd)
						{
						case 't':
							if (orders[1].charAt(0)=='p')
								mode=MODE_PIXELS;
							if (orders[1].charAt(0)=='s')
								mode=MODE_STEPS;
							continue;
						case 'u':
						case 'd':
							numerator=getStreams()[streamIndex].getHeight()*1.4f;
							denominator=MAX_VERTICAL;
							break;
						case 'l':
						case 'r':
							numerator=getStreams()[streamIndex].getWidth()*2.7f;
							denominator=MAX_HORIZONTAL;
							break;
						default:
							continue;
						}
					if (mode==MODE_PIXELS)
						quan=(int)(quan/(numerator/denominator));
					while (quan-->0)
						{
						switch (cmd)
							{
							case 'u':
								moveCamera(VEO_MOVE_UP);
								break;
							case 'd':
								moveCamera(VEO_MOVE_DOWN);
								break;
							case 'l':
								moveCamera(VEO_MOVE_LEFT);
								break;
							case 'r':
								moveCamera(VEO_MOVE_RIGHT);
								break;
			
							default:
								break;
							}
						Thread.sleep(100);
						}
					}
				catch (Exception e)
					{
					e.printStackTrace();
					
					}
				}
			}
		}
	/**
	 * Tests a String to see if it can be converted to a floating point number.
	 * @param possibleNumber String representation of a number, or not.
	 * @return boolean
	 */
	public final static boolean isANumber(String possibleDecimalNumber)
		{
		boolean isNumeric=false;
		try 
			{
			Float.valueOf(possibleDecimalNumber);  //Test for numeric
			isNumeric=true;
			}
		catch(NumberFormatException e)
			{
			// Must not be a number
			}
		return isNumeric;
		}

	}
