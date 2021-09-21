package Tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

//import framework_global.*;
import FrameworkSource.global.DataReader;
//import framework_global.reporter.*;
import FrameworkSource.global.reporter.ReportEvents;
import FrameworkSource.global.reporter.ReportGenerator;
//import framework_web.*;
import FrameworkSource.web.Browser;
import FrameworkSource.web.Page;
import FrameworkSource.web.WebElement;

public class SparkPreProcessingBlogs {

	@Test
	public void main() throws Exception
	{
	DataReader datareader = new DataReader("SparkPreProcessingBlogs");
	new SparkPreProcessingBlogs().new TestFlow().driver(datareader);
	}


private class TestFlow {
	
	
	
	TestFlow()
	{
		ReportEvents.ScreenShotRequired("false");
	}

	Browser browser = new Browser();
	Page page = new Page(browser);
	HashMap<String, String> testdata, commondata;
	private void driver(DataReader datareader) throws Exception
	{
		String browser[] = datareader.browser();
		commondata = datareader.getCommomData();

		for(int i=0;i<datareader.noOfTimes();i++)
		{
			testdata = datareader.getTestData(i);
			for(int j=0;j<datareader.noOfBrowsers();j++)
			{
				try
				{
				//Call methods
				open_browser(browser[j],datareader.getURL());
				//login();
				navigateToPreProcessing();
				
				//close_browser();
				}
				finally
				{
					ReportGenerator.Generate("false");
				}
			}
		}
	}

	private void open_browser(String browserType, String url) throws Exception
	{
		browser.InitiateBrowser(browserType);
		browser.Maximize();
		browser.NavigateURL(url);
	}


	private void login() throws IOException, InterruptedException
	{
		page.SetCurrentPage("LandingPage");
		new WebElement(browser,"RAAVNSignIn").Click();
		
		String actualTitle = page.GetTitle();
		System.out.println(actualTitle);
		String expectedTitle = testdata.get("PageTitle");
		if(expectedTitle.equals(actualTitle))
			ReportEvents.Reporter("Pass", "Page Validation", "User successfully clicked on RAAVN Sign In button and "
					+ "navigated to Home Page");
		else
			ReportEvents.Reporter("Pass", "Page Validation", "User is not able to login into the RAAVN application");
		
		
	}

	private void navigateToJAMS() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		
	}
	
	private void submitASession()
	{
		
	}
	
	
	private void navigateToPreProcessing() throws AWTException, IOException, InterruptedException, ParseException
	{
		String DATEFORMAT = "yyyy/MM/dd HH:mm:ss";
		Date largestDate;
		ArrayList al = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    String utcTime = sdf.format(new Date());
	    System.out.println("UTC time when session is submitted: "+ utcTime);
	    Date date2 =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(utcTime);  
	    System.out.println("Date 2 is: "+ date2);
	    largestDate= date2;
	    
		
		Robot robot = new Robot();                          
		robot.keyPress(KeyEvent.VK_CONTROL); 
		robot.keyPress(KeyEvent.VK_T); 
		robot.keyRelease(KeyEvent.VK_CONTROL); 
		robot.keyRelease(KeyEvent.VK_T);
		 
		//Switch focus to new tab
		ArrayList<String> tabs = new ArrayList<String> (browser.driver.getWindowHandles());
		browser.driver.switchTo().window(tabs.get(1));
		 
		//Launch URL in the new tab
		String url= testdata.get("PreProcessingURL");
		browser.driver.get(url);
		
		
		page.SetCurrentPage("PreProcessing");
		
		Thread.sleep(20000);
		
		
		for(int i=0; i<2;i++)
		{
			System.out.println("i is"+ i);
			browser.Refresh();
		List<org.openqa.selenium.WebElement> list  = browser.driver.findElements(By.xpath("//td[contains(@id,'batch')]/a"));
		for(org.openqa.selenium.WebElement ele: list)
		{
			String val = ele.getText();
			Date date1 =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(val);
		
			if(date1.compareTo(largestDate)>0)
			{
				System.out.println("element date fetched is: "+ date1);	
				al.add(date1);
				
				String recordValue = ele.findElement(By.xpath("//td[contains(text(),'records')]")).getText();
				//Conversion of string into int
				String[] rec =recordValue.split(" ");
				String firstValue = rec[0];
				System.out.println("firstValue is: "+ firstValue);
				int recordIntValue = Integer.parseInt(firstValue);
				if(recordIntValue>0 )
					{
						Actions action = new Actions(browser.driver);
						//Open in new tab
						action.keyDown(Keys.CONTROL).click(ele).keyUp(Keys.CONTROL).build().perform();
						batchDetails();
						
					}
			}

		}
		System.out.println("last highest value is: "+ al.get(0));
		largestDate= (Date) al.get(0);
		al.clear();

		}
		
	}
	
	private void batchDetails()
	{
		ArrayList<String> tabs = new ArrayList<String> (browser.driver.getWindowHandles());
		browser.driver.switchTo().window(tabs.get(2));
		
		//code
		System.out.println("hi");
		browser.driver.switchTo().window(tabs.get(1));
		
		
	}
	
		private void close_browser() throws Exception
	{
		browser.Quit();
	}

}}

