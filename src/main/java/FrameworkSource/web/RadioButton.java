package FrameworkSource.web;

import java.io.IOException;

import FrameworkSource.global.reporter.*;
import org.junit.Assert;
/**
 * The class consists of all the functions related to RadioButton Class.
 */
public class RadioButton extends WebUIElement {
	/**
	 * Radio Button class constructor, directly invoked when an object of Radio button class is created. Class radio button extends WebUIElement class.
	 * @param b is a variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */	
	public RadioButton(Browser b,String Elementvalue)
	{
		super(b,Elementvalue);
	}
	
	public RadioButton(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
	
	/**
	 * The function selects the radio button. If it is already selected then no operation is performed, else radio button will get selected.
	 * @throws IOException 
	 */
	public void SelectRadiobutton() throws IOException
	{	
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try
		{		
		if(b.element.isSelected())
	        {
			ReportEvents.Done(callerClassName+":Radiobutton","Radiobutton " + b.element +" already deselected",b);
			//Logger.INFO("Radiobutton",  "Radiobutton " + b.element +" already deselected");
	        }
	        else
	        {
	          b.element.click(); 
	          ReportEvents.Done(callerClassName+":Radiobutton","Radiobutton " + b.element +" successfully selected",b);
	          //Logger.INFO("Radiobutton", "Radiobutton " + b.element +" successfully selected");
	        }
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Radiobutton", e,b);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterationsAssert.fail();
			// Logger.ERROR("Radiobutton",e);
		}
			
	}
	
}
