package FrameworkSource.web;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import CentralConfig.DB_UpdateMetrics;
import FrameworkSource.global.DataReader;
import FrameworkSource.global.reporter.*;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * The class consists of all the customized functions related to browsers.
 */
public class Browser {
	
	public  WebDriver driver = null;
	public  String[][] PageValues = null;	
	public  int rowCount = 0;
	public  int columnCount = 0;
	public  String[] FoundValue = null;
	public  WebElement element;
	public String strLanguage = null;
	public List<WebElement> multiple_element;
	
/**
* This function launches a browser version either Chrome, IE or Firefox.
* @param BrowserType is a string parameter which takes either values of Chrome, IE or Firefox.
* @throws InterruptedException
 * @throws IOException 
*/
public void InitiateBrowser(String BrowserType) throws InterruptedException, IOException
	{	
	String strLangParam  = new Exception().getStackTrace()[1].getClassName();
	String strTestCase = new Exception().getStackTrace()[1].getFileName();
	
	if(strLangParam.contains("_Fr")||strLangParam.contains("_fr")||strLangParam.contains("_FR"))
	{
		strLanguage = "French";
	}
	else
	{
		strLanguage = "English";
	}
	//if(abc.split("$")[0]
	String callerClassName = new Exception().getStackTrace()[1].getMethodName();
	if(DataReader.mapIteration.containsKey(strTestCase.replace(".java", "")))
	{
		DataReader.mapIteration.put(strTestCase.replace(".java", ""), DataReader.mapIteration.get(strTestCase.replace(".java", "")) + 1);
		//ReportEvents.Done("Pool:" + iTestCaseCount + "::Thread Count: 1::Iterations" + iIterations + "Feature Name: "+ strFeatureName + "Tests."+ strTestCaseName +"$",  "Execution Started");
	}
	
		try{
			
			
			switch(BrowserType.toLowerCase())
			{
				case "chrome":
				try{
					WebDriverManager.chromedriver().setup();
				}catch(Exception e)
				{
					System.setProperty("webdriver.chrome.driver",  System.getProperty("user.dir") + "\\src\\test\\resources\\browserdrivers\\chromedriver.exe");
				}
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--disable-gpu");
				driver = new ChromeDriver(chromeOptions); 
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				ReportEvents.Done(callerClassName+":Browser", "Google Chrome is Launched : "+ driver);
				//Logger.INFO("Browser", "Google Chrome is Launched : "+ driver);	
				break;
				
				case "ie":
					try{			
				WebDriverManager.iedriver().setup();
					}catch(Exception e)
					{
						File ieFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\browserdrivers\\IEDriverServer.exe");
						System.setProperty("webdriver.ie.driver", ieFile.getAbsolutePath());
					}
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability("ignoreZoomSetting", true);
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				ieCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, " ");
				driver = new InternetExplorerDriver(ieCapabilities);
				//driver = new InternetExplorerDriver(ieCaps);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				ReportEvents.Done(callerClassName+":Browser", "IE is Launched : "+ driver);
				//Logger.INFO("Browser", "IE is Launched : "+ driver);	
				break;
				
				case "firefox":
					try{
					WebDriverManager.firefoxdriver().setup();
				}catch(Exception e)
				{
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\browserdrivers\\geckodriver.exe");
				}
				driver = new FirefoxDriver(); 
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				ReportEvents.Done(callerClassName+":Browser", "Firefox is Launched : "+ driver);
				//Logger.INFO("Browser", "Firefox is Launched : "+ driver);	
				break;
				
				default: 
					driver=null;
					ReportEvents.Done(callerClassName+":Browser", "Invalid browser name : "+ BrowserType);
					//Logger.INFO("Browser", "Invalid browser name : "+ BrowserType);	
			}
		
		}
		
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
		     Assert.fail();
			}
		
	}
/**
 * The function opens a URL in the launched browser.
 * @param url takes a String value.
 * @throws IOException 
 */
	public void NavigateURL(String url) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.get(url);
		ReportEvents.Done(callerClassName+":Browser", "Opening URL "+ url);
		//Logger.INFO("Browser","Opening URL "+ url);
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
		     Assert.fail();
		}
		
	}
	
	/**
	 * The function opens a URL in the launched browser.
	 * @param url takes a String value.
	 * @throws IOException 
	 */
		public void NavigateURL_JavaScriptExecutor(String url) throws IOException
			{
				String callerClassName = new Exception().getStackTrace()[1].getMethodName();
				JavascriptExecutor js = (JavascriptExecutor)driver;
				try{
					js.executeScript(
							"window.location = '"+url+"'");
				}
				catch(Exception e)
				{
					ReportEvents.Error(callerClassName+":Browser", e);
				}
				
			}
	
	/**
	 * The function closes the open browser.
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void Quit() throws IOException, ParseException, ClassNotFoundException, SQLException
	{
		String callerClassName = new Exception().getStackTrace()[1].getFileName();
		
		try{
			try {
				driver.quit();}
			catch(Exception e){
				driver.close();}
			
			ReportEvents.Done(callerClassName+":Browser",  "Browser is Closed");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
		     Assert.fail();
		}
		
		fnUpdateMetricesAfterClose(callerClassName);
	}
	
private void fnUpdateMetricesAfterClose(String callerClassName) throws ParseException, SQLException, ClassNotFoundException, IOException {
		
		DB_UpdateMetrics objM = new DB_UpdateMetrics();
		Calendar cal = null;
		Timestamp time,StarttimeStamp;
		String strStatus = "";
		DateFormat dtEndTimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Date dateEndTime = new Date();
		String strEndDate = dtEndTimeFormat.format(dateEndTime);
		String strIterationStatus = "-1";
		int iIterations = -1;
		
		StarttimeStamp = objM.getStartingTimestamp(strEndDate); //Store the Starting Time from application.html session start time
		cal = Calendar.getInstance();
		cal.setTimeInMillis(StarttimeStamp.getTime());
		time = new Timestamp( cal.getTime().getTime());
		
		try{
			strStatus = ExtentTestManager.getTest().getStatus().toString();
			
		}catch(Exception eResult)
		{
			strStatus = "Failed";
		}
		
		if (strStatus.equalsIgnoreCase("fail"))
		{
			strStatus = "Failed";
			strIterationStatus = "0";
		}
		else if (strStatus.equalsIgnoreCase("pass"))
		{
			strStatus = "Passed";
			strIterationStatus = "1";
		}

		if(DataReader.mapIterationStatus.containsKey(callerClassName.replace(".java", "")))
		{
			DataReader.mapIterationStatus.put(callerClassName.replace(".java", ""),DataReader.mapIterationStatus.get(callerClassName.replace(".java", "")) + ","+strIterationStatus);
		}
		else
		{
			
			DataReader.mapIterationStatus.put(callerClassName.replace(".java", ""), strIterationStatus);
		}
		objM.InitiateDBconn();
		if(DataReader.mapDBMetricesData.containsKey(callerClassName))
		{
			if(DataReader.mapIteration.containsKey(callerClassName.replace(".java", "")))
			{
				iIterations = DataReader.mapIteration.get(callerClassName.replace(".java", ""));
			}
			objM.fnRowUpdateM(DataReader.mapDBMetricesData.get(callerClassName), iIterations, time, strStatus,DataReader.mapIterationStatus.get(callerClassName.replace(".java", "")));
		}
		
		
	}
	/**
	 * The function Maximizes the browser.
	 * @throws IOException 
	 */
	public void Maximize() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.manage().window().maximize();
		ReportEvents.Done(callerClassName+":Browser",  "Browser Maximize");
		//Logger.INFO("Browser", "Browser Maximize");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
		     //Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		
	}
	/**
	 * The function switches to desired open windows of browser.
	 * @param windownumber takes integer value. Eg.1 for first window, 2 for second window
	 * @throws IOException 
	 */
	public void GetBrowser(int windownumber) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println(tabs.size());
		ReportEvents.Done(callerClassName+":Browser",  "Total No. of windows opened" + tabs.size());
		//Logger.INFO("Browser", "Total No. of windows opened" + tabs.size());
		driver.switchTo().window((String) tabs.get(windownumber-1));
		ReportEvents.Done(callerClassName+":Browser",  "Windows switched successfully");
		//Logger.INFO("Browser", "Windows switched successfully");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}

	}
	
	/**
	 * The function switches to desired open windows of browser.
	 * @param title takes the current page title.
	 * @throws IOException 
	 */
	public void GetBrowser(String title) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			System.out.println(tabs.size());
			ReportEvents.Done(callerClassName+":Browser",  "Total No. of windows opened" + tabs.size());
			//Logger.INFO("Browser", "Total No. of windows opened" + tabs.size());
			String currentWindow = driver.getWindowHandle();  //will keep current window to switch back
			for(String winHandle : driver.getWindowHandles()){
			   if (driver.switchTo().window(winHandle).getTitle().equals(title)) {
				   ReportEvents.Done(callerClassName+":Browser",  "Windows switched successfully");
					//Logger.INFO("Browser", "Windows switched successfully");
			     break;
			   } 
			   else {
			      driver.switchTo().window(currentWindow);
			   } 
			}
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}

	}
	
	/**
	 * The function clicks Back button of opened browser.
	 * @throws IOException 
	 */
	public  void Back() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.navigate().back();
		ReportEvents.Done(callerClassName+":Browser",  "Browser Back button is pressed successfully. ");
		//Logger.INFO("Browser", "Browser Back button is pressed successfully. ");	
	}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	/**
	 * The function clicks on the Forward button of opened browser.
	 * @throws IOException 
	 */
	public  void Forward() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.navigate().forward();
		ReportEvents.Done(callerClassName+":Browser",  "Browser Forward button is pressed successfully. ");
		//Logger.INFO("Browser", "Browser Forward button is pressed successfully. ");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
		
	}
	/**
	 * The function clears all cookies of the browser.
	 * @throws IOException 
	 */
	public void ClearCookies() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.manage().deleteAllCookies();
		ReportEvents.Done(callerClassName+":Browser",  "All Cookies has been deleted successfully. ");
		//Logger.INFO("Browser", "All Cookies has been deleted successfully. ");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * The function clears specific cookie by name.
	 * @throws IOException 
	 */
	public void ClearCookies(String name) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.manage().deleteCookieNamed(name);
		ReportEvents.Done(callerClassName+":Browser",  name+" cookie has been deleted successfully. ");
		//Logger.INFO("Browser", name+" cookie has been deleted successfully. ");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	
	/**
	 * The function refresh the opened open session of browser.
	 * @throws IOException 
	 */
	public void Refresh() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try{
		driver.navigate().refresh();
		ReportEvents.Done(callerClassName+":Browser",   "Browser Page Refresh successfully.");
		//Logger.INFO("Browser", "Browser Page Refresh successfully.");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * The function will kill any task.
	 * @throws IOException 
	 */	
	public void KillTask(String taskName) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		if(!taskName.contains(".exe"))
		{
			taskName = taskName + ".exe"; 
		}

		try{
		Runtime.getRuntime().exec("Taskkill /IM "+taskName+" /F");
		ReportEvents.Done(callerClassName+":Browser",   taskName+" killed successfully.");
		//Logger.INFO("Browser", "Browser Page Refresh successfully.");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Logger.ERROR("Browser",e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	/**
	 * The function inputs various keys from keyboard.
	 * @param command takes string value which needs to be input from keyboard like Down, Left, Up, Right, Backspace, Alt, Ctrl, Delete, Enter and Spacebar
	 * @param times takes a integer value of number of times the command is to be executed
	 * @throws IOException 
	 */
	public void PressKeyonPage(String command, int times) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String command1 = command.substring(0,1).toUpperCase() + command.substring(1).toLowerCase();
		Actions actions = new Actions(driver);
		try
		{			
			switch(command1)
			{
				case "Enter":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.ENTER).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","Enter Key is pressed successfully.");
				break;
							
				case "Down":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.DOWN).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","DownArrow Key is pressed successfully.");
				break;
				
				case "Up":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.UP).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","UpArrow Key is pressed successfully.");
				break;		
				
				case "Left":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.LEFT).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","UpArrow Key is pressed successfully.");
				break;	
				
				case "Right":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.RIGHT).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","UpArrow Key is pressed successfully.");
				break;	
					
				case "Backspace":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.BACK_SPACE).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","Backspace Key is pressed successfully.");
				break;
						
				case "Spacebar":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.SPACE).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","Spacebar Key is pressed successfully.");
				break;
				
				case "Tab":
				for(int i=1;i<=times;i++)
				{
					actions.sendKeys(Keys.TAB).build().perform();
				}
				ReportEvents.Done(callerClassName+":Browser","Spacebar Key is pressed successfully.");
				break;
				
				
				default: 
					ReportEvents.Fatal(callerClassName+":Browser", "Invalid key name.");		
					}	
			
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Browser", e);
			//Assert.fail(); removing assert.fail as it is breaking multiple iterations
		}
	}
	
	
}