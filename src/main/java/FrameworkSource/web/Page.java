package FrameworkSource.web;

import FrameworkSource.global.*;
import FrameworkSource.global.reporter.*;
import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
/**
 * The class consists of all the functions relates to Page Class.
 */
public class Page{
	
	Browser b;
	/**
	 * Page class constructor, directly invoked when an object of Page class is created.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 */
	public Page(Browser b)
	{
		this.b = b;
	}
	/**
	 * The function sets page value depending upon the string value provided. This function calls Properties function in ORReaded class.	
	 * @param Value takes string value.
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws ParseException 
	 */
	public void SetCurrentPage(String Value) throws IOException, InterruptedException
	{
		b.PageValues= new ORReader(b).Properties(Value);
	}
	
	public void SetCurrentPage(String Value,String excelSheet) throws IOException, InterruptedException
	{
		b.PageValues= new ORReader(b).Properties(Value);
	}
	
	public void SetProperty(String strLogicalName, String strPropertyID, String strPropValue) throws IOException
	{
		new ORReader(b).fnSetProperty(strLogicalName, strPropertyID, strPropValue);
	}
	
	/**
	 * The function waits for specified number of seconds.	
	 * @param seconds takes value of type int.
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void Wait(int seconds) throws InterruptedException, IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		//int total = Integer.parseInt(seconds+"000");
		//Logger.INFO("Page", "Waited for "+seconds+" seconds");
		Thread.sleep(seconds*1000);
		ReportEvents.Done(callerClassName+":Page","Waited for "+seconds+" seconds.");
	}

	/**
	 * The function retrieves the title of page.
	 * @throws IOException 
	 */
	public String GetTitle() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		String title="";
		try {
		title=b.driver.getTitle();
		ReportEvents.Done(callerClassName+":Page","Page title returned: "+title);
		
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Page", e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		return title;
	}

	/**
	 * The function retrieves the title of page.
	 * @throws IOException 
	 */
	
	public String GetTitle_JavascriptExecutor() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		JavascriptExecutor js = (JavascriptExecutor) b.driver;
		String title="";
		try {
		title= js.executeScript("return document.title;").toString();
		ReportEvents.Done(callerClassName+":Page","Page title returned: "+title);
		
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Page", e);
		}
		return title;
	
	}
	
	/**
	 * The function retrieves the url of page.
	 * @throws IOException 
	 */
	
	public String GetURL_JavascriptExecutor() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		JavascriptExecutor js = (JavascriptExecutor) b.driver;
		String url="";
		try {
		url=js.executeScript("return document.URL;").toString();
		ReportEvents.Done(callerClassName+":Page","Page URL returned: "+url);
		
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Page", e);
		}
		return url;
	
	}
	
	/**
	 * The function retrieves the domain of page.
	 * @throws IOException 
	 */
	
	public String GetDomain_JavascriptExecutor() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		JavascriptExecutor js = (JavascriptExecutor) b.driver;
		String domain="";
		try {
		domain=js.executeScript("return document.domain;").toString();
		ReportEvents.Done(callerClassName+":Page","Page Domain returned: "+domain);
		
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Page", e);
		}
		return domain;
	
	}


	/**
	 * The function scroll the page as per pixel range defined in parameters.
	 * @param x_pixel and y_pixel takes value of type int. For scroll down x_pixel: 0 and y_pixel: 250. For scroll up x_pixel: 0 and y_pixel: -250
	 * @throws IOException 
	 */
	public void ScrollByPixel(int x_pixel, int y_pixel) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		JavascriptExecutor js = (JavascriptExecutor) b.driver;
		js.executeScript("window.scrollBy("+x_pixel+","+y_pixel+")");
		ReportEvents.Done(callerClassName+":Page","Page Scrolled by from: "+ x_pixel+" to: "+y_pixel);
		//Logger.INFO("Page", "Page Scrolled by from: "+ x_pixel+" to: "+y_pixel);
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Page", e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * The function scrolls the page till the end.
	 * @throws IOException 
	 */	
	public void ScrollTillEnd() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
			JavascriptExecutor js = (JavascriptExecutor) b.driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			ReportEvents.Done(callerClassName+":Page","Page Scrolled till the end.");
			}
			catch(Exception e)
			{
				ReportEvents.Error(callerClassName+":Page", e);
				//Assert.fail(); removing assert.fail as it is breaking multiple iterations
			}
	}
	
	/**
	 * The function scrolls the page to the end.
	 * @throws IOException 
	 */	
	public void ScrollToTop() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
			JavascriptExecutor js = (JavascriptExecutor) b.driver;
			js.executeScript("window.scrollTo(0, 0)");
			ReportEvents.Done(callerClassName+":Page","Page Scrolled to top.");
			}
			catch(Exception e)
			{
				ReportEvents.Error(callerClassName+":Page", e);
				//Assert.fail(); removing assert.fail as it is breaking multiple iterations
			}
	}
	
}