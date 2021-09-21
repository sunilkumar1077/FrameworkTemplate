package Tests;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.openqa.selenium.By;
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

public class HomePageValidation {

	@Test
	public void main() throws Exception
	{
	DataReader datareader = new DataReader("HomePageValidation");
	new HomePageValidation().new TestFlow().driver(datareader);
	}


private class TestFlow {
	
	
	
	TestFlow()
	{
		ReportEvents.ScreenShotRequired("true");
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
				login();
				//validateAllLinks();
				validateUserInfo();
				headerValidation();
				validateDataManager();
				validateAnalystToolbox();
				validateVisualizationNavigator();
				validateExports();
				validateApplications();
				validateHelp();
				validateAdministratorTool();
				logoff();
				/*
				 * validateDataManager(); validateAnalystToolbox();
				 */
				close_browser();
				}
				finally
				{
					ReportGenerator.Generate("true");
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


	private void validateAllLinks() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String role = new WebElement(browser,"Role").GetValue();
		System.out.println("Role is:"+ role);
		switch(role)
		{
		case "Admin":
		{
			validateDataManager();
			validateAnalystToolbox();
			validateVisualizationNavigator();
			validateExports();
			validateApplications();
			validateHelp();
			validateAdministratorTool();
		}
		
		case "User":
		{
			
		}
			
		}
		
		
	}
	
	
	
	private void validateUserInfo() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='item item-profile-button']"))).perform();
		
		String actualMyProfileValue = new WebElement(browser,"MyProfile").GetValue();
		String actualReleaseInfoValue = new WebElement(browser,"ReleaseInfo").GetValue();
		String actualReportIssueValue = new WebElement(browser,"ReportIssue").GetValue();
		String actualLogOffValue = new WebElement(browser,"LogOff").GetValue();
		
		String expectedMyProfileValue =  testdata.get("ProfileText");
		String expectedReleaseInfoValue =  testdata.get("ReleaseInfoText");
		String expectedReportIssueValue =  testdata.get("ReportText");
		String expectedLogOffValue =  testdata.get("LogOffText");
		
		if(expectedMyProfileValue.equals(actualMyProfileValue)&&
				expectedReleaseInfoValue.equals(actualReleaseInfoValue)&&
				expectedReportIssueValue.equals(actualReportIssueValue)&&
				expectedLogOffValue.equals(actualLogOffValue))
			ReportEvents.Reporter("Pass", "User Info Existence", 
					"User Info is getting displayed as expected");

		else
			ReportEvents.Reporter("fail", "User Info Existence", 
					"User Info is NOT getting displayed as expected");
		}
	
	
	private void headerValidation() throws IOException, InterruptedException 
	{
		page.SetCurrentPage("HomePage");
		
		new WebElement(browser,"HeaderHomePage").Click();
		String actualHeaderHomePageValue = new WebElement(browser,"HeaderHomePage").GetValue();
		String actualHeaderTextValue = new WebElement(browser,"HeaderText").GetValue();
		String actualHeaderDataExplorerValue = new WebElement(browser,"HeaderDataExplorer").GetValue();
		
		String expectedHeaderHomePageValue = testdata.get("HeaderHomePageText");
		String expectedHeaderTextValue = testdata.get("HeaderText");
		String expectedHeaderDataExplorerValue = testdata.get("HeaderDataExplorer");
		
		if(expectedHeaderHomePageValue.equals(actualHeaderHomePageValue)&&
			expectedHeaderTextValue.equals(actualHeaderTextValue)&&
			expectedHeaderDataExplorerValue.equals(actualHeaderDataExplorerValue))
			ReportEvents.Reporter("Pass", "Header Info Existence", 
					"Header Info is getting displayed as expected");

		else
			ReportEvents.Reporter("fail", "Header Info Existence", 
					"Header Info is NOT getting displayed as expected");
			
			
			
	}
	
	
	private void validateTopNavigationDataExplorer()
	{
		
	}
	
	private void validateDataManager() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String actualValue = new WebElement(browser,"DataManagerLink").GetValue();
		System.out.println("actual Value is:"+actualValue );
		String expectedValue = testdata.get("DataManagerText");
		System.out.println("expected value is:"+expectedValue );
		if(new WebElement(browser,"DataManagerIcon").Exists() && new WebElement(browser,"DataManagerLink").Exists())
		{
			ReportEvents.Reporter("Pass", "Data Manager Existence", 
					"Data Manager is getting displayed as expected");
		}
		else
			ReportEvents.Reporter("fail", "Data Manager Existence", 
					"Data Manager is NOT getting displayed as expected");
		
		//hover over code
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='data_manager_icon']"))).perform();
		String actualDataManagerHoverValue = new WebElement(browser,"DataManagerHeader").GetValue();
		String actualDataReportsHoverValue = new WebElement(browser,"DataReports").GetValue();
		String actualFeedManagerHoverValue = new WebElement(browser,"FeedManager").GetValue();
		String actualJAMSHoverValue = new WebElement(browser,"JAMS").GetValue();
		String actualTweetSpoutHoverValue = new WebElement(browser,"TweetSpout").GetValue();
		
		String expectedDataManagerHoverValue = testdata.get("DataManagerHoverText");
		String expectedDataReportsHoverValue = testdata.get("DataReportsText");
		String expectedFeedManagerHoverValue = testdata.get("FeedManagerText");	
		String expectedJAMSHoverValue = testdata.get("JAMSText");
		String expectedTweetSpoutHoverValue = testdata.get("TweetSpoutText");
		
		if(expectedDataManagerHoverValue.equals(actualDataManagerHoverValue) &&
				expectedDataReportsHoverValue.equals(actualDataReportsHoverValue)&&
				expectedFeedManagerHoverValue.equals(actualFeedManagerHoverValue)&&
				expectedJAMSHoverValue.equals(actualJAMSHoverValue)&&
				expectedTweetSpoutHoverValue.equals(actualTweetSpoutHoverValue))
		{
			ReportEvents.Reporter("Pass", "Data Manager Modal Existence", 
					"Data Manager Modal and all its component are getting displayed as expected.");
		}
		else
		{
			ReportEvents.Reporter("fail", "Data Manager Existence", 
					"Data Manager Modal and all its component are NOT getting displayed as expected.");
		}
		
		
		
		
		
		
		}
	
	
	private void validateAnalystToolbox() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String actualValue = new WebElement(browser,"AnalystToolboxLink").GetValue();
		System.out.println("actual Value is:"+actualValue );
		String expectedValue = testdata.get("AnalystToolboxText");
		System.out.println("expected value is:"+expectedValue );
		if(new WebElement(browser,"AnalystToolboxIcon").Exists() && new WebElement(browser,"AnalystToolboxLink").Exists() && actualValue.equals(expectedValue))
		
			ReportEvents.Reporter("Pass", "Analyst Toolbox Existence", 
					"Analyst Toolbox is getting displayed as expected");
		else
		
			ReportEvents.Reporter("fail", "Analyst Toolbox Existence", 
					"Analyst Toolbox is NOT getting displayed as expected");
		
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='Analyst_Toolbox_icon']"))).perform();
		String actualHeaderValue = new WebElement(browser,"AnalystToolboxHeader").GetValue();
		String actualAIManagerValue = new WebElement(browser,"AIManager").GetValue();
		String actualQsavedValue = new WebElement(browser,"Qsaved").GetValue();
		
		String expectedHeaderValue =  testdata.get("AnalystToolboxHovertext");
		String expectedAIManagerValue =  testdata.get("AIManagertext");
		String expectedQsavedValue =  testdata.get("QsavedText");
		
		
		if(actualHeaderValue.equals(expectedHeaderValue)&&
				actualAIManagerValue.equals(expectedAIManagerValue)&&
				actualQsavedValue.equals(expectedQsavedValue))
			ReportEvents.Reporter("Pass", "Analyst Toolbox Modal Existence", 
					"Analyst ToolBox Modal and all its component are getting displayed as expected.");
		
		else
		
			ReportEvents.Reporter("fail", "Analyst Toolbox Modal Existence", 
					"Analyst Toolbox Modal and all its component are NOT getting displayed as expected.");
		
			
		
		
		}
	
	
	private void validateVisualizationNavigator() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String actualValue = new WebElement(browser,"VisuaizationNavigatorLink").GetValue();
		System.out.println("actual Value is:"+actualValue );
		String expectedValue = testdata.get("VisualizationNavigatorText");
		System.out.println("expected value is:"+expectedValue );
		if(new WebElement(browser,"VisuaizationNavigatorIcon").Exists() && new WebElement(browser,"VisuaizationNavigatorLink").Exists() && actualValue.equals(expectedValue))
		
			ReportEvents.Reporter("Pass", "Visualization Navigator Existence", 
					"Visualization Navigator is getting displayed as expected");
		else
		
			ReportEvents.Reporter("fail", "Visualization Navigator Existence", 
					"Visualization Navigator is NOT getting displayed as expected");
		
		
		
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='Visualization_Toolbox_icon']"))).perform();
		
		String actualVisualHeaderValue = new WebElement(browser,"VisualizationHeader").GetValue();
		String actualVisualCrisisValue = new WebElement(browser,"CrisisMonitor").GetValue();
		String actualVisualDataValue = new WebElement(browser,"DataExplorer").GetValue();
		String actualVisualMediaValue = new WebElement(browser,"MediaMaps").GetValue();
		String actualVisualTrendsValue = new WebElement(browser,"TrendsExplorer").GetValue();
		String actualVisualEntityValue = new WebElement(browser,"Entity Explorer").GetValue();
		String actualVisualTwitterValue = new WebElement(browser,"TwitterAccount").GetValue();
		
				
		
		String expectedVisualHeaderValue = testdata.get("VisualizationHoverText");
		String expectedVisualCrisisValue = testdata.get("CrisisText");
		String expectedVisualDataValue = testdata.get("ExplorerText");
		String expectedVisualMediaValue = testdata.get("MediaText");
		String expectedVisualTrendsValue = testdata.get("TrendsText");
		String expectedVisualEntityValue = testdata.get("EntityText");
		String expectedVisualTwitterValue = testdata.get("TwitterText");
		
		
		if(expectedVisualHeaderValue.equals(actualVisualHeaderValue) &&expectedVisualCrisisValue.equals(actualVisualCrisisValue)&&
				expectedVisualDataValue.equals(actualVisualDataValue)&&
				expectedVisualMediaValue.equals(actualVisualMediaValue)&&
				expectedVisualTrendsValue.equals(actualVisualTrendsValue)&&
				expectedVisualEntityValue.equals(actualVisualEntityValue)&&
				expectedVisualTwitterValue.equals(actualVisualTwitterValue))
			ReportEvents.Reporter("Pass", "Visualization Navigator Modal Existence", 
					"Visualization Navigator Modal and all its component are getting displayed as expected.");
		else
			ReportEvents.Reporter("fail", "Visualization Navigator Modal Existence", 
					"Visualization Navigator Modal and all its component are NOT getting displayed as expected.");
		
		
		}


	private void validateExports() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String actualValue = new WebElement(browser,"ExportsLink").GetValue();
		System.out.println("actual Value is:"+actualValue );
		String expectedValue = testdata.get("ExportsText");
		System.out.println("expected value is:"+expectedValue );
		if(new WebElement(browser,"ExportsIcon").Exists()
				&& new WebElement(browser,"ExportsLink").Exists() 
				&& actualValue.equals(expectedValue))
		ReportEvents.Reporter("Pass", "Exports Existence", 
					"Exports is getting displayed as expected");
		else
			ReportEvents.Reporter("fail", "Exports Existence", 
					"Exports is NOT getting displayed as expected");
		
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='Exports_icon']"))).perform();
		
		if(new WebElement(browser,"ExportHoverIcon").Exists())
		ReportEvents.Reporter("Pass", "Exports Icon Existence", "Exports icon is getting displayed as expected");
		else
			ReportEvents.Reporter("Pass", "Exports Icon Existence", "Exports icon is not getting displayed as expected");	
		
		}

	
	private void validateApplications() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String actualValue = new WebElement(browser,"ApplicationsLink").GetValue();
		System.out.println("actual Value is:"+actualValue );
		String expectedValue = testdata.get("ApplicationsText");
		System.out.println("expected value is:"+expectedValue );
		if(new WebElement(browser,"ApplicationIcon").Exists() && new WebElement(browser,"ApplicationsLink").Exists() && actualValue.equals(expectedValue))
		
			ReportEvents.Reporter("Pass", "Applications Existence", 
					"Applications is getting displayed as expected");
		else
		
			ReportEvents.Reporter("Pass", "Applications Existence", 
					"Applications is NOT getting displayed as expected");
		
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='Applications_icon']"))).perform();
		
		String actualAppValue = new WebElement(browser,"ApplicationsHeader").GetValue();
		String actualConnectionsValue = new WebElement(browser,"Connections").GetValue();
		String actualSemanticaValue = new WebElement(browser,"Semantica").GetValue();
		String actualWCAValue = new WebElement(browser,"WCA").GetValue();
		
		String expectedAppValue = testdata.get("ApplicationHoverText");
		String expectedConnectionsValue = testdata.get("ConnectionsText");
		String expectedSemanticaValue = testdata.get("SemanticaText");
		String expectedWCAValue = testdata.get("WCAText");
		
		
		if(expectedAppValue.equals(actualAppValue)&&
				expectedConnectionsValue.equals(actualConnectionsValue)&&
				expectedSemanticaValue.equals(actualSemanticaValue)&&
				expectedWCAValue.equals(actualWCAValue)
				)
			ReportEvents.Reporter("Pass", "Applications Modal Existence", 
					"Applications Modal and all its component are getting displayed as expected.");
		else
			ReportEvents.Reporter("fail", "Applications Modal Existence", 
					"Applications Modal and all its component are NOT getting displayed as expected.");
		
		}

	
	private void validateHelp() throws IOException, InterruptedException
	{
		page.SetCurrentPage("HomePage");
		String actualValue = new WebElement(browser,"HelpLink").GetValue();
		String expectedValue = testdata.get("HelpText");
		System.out.println("expected value is:"+expectedValue );
		if(new WebElement(browser,"HelpIcon").Exists() && new WebElement(browser,"HelpLink").Exists() && actualValue.equals(expectedValue))
		
			ReportEvents.Reporter("Pass", "Help Existence", 
					"Help is getting displayed as expected");
		else
		
			ReportEvents.Reporter("fail", "Help Existence", 
					"Help is NOT getting displayed as expected");
		
		Actions action = new Actions(browser.driver);
		action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='Help_icon']"))).perform();
		
		if(new WebElement(browser,"HelpHoverIcon").Exists())
		ReportEvents.Reporter("Pass", "Help Icon Existence", "Help icon is getting displayed as expected");
		else
			ReportEvents.Reporter("fail", "Help Icon Existence", "Help icon is not getting displayed as expected");	
		
		
		
		}
	
	
	
	private void validateAdministratorTool() throws IOException
	{
		String role = new WebElement(browser,"Role").GetValue();
		System.out.println("Role is:"+ role);
		if(role.equals("Collector")||role.equals("User"))
		{
			Boolean flag= new WebElement(browser,"AdministratorTool").IsEnabled();
			if(flag.equals(false))
			{
				ReportEvents.Reporter("pass", "Administrator Tools Validation", role+" doesn't have access to Administrator Tools");
			}
		}
		else if(role.equals("Admin"))
		{
			Boolean flag= new WebElement(browser,"AdministratorTool").IsEnabled();
			if(flag.equals(true))
			{
				String actualAdminValue = new WebElement(browser,"AdminLink").GetValue();
				System.out.println("actualAdminValue is: "+ actualAdminValue);
				String expectedAdminValue = testdata.get("AdminText");
				System.out.println("expectedAdminValue is: "+ expectedAdminValue);
				if(new WebElement(browser,"AdminIcon").Exists()&&
					new WebElement(browser,"AdminLink").Exists()&&
					expectedAdminValue.equals(actualAdminValue))
					ReportEvents.Reporter("Pass", "Administrator Tools Existence", 
							"Administrator Tools is getting displayed as expected");
				else
				
					ReportEvents.Reporter("fail", "Administrator Tools Existence", 
							"Administrator Tools is NOT getting displayed as expected");
				
				
				
				//new WebElement(browser,"UserMgt").ScrollTillElement());
				
				Actions action = new Actions(browser.driver);
				action.moveToElement(browser.driver.findElement(By.xpath("//*[@class='administrator_icon']"))).perform();
				
			String actualAdminHeaderValue =	new WebElement(browser,"AdminHeader").GetValue();
			String actualUserMgtValue = new WebElement(browser,"UserMgt").GetValue();
			
			String expectedAdminHeaderValue = testdata.get("AdminHoverText");
			String expectedUserMgtValue = testdata.get("UserMgtText");
			
			if(expectedAdminHeaderValue.equals(actualAdminHeaderValue)&&
					expectedUserMgtValue.equals(actualUserMgtValue))
				
				ReportEvents.Reporter("Pass", "Administrator Tools Modal Existence", 
						"Administrator Tools and all its component are getting displayed as expected.");
			else
				ReportEvents.Reporter("fail", "Administrator Tools Modal Existence", 
						"Administrator Tools and all its component are NOT getting displayed as expected.");
				
			}
		}
	
	}
	
	private void logoff() throws IOException
	{
		new WebElement(browser,"LogOut").Click();
	}

	private void close_browser() throws Exception
	{
		browser.Quit();
	}

}}

