package FrameworkSource.web;

import java.io.IOException;

import org.junit.Assert;

import FrameworkSource.global.reporter.*;
public class TextBox extends WebUIElement{
	/**
	 * TextBox class constructor, directly invoked when an object of TextBox class is created. Class TextBox extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	public TextBox(Browser b,String Elementvalue)
	{
		
		super(b,Elementvalue);
	}
	
	public TextBox(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
	/**
	 * The function sends the string value to the text box.
	 * @param value takes string value.
	 * @throws IOException 
	 */
	public void SendKeys(String value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {
		if(value != null)
		{	
		b.element.clear();
		b.element.sendKeys(value);
		ReportEvents.Done(callerClassName+":TextBox", "Values are sucessfully entered in textbox."+value,b);
		}
		else
		{
			ReportEvents.Fatal(callerClassName+":TextBox", "Value entered is not correct i.e. ."+value,b);
		}
		}
		catch(Exception e){
			ReportEvents.Error(callerClassName+":TextBox",e,b);
		}
		//Logger.INFO("TextBox", "Values are sucessfully entered in textbox.");
	}
	/**
	 * The function clears the value present in the text box.
	 * @throws IOException 
	 */
	public void Clear() throws IOException
	{  		
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {
		b.element.clear();
		ReportEvents.Done(callerClassName+ ":TextBox", "Values are sucessfully cleared from textbox.",b);
	}
		catch(Exception e){
			ReportEvents.Error(callerClassName+":TextBox",e,b);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		//Logger.INFO("TextBox", "Values are sucessfully cleared from textbox.");
	}
}
