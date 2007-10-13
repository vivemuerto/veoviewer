package org.buteomont.util;

import java.io.*;
import java.util.*;

/**
 * Saves and restores configuration information
 *
 */
public class Initializer
	{
	/**
	 * Name-value pairs of configuration data.  Interpreted externally.
	 */
	Map data=new HashMap();
	String filename;
	
	private Initializer()
		{
		super();
		}

	public Initializer(String filename)
		{
		this();
		this.filename=filename;
		}

	/**
	 * Initialize the data map from a disk file.
	 * @throws Exception
	 */
	public void hydrate() throws Exception
		{
		data.clear();
		String pairs=new String(readFile(filename));
		for (StringTokenizer lines=new StringTokenizer(pairs,"\n");lines.hasMoreTokens();)
			{
			String name=null;
			String value=null;
			StringTokenizer line=new StringTokenizer(lines.nextToken(),"=");
			if (line.hasMoreTokens()) name=line.nextToken();
			if (line.hasMoreTokens()) value=line.nextToken();
			if (value!=null)
				data.put(name, value);
			}
		}

	/**
	 * Save the  data to a disk file in text format.
	 * @throws IOException
	 */
	public void persist() throws IOException
		{
		boolean append=false;
		for (Iterator i=data.keySet().iterator();i.hasNext();)
			{
			String name=(String)i.next();
			String value=(String)data.get(name);
			writeFile(filename, 
					 (name+"="+value+"\n").getBytes(),
					 append);
			append=true;
			}
		}

	/**
	 * Reads a file. 100% Java compliant (no system calls)
	 * 
	 * @param String sourceFileName
	 */
	public byte[] readFile(String sourceFileName) throws java.io.IOException
		{
		java.io.InputStream in=null;
		byte[] buf=null;
		try
			{// try from disk file first
			in=new java.io.FileInputStream(sourceFileName);
			}
		catch (FileNotFoundException e)
			{// no file, try from all jars in the classpath
			in=getClass().getResourceAsStream("/"+sourceFileName);
			}
		try
			{
			StringBuffer sb=new StringBuffer();
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			String line=null;
			while ((line=br.readLine())!=null)
				{
				sb.append(line).append("\n");
				}
			buf=sb.toString().getBytes();
			}
		finally
			{
			in.close();
			}

		return buf;
		}

	/**
	 * Writes a file.  100% Java compliant (no system calls)
	 * 
	 * @param String fileName
	 * @param byte[] contents
	 * @param boolean append
	 */
	public void writeFile(String fileName, byte[] contents, boolean append)
		throws java.io.IOException
		{
		java.io.FileOutputStream out=null;

		try 
			{
		  out = new java.io.FileOutputStream(fileName, append);
		  out.write(contents);
			}
		finally 
			{
			out.close();
			}
		}

	/**
	 * @return Returns the data.
	 */
	public Map getData()
		{
		return data;
		}

	/**
	 * @param data The data to set.
	 */
	public void setData(Map data)
		{
		this.data=data;
		}
	
	public String get(String name)
		{
		return (String)data.get(name); 
		}

	}
