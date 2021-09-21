package FrameworkSource.global.reporter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.relevantcodes.extentreports.LogStatus;

import FrameworkSource.global.DataReader;
import FrameworkSource.global.Initialize.ClsIntialize;
import FrameworkSource.web.*;
/**
 * ReportsEvents class consists of functions dealing with configuration related to screenshots and customized data in reports
 */
public class ReportEvents extends ClsIntialize {
	private static Browser b;
	private static final String STRFALSE = "false";
	private static final String STRCONSOLE = "Console";
	private static final String STRFLAG = "Yes";
	private static final String STRHTMLPARTONE = "&nbsp;&nbsp;&nbsp;<a href='";
	private static final String STRHTMLPARTTWO = "' data-featherlight='image'><span class='label grey white-text'>S</span></a>";
	private static final String LOG_TYPE = "LogType";
	private static final String DEBUG = "debug";
	
	private static String Flag= "False";
	private static String Flag1= "False";
	private static List<String> Trace =new ArrayList<String>();
	
	/* These two flags provide the backward compatibility to the old scripts*/
	private static String strStepScreenShotRequired = STRFALSE;
	private static String strScreenShotRequired = STRFALSE; 
	
	public static String strReportPath;
	
	
	ExtentReports report;
	ExtentHtmlReporter html;
	ExtentTest test;
	
	/**
	* The constructor initializes Device class object
	*/
	public ReportEvents(Browser BrowObj)
	{
		b = BrowObj;
	}
	
	public static Browser fnGetDeviceObject()
	{
		return b;
		
	}
	
	public ReportEvents(String strTestCaseName)
	{
		try
		{
			DateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HHmmss");
			Date date = new Date();
			if (ExtentManager.getInstance() == null)
			{
				strReportPath = System.getProperty("user.dir")+"\\ExecutionReports\\Test_Report"+ dateformat.format(date) +".html";
				ExtentManager.createInstance(strReportPath);
				if(ClsIntialize.fnGetProp().getProperty("DeleteReportHistory").equalsIgnoreCase("yes"))
					DeleteFolder(new File( System.getProperty("user.dir")+"//ExecutionReports"));
			}
			ExtentTestManager.createTest(strTestCaseName);
			
		}catch(Exception e)
		{
			Logger.ERROR("error", e);
		}
	}
	
	public static String fnGetFlag()
	{
		return Flag;
	}
	
	public static String fnGetFlag1()
	{
		return Flag1;
	}
	
	/*public ReportEvents()
	{
		try
		{
			DateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy HHmmss");
			Date date = new Date();
			if (ExtentManager.getInstance() == null)
				ExtentManager.createInstance(System.getProperty("user.dir")+"\\ExecutionReports\\Test_Report"+ dateformat.format(date) +".html");
			ExtentTestManager.createTest(new Exception().getStackTrace()[1].getFileName());
		}catch(Exception e)
		{
			Logger.ERROR("error", e);
		}
	}*/
	
	/**ScreenShotRequired is only to provide backward compatibility to the old framework
	This function initializes the flag to invoke captureScreenshot*/
	public static void ScreenShotRequired(String flag)
	{
		if(flag.equalsIgnoreCase("true"))
		{
			strScreenShotRequired = flag;
		}
		else if(flag.equalsIgnoreCase(STRFALSE))
		{
			strScreenShotRequired = flag;
		}
		CaptureScreenShots(strScreenShotRequired,strStepScreenShotRequired);
	}
	
	/**Step ScreenShotRequired is only to provide backward compatibility to the old framework
	This function initializes the flag to invoke captureScreenshot*/
	public static void StepScreenShotRequired(String flag)
	{
		if(flag.equalsIgnoreCase("true"))
		{
			strScreenShotRequired = "true";

			strStepScreenShotRequired = flag;
		}
		else if(flag.equalsIgnoreCase(STRFALSE))
		{
			strStepScreenShotRequired = flag;
		}
		
		CaptureScreenShots(strScreenShotRequired,strStepScreenShotRequired);
	}

	/**
	 * CaptureScreenShots signifies if ScreenShots needs to be captured during the run.
	 * @param flag Mandatory: Parameter takes either "True" or "False" which signifies if Screenshot needs to be captured or not respectively.
	 * @param flag1 Optional: Parameter takes either "True" or "False" which signifies if implicit Screenshots will be captured at each step in test scripts.
	 */
	public static void CaptureScreenShots(String flag,Object... flag1)
	{
		if(flag1.length>0)
		{
			if(flag.equalsIgnoreCase("True"))
			{
				Flag = "true";
				Flag1=(String)flag1[0];
			}
			else
			{
				Flag = "false";
				Flag1 = "false";
			}
		}
	}
	
	private static void DeleteFolder(File folder) {
		   if (folder.isDirectory()) {
		          File[] list = folder.listFiles();
		          if (list != null) {
		              for (int i = 0; i < list.length; i++) {
		                  File tmpF = list[i];
		                  if (tmpF.isDirectory()) {
		                	  DeleteFolder(tmpF);
		                  }
		                  tmpF.delete();
		              }
		          }
		   }
		
	}
	public static void Done(String StepName, String StepDescription) throws IOException
	{
		String code="",ImageLocation = "";
		ExtentTestManager.getTest().info(StepName + StepDescription);
		fnPrintonConsole(StepName, StepDescription);
		Logger.INFO(StepName, StepDescription);
	
		if(Flag1.equalsIgnoreCase("True"))
		{
			ImageLocation = TakeScreenshot(b, StepName);
			code = fnPrepareSnapshotCode(ImageLocation);
		}
	}
	
	public static void Done(String StepName, String StepDescription, boolean blnDebugflag) throws IOException
	{
		String code="",ImageLocation = "";
		if (properties.getProperty(LOG_TYPE).equalsIgnoreCase(DEBUG) && blnDebugflag){
			ExtentTestManager.getTest().info(StepName + StepDescription);
			fnPrintonConsole(StepName, StepDescription);
			if(Flag1.equalsIgnoreCase("True"))
			{
				ImageLocation = TakeScreenshot(b, StepName);
				code = fnPrepareSnapshotCode(ImageLocation);
			}
		}
		Logger.INFO(StepName, StepDescription);
	}
	
	
	public static void Warning(String StepName, String StepDescription,boolean blnDebugflag) throws IOException
	{
		String code="",ImageLocation = "";
		if (properties.getProperty(LOG_TYPE).equalsIgnoreCase(DEBUG) && blnDebugflag){
			ExtentTestManager.getTest().warning(StepName + StepDescription);
			fnPrintonConsole(StepName, StepDescription);
			if(Flag1.equalsIgnoreCase("True"))
			{
				ImageLocation = TakeScreenshot(b, StepName);
				code = fnPrepareSnapshotCode(ImageLocation);
			}
		}
		Logger.WARNING(StepName, StepDescription);
		
	}
	
	public static void Warning(String StepName, String StepDescription) throws IOException
	{
		String code="",ImageLocation = "";
		ExtentTestManager.getTest().warning(StepName + StepDescription);
		fnPrintonConsole(StepName, StepDescription);
		Logger.WARNING(StepName, StepDescription);
		if(Flag1.equalsIgnoreCase("True"))
		{
			ImageLocation = TakeScreenshot(b, StepName);
			code = fnPrepareSnapshotCode(ImageLocation);
		}
		
	}
	
	public static void Error(String StepName, Exception e) throws IOException
	{
		String code="",ImageLocation = "";
		
		if(Flag1.equalsIgnoreCase("True"))
		{
			ImageLocation = TakeScreenshot(b, StepName);
			code = fnPrepareSnapshotCode(ImageLocation);
		}
		ExtentTestManager.getTest().error(StepName + e.getMessage());
		fnPrintonConsole(StepName, e.getMessage());
		Logger.ERROR(StepName, e);
	}
	
	public static void Error(String StepName, Exception e, boolean blnDebugflag) throws IOException
	{
		String code="",ImageLocation = "";
		if (properties.getProperty(LOG_TYPE).equalsIgnoreCase(DEBUG) && blnDebugflag){
			ExtentTestManager.getTest().error(StepName + e.getMessage());
			fnPrintonConsole(StepName, e.getMessage());
			if(Flag1.equalsIgnoreCase("True"))
			{
				ImageLocation = TakeScreenshot(b, StepName);
				code = fnPrepareSnapshotCode(ImageLocation);
			}
		}
		Logger.ERROR(StepName, e);
		
	}
	
	public static void Fatal(String StepName, String StepDescription) throws IOException
	{
		String code="",ImageLocation = "";
		ExtentTestManager.getTest().fatal(StepName + StepDescription);
		fnPrintonConsole(StepName, StepDescription);
		Logger.FATAL(StepName, StepDescription);

		
		if(Flag1.equalsIgnoreCase("True"))
		{
			ImageLocation = TakeScreenshot(b, StepName);
			code = fnPrepareSnapshotCode(ImageLocation);
		}
		
	}
	
	public static void Fatal(String StepName, String StepDescription, boolean blnDebugflag) throws IOException
	{
		String code="",ImageLocation = "";
		if (properties.getProperty(LOG_TYPE).equalsIgnoreCase(DEBUG) && blnDebugflag){
			ExtentTestManager.getTest().fatal(StepName + StepDescription);
			fnPrintonConsole(StepName, StepDescription);
			if(Flag1.equalsIgnoreCase("True"))
			{
				ImageLocation = TakeScreenshot(b, StepName);
				code = fnPrepareSnapshotCode(ImageLocation);
			}
		}
		Logger.FATAL(StepName, StepDescription);
		
	}
	
	public static void Done(String StepName, String StepDescription, Browser BrowObj) throws IOException
	{
		b = BrowObj;
		Done(StepName,StepDescription);
	}

	
	
	public static void Warning(String StepName, String StepDescription,Browser BrowObj) throws IOException
	{
		b = BrowObj;
		Warning(StepName,StepDescription);
		
	}
	
	
	public static void Error(String StepName, Exception e, Browser BrowObj) throws IOException
	{
		b = BrowObj;
		Error(StepName,e);
		
	}
	public static void Fatal(String StepName, String StepDescription, Browser BrowObj) throws IOException
	{
		b = BrowObj;
		Fatal(StepName,StepDescription);
		
	}
	/**
	* The method writes data to logs based on status(Pass/Fail)
	* @param it takes different string values like status, stepname and stepdescription
	* @throws IOException 
	*/
	public static void Reporter(String status, String stepname, String stepdescription) throws IOException
	{
		String code="",ImageLocation = "";
		
		if(stepname.contains(" "))
			stepname=stepname.replaceAll(" ", "_");
		
		if(Flag.equalsIgnoreCase("True")){
			ImageLocation = TakeScreenshot(b, stepname);
			code = fnPrepareSnapshotCode(ImageLocation);
		}
		
		if(status.equalsIgnoreCase("pass"))
			WritePassToLogger(stepname,stepdescription + code);
		else if(status.equalsIgnoreCase("fail"))
			WriteFailToLogger(stepname,stepdescription + code);
	}
	
	

	/**
	* The method takes screenshot and saves it to testcase folder with screenshot name as stepname  
	* @param Device expects the Device class object and stepname accepts the String value
	* @throws IOException 
	*/
	private static String TakeScreenshot(Browser b1, String stepname) throws IOException {
		
		
		/*
		 * String strReportSnapshotsPath =
		 * "\"\\\\sp.sunlifecorp.com\\sites\\tcoe\\TCoE\\Regression\\ALL LOB\\Execution Reports\""
		 * ; try{ Runtime.getRuntime().exec("net use f: " + strReportSnapshotsPath);
		 * strReportSnapshotsPath = "F:\\"; }catch(Exception e) { strReportSnapshotsPath
		 * = strReportSnapshotsPath + "\\"; }
		 */
			
		DateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String callerClassName = new Exception().getStackTrace()[3].getMethodName();
		String callerClassName1 = new Exception().getStackTrace()[2].getFileName();
		String testname = callerClassName1.split("\\.")[0];
		String className = new Exception().getStackTrace()[3].getClassName();
		String LineNum = String.valueOf(new Exception().getStackTrace()[3].getLineNumber());
		String class1, class2, ImageLocation;
		if (callerClassName.equals("driver")) {
			callerClassName = new Exception().getStackTrace()[2].getMethodName();
		}
		long count = className.chars().filter(ch -> ch == '.').count();

		class1 = className.split("\\.")[(int) count];
		class2 = class1.split("\\$")[0];

		String datestring = date.toString();
		datestring = datestring.replaceAll(":", "");
		String class3 = datestring.split(" ")[3];
		String projectPath = System.getProperty("user.dir");
		//File folder = new File(strReportSnapshotsPath + ClsIntialize.fnGetProp().getProperty("LOB") + "\\" + ClsIntialize.fnGetProp().getProperty("Project"));
		 String timeStamp = new SimpleDateFormat("dd-MM-yyyy_hh:mma").format(new Date());
		 String finalTimeStamp = (timeStamp.replace(":", "."));
		 String path = System.getProperty("user.dir")+"\\Screenshots"+"\\"+testname+"_"+finalTimeStamp;
		File folder = new File(path);
		
		 
		boolean Exsits = folder.exists();
		if (!Exsits) {
			folder.mkdir();
		}
		try {
			File src = ((TakesScreenshot) b1.driver).getScreenshotAs(OutputType.FILE);
			ImageLocation = path + "\\"+class3+".png";
			/*
			 * ImageLocation = strReportSnapshotsPath +
			 * ClsIntialize.fnGetProp().getProperty("LOB") + "\\" +
			 * ClsIntialize.fnGetProp().getProperty("Project") +
			 * "\\" + dateformat.format(date) + "\\" + class2 + "_" + callerClassName + "_"
			 * + LineNum + "_" + class3 + ".jpeg"; ImageLocation =
			 * ImageLocation.replaceAll(" ", "%20");
			 */
			ImageLocation.replaceAll(" ", "%20");
			ImageLocation.replaceAll("<", "_");
			ImageLocation.replaceAll(">", "_");
			FileUtils.copyFile(src, new File(ImageLocation));
		} catch (Exception e) {
			ImageLocation = "";
		}
		return ImageLocation;

	}
	/**
	 * The function writes FATAL logs in logger files.
	 * @param stepname takes the name which needs to be logged in Log4J.
	 * @param stepdescription takes the description of the logs which needs to be logged in Log4J.
	 */
	private static void WriteFailToLogger(String StepName, String StepDescription) 
	{
		ExtentTestManager.getTest().fail(StepName + StepDescription);
		fnPrintonConsole(StepName, StepDescription);
	}

	private static void fnPrintonConsole(String StepName, String StepDescription) {
		if (properties.getProperty(STRCONSOLE).equalsIgnoreCase(STRFLAG))
			System.out.println(StepName + "::" + StepDescription);
	}
	/**
	 * The function writes INFO logs in logger files.
	 * @param stepname takes the name which needs to be logged in Log4J.
	 * @param stepdescription takes the description of the logs which needs to be logged in Log4J.
	 */
	private static void WritePassToLogger(String StepName, String StepDescription) {
		ExtentTestManager.getTest().pass(StepName + StepDescription);
		fnPrintonConsole(StepName, StepDescription);
		
	}
	
	private static String fnPrepareSnapshotCode(String ImageLocation) {
		return STRHTMLPARTONE + ImageLocation + STRHTMLPARTTWO;
	}

	
}
