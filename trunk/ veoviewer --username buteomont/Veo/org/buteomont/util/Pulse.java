package org.buteomont.util;

import java.util.List;
import java.util.Vector;

public class Pulse extends Thread
	{
	/** Pulses per minute */
	private int rate;
	
	/** Unique ID provided by parent */
	private String identifier;
	
	/** Set to false to stop the pulses */
	private boolean going=true;
	
	/** List of PulseListeners to be called when a pulse occurs */
	private List<PulseListener> listeners=new Vector<PulseListener>();
	
	private Pulse()
		{
		super();
		}

	public Pulse(int rate, String id)
		{
		super();
		this.rate=rate;
		this.identifier=id;
		System.out.println("New Pulse created with ID of "+id
			+" and rate of "+rate+" pulses per minute.");
		}

	public void run()
		{
		while(going && getRate()>0)
			{
			try
				{
				sleep(60000/getRate());
				firePulse();
				}
			catch (InterruptedException e)
				{
				e.printStackTrace();
				}
			}
		}

	public void addListener(PulseListener listener)
		{
		getListeners().add(listener);
		}
	
	public String toString()
		{
		// TODO Auto-generated method stub
		return super.toString();
		}

	private void firePulse()
		{
//		System.out.println(getIdentifier()+" pulse.");
		for (Object listener:getListeners())
			((PulseListener)listener).pulsed(this, getIdentifier());
		}
	
	public boolean isGoing()
		{
		return going;
		}

	public void setGoing(boolean going)
		{
		this.going=going;
		}

	public List<PulseListener> getListeners()
		{
		return listeners;
		}

	public int getRate()
		{
		return rate;
		}

	public void setRate(int rate)
		{
		this.rate=rate;
		}

	public String getIdentifier()
		{
		return identifier;
		}

	}
