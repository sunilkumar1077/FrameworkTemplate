package FrameworkSource.web;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import FrameworkSource.global.reporter.*;
/**
 * The class consists of all the functions relates to Checkbox of web page.
 */
public class CheckBox extends WebUIElement {
	/**
	 * CheckBox class constructor, directly invoked when an object of CheckBox class is created. Class Checkbox extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	public CheckBox(Browser b,String Elementvalue)
	{
		super(b,Elementvalue);
	}
	
	public CheckBox(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
	/**
	 * The function checks if Check Box is selected or not. If it is selected then no operation is performed, else Check Box is selected.
	 * @throws IOException 
	 */
	public void SelectCheckbox() throws IOException
	{	
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try
		{
		if(b.element.isSelected())
	        {
				ReportEvents.Done(callerClassName+":CheckBox", "Checkbox "+ b.element +" already checked",b);
				//Logger.INFO("CheckBox", "Checkbox "+ b.element +" already checked");
	        }
	        else
	        {
	          b.element.sendKeys(Keys.SPACE); 
	          ReportEvents.Done(callerClassName+":CheckBox","Checkbox "+ b.element +" successfully checked",b);
	         // Logger.INFO("CheckBox", "Checkbox "+ b.element +" successfully shecked");
	        }
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":CheckBox", e, b);
			 //Logger.ERROR("CheckBox",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}

	public void DeselectCheckbox() throws IOException
	{	
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try	{
		if(b.element.isSelected())
	        {
			  b.element.sendKeys(Keys.SPACE); 
			  ReportEvents.Done(callerClassName+":CheckBox", "Checkbox "+ b.element +" successfully unchecked",b);
			  //Logger.INFO("CheckBox", "Checkbox "+ b.element +" successfully unchecked");
	        }
	        else
	        	ReportEvents.Done(callerClassName+":CheckBox","Checkbox "+ b.element +" already unchecked",b);
				//Logger.INFO("CheckBox", "Checkbox "+ b.element +" already unchecked");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":CheckBox", e, b);
			// Logger.ERROR("CheckBox",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
}
