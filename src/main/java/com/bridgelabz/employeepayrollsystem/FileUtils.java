package com.bridgelabz.employeepayrollsystem;

import java.io.File;

public class FileUtils 
{
	public static boolean deleteFile(File contentToDelete)
	{
		File[] allContents = contentToDelete.listFiles();
		if (allContents!=null)
		{
			if (contentToDelete.isDirectory())
			{
				deleteFile(contentToDelete);
			} 
			else 
			{
				contentToDelete.delete();
			}
		}
		return contentToDelete.delete();
	}
}
