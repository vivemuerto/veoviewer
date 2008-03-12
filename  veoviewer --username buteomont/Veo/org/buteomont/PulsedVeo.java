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

	public void command(byte cmd, int quan)
		{
		try
			{
			moveCamera(cmd);
			}
		catch (IOException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		}

	}
