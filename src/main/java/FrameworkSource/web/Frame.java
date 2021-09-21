package FrameworkSource.web;

import java.io.IOException;
import org.junit.Assert;
import org.openqa.selenium.By;


import FrameworkSource.global.reporter.*;
public class Frame extends WebUIElement
{
	
	public Frame(Browser b, String Elementvalue)
	{
		super(b,Elementvalue);
		
	}
	
	public Frame(Browser b)
	{
		super(b);
	}
	
	public Frame(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
	
	/**
	 * Switch to the Frame element found in the page
	 * new Frame(browser,"logicalname").SwitchFrame();
	 * @throws IOException 
	 * 
	 */
	public void SwitchFrame() throws IOException 
	{
	String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try
		{
		b.driver.switchTo().frame(b.element);
		ReportEvents.Done(callerClassName+ ":Frame", "Sucessfully switched to the Frame element: "+b.element);
		}
		catch(Exception e)
		{
			ReportEvents.Fatal(callerClassName+":Frame","Cannot switch to Frame element: "+b.element);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * Switch to the Frame directly by name or id. 
	 * For Example: new Frame(browser).SwitchFrame("IdOrName");
	 * @param IdOrName takes String values for id and name.
	 * @throws IOException 
	 * 
	 */
	public void SwitchFrame(String IdOrName) throws IOException 
	{
	String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try
		{
		b.driver.switchTo().frame(IdOrName);
		ReportEvents.Done(callerClassName+ ":Frame", "Sucessfully switched to the Frame: "+IdOrName);
		}
		catch(Exception e)
		{
			ReportEvents.Fatal(callerClassName+":Frame","Cannot switch to Frame: "+IdOrName);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * Switch to the Frame directly index 
	 * @param index takes integer value for the frame. Eg.1 for first frame, 2 for second frame
	 * For Example: new Frame(browser).SwitchFrame(1);
	 * @throws IOException 
	 * 
	 */
	public void SwitchFrame(int index) throws IOException 
	{
	String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try
		{
		b.driver.switchTo().frame(index-1);
		ReportEvents.Done(callerClassName+ ":Frame", "Sucessfully switched to the Frame: "+index);
		}
		catch(Exception e)
		{
			ReportEvents.Fatal(callerClassName+":Frame","Cannot switch to Frame: "+index);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * Find total number of frames in a page.
	 * int size = new Frame(browser).NoOfFrames();
	 * @throws IOException 
	 * 
	 */
	public int NoOfFrames() throws IOException 
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		int total = 0;
		try
		{
		total = b.driver.findElements(By.tagName("iframe")).size();
		ReportEvents.Done(callerClassName+ ":Frame", "Total no. of frames are: "+total);
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Frame",e);	
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		return total;
	}
	
	/**
	 * Switching to the Original page from Frame
	 * When Working on Frames need to come back to the Default content/page
	 * new Frame(browser).SwitchOutOfFrame();
	 * @throws IOException
	 */	
	public void SwitchOutOfFrame() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try
		{
		   b.driver.switchTo().defaultContent();
		   ReportEvents.Done(callerClassName+":Page","Page Switched from Frame to Default content/page");
		}
		
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Page", e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		
		
	}
	
	
}
	
	
	

