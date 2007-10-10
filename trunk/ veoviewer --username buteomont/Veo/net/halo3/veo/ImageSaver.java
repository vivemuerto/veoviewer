package net.halo3.veo;

import java.io.*;

public interface ImageSaver
	{
	public void saveImage(File file);
	public void saveImage(OutputStream stream);
	}
