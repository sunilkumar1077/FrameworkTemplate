package FrameworkSource.web;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import FrameworkSource.global.reporter.*;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
/**
 * The class consists of all the functions relates to List of web page.
 */
public class DropDown extends WebUIElement{
	/**
	 * List class constructor, directly invoked when an object of List class is created. Class List extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	public DropDown(Browser b,String Elementvalue)
	{
		//<select> and <li> tag
		super(b,Elementvalue);
	}
	
	public DropDown(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
	
	/**
	 * The function selects an element from a List <li> tag.
	 * @param value takes string value which needs to be selected from a list.
	 * @throws IOException 
	 */
	public void SelectItemByText(String value) throws IOException{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
	try
	{	List<WebElement> Language = b.element.findElements(By.tagName("li"));
		boolean presence =false;
		for (WebElement li : Language) 
		{			
			if (li.getText().equals(value)) 
			{
			     li.click();
			     ReportEvents.Done(callerClassName+":DropDown",value+" is Clicked from the list.",b);
			     //Logger.INFO("DropDown", value+" is Clicked from the list.");
			     presence = true;
			     break;
			}			
		}
		if (presence==false)
		{
			ReportEvents.Done(callerClassName+":DropDown",value+" is not listed in the list.",b);
				//Logger.INFO("DropDown", value+" is not listed in the list.");
		}		 
	}		
	catch(Exception e)
		{
		ReportEvents.Error(callerClassName+":DropDown", e,b);
		//Logger.ERROR("DropDown",e);
		//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * The function selects an element from a select tag <select>.
	 * @param value takes string value which needs to be selected from a list.
	 * Example: In <select id="SelectID"><option value="value1">Sunlife</option></select> Sunlife is the value passed as parameter
	 * @throws IOException 
	 */
	public void SelectByText(String value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
	try
	{
		Select dropdown= new Select(b.element);
		dropdown.selectByVisibleText(value);
		ReportEvents.Done(callerClassName+":DropDown",value+" is selected from the dropdown.",b);
		//Logger.INFO("DropDown", value+" is selected from the dropdown.");
	}
	catch(Exception e)
	{
		ReportEvents.Error(callerClassName+":DropDown", e,b);
    // Logger.ERROR("DropDown",e);
		//Assert.fail(); removing assert.fail as it is breaking multiple iterations
	}
	}
	
	/**
	 * The function selects an element based on vaue parameter defined under <option> tag.
	 * @param value takes string value from value tag.
	 * Example: In <select id="SelectID"><option value="value1">Sunlife</option></select> value1 is the value passed as parameter
	 * @throws IOException 
	 */
	public void SelectByValue(String value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
	try
	{
		Select dropdown= new Select(b.element);
		dropdown.selectByValue(value);
		ReportEvents.Done(callerClassName+":DropDown",value+" is selected from the dropdown.",b);
		//Logger.INFO("DropDown", value+" is selected from the dropdown.");
	}
	catch(Exception e)
	{
		ReportEvents.Error(callerClassName+":DropDown", e,b);
     //Logger.ERROR("DropDown",e);
		//Assert.fail(); removing assert.fail as it is breaking multiple iterations
	}
	}
	
/**
 * The function selects an element from a select tag depending upon the index value.
 * @param value takes an integer value.
 * @throws IOException 
 */
	public void SelectByIndex(int value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		Select dropdown= new Select(b.element);
		dropdown.selectByIndex(value);
		ReportEvents.Done(callerClassName+":DropDown", value+" is selected from the dropdown.",b);
		//Logger.INFO("DropDown", value+" is selected from the dropdown.");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":DropDown", e,b);
	    // Logger.ERROR("DropDown",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		
	}
	
	/**
	 * The function retrieves the value of current element which is selected.
	 * @param value takes an integer value.
	 * @throws IOException 
	 */
	public String GetSelectedText() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		String SelectedText = null;
	try
	{
		Select dropdown= new Select(b.element);
		WebElement dropdownoption = dropdown.getFirstSelectedOption();
		SelectedText =  dropdownoption.getText();
	}
	catch(Exception e)
	{
	ReportEvents.Error(callerClassName+":DropDown", e,b);
     Logger.ERROR("DropDown",e);
   //Assert.fail(); removing assert.fail as it is breaking multiple iterations
	}
	return SelectedText;
	}
	
	/**
	 * The function verifies currently selected drop-down text of select tag
	 * @param value takes an String value to be verified.
	 * @throws IOException 
	 */
	public boolean VerifySelectedText(String Value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		Boolean check = false;
	try
	{
		Select dropdown= new Select(b.element);
		WebElement dropdownoption = dropdown.getFirstSelectedOption();
		String SelectedText =  dropdownoption.getText();
		if (Value.equals(SelectedText))
		{
			check = true;
		}									
	}
	catch(Exception e)
	{
		ReportEvents.Error(callerClassName+":DropDown", e,b);
     Logger.ERROR("DropDown",e);
   //Assert.fail(); removing assert.fail as it is breaking multiple iterations
	}
	return check;
	}
	
	/**
	 * The function returns total elements in a list.
	 * @return integer value.
	 * @throws IOException 
	 */
	public int GetItemCount() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		Select dropdown= new Select(b.element);
		int elementcount = dropdown.getOptions().size(); 
		ReportEvents.Done(callerClassName+":DropDown", "Total Dropdown Items: "+ elementcount+" returned successfully.",b);
		//Logger.INFO("List","Total Dropdown Items: "+ elementcount+" returned successfully.");
		return elementcount;	
	}

	/**
	 * The function verifies whether an element is present in drop-down or not.
	 * @param value takes an String value to be verified.
	 * @throws IOException 
	 */  
	public Boolean VerifyTextListedInDropDown(String value) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		Boolean found = false;
		try
		{	
		Select dropdown= new Select(b.element);
		List<WebElement> dropdownoptions = dropdown.getOptions();
		for(WebElement we:dropdownoptions)  
        {   if (we.getText().equals(value))
               {
            	   found = true;
            	   ReportEvents.Done(callerClassName+":DropDown", "Text Exits in DropDown",b);
            	  // Logger.INFO("DropDown", "Text Exits in DropDown");
            	   break;
                } 
           }  		
		if (found==false)
		{
			ReportEvents.Done(callerClassName+":DropDown", value+" is not listed in the DropDown.",b);
			//Logger.INFO("DropDown", value+" is not listed in the DropDown.");
		}
		}		
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":DropDown", e,b);
			//Logger.ERROR("DropDown",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		return found;
		
	}

}
