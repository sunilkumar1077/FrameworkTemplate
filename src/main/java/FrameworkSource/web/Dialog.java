package FrameworkSource.web;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import FrameworkSource.global.reporter.*;
/**
 * Dialog class consists of all the functions related to web/javascript alerts, confirmation and prompts.
 */
public class Dialog{
	Browser b;
	/**
	 * Dialog class constructor, directly invoked when an object of Dialog class is created. 
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 */
	public Dialog(Browser b)
	{
		this.b = b;
	}

	/**
	 * The function switches from the current web page to active open pop up and accepts it.
	 * @throws IOException 
	 */
	public void Click() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		Alert alert= b.driver.switchTo().alert();
		alert.accept();
		ReportEvents.Done(callerClassName+":Dialog",  "Pop up is accepted",b);
		//Logger.INFO("Dialog", "Pop up is accepted");
		}
		catch(Exception e)
		{
		ReportEvents.Error(callerClassName+":Dialog", e, b);
	     //Logger.ERROR("Dialog",e);
		//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	/**
	 * The function switches from the current web page to active open pop up and closes it.
	 * @throws IOException 
	 */
	public void Close() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		Alert alert= b.driver.switchTo().alert();
		alert.dismiss();
		ReportEvents.Done(callerClassName+":Dialog",  "Pop up is Closed",b);
		//Logger.INFO("Dialog", "Pop up is Closed");
		}
		catch(Exception e)
		{ 
			ReportEvents.Error(callerClassName+":Dialog", e, b);
	    // Logger.ERROR("Dialog",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	/**
	 * The function checks the existence of the alerts or pop up.
	 * @return boolean value. If alets are found then true value is returned else false.
	 */
	public boolean Exists()
	{
	
		boolean flag = false;
		Alert alert= b.driver.switchTo().alert();
		if(alert != null)
		{
			flag = true;
		}
		return flag;
		
	}
	/**
	 * The function switches from the current web page to active open pop up and returns the test written in it.
	 * @return String value.
	 * @throws IOException 
	 */
	public String GetVisibleText() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
	
		Alert alert= b.driver.switchTo().alert();
		String value= alert.getText();
		ReportEvents.Done(callerClassName+":Dialog",   "Pop up text :" + value + " is returned successfully.",b);
		//Logger.INFO("Dialog", "Pop up text :" + value + " is returned successfully.");
		return value;	
	}
	
	/**
	 * The function switches from the current web page to active open pop up and pass the text to it.
	 * @return String value.
	 * @throws IOException 
	 */
	public void SendKeys(String value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		Alert alert= b.driver.switchTo().alert();
		alert.sendKeys(value);
		ReportEvents.Done(callerClassName+":Dialog",  "Pop up text :" + value + " is passed successfully.",b);
		//Logger.INFO("Dialog", "Pop up text :" + value + " is passed successfully.");
	}
}
