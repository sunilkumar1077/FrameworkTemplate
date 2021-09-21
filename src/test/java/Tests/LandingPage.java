package Tests;

//import framework_web.*;
import FrameworkSource.web.*;
//import framework_global.*;
import FrameworkSource.global.*;
//import framework_global.reporter.*;
import FrameworkSource.global.reporter.*;
import java.util.HashMap;
import java.io.IOException;
import org.junit.*;

public class LandingPage {

	@Test
	public void main() throws Exception
	{
	DataReader datareader = new DataReader("LandingPageValidations");
	new LandingPage().new TestFlow().driver(datareader);
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
				validateVersionNumber();
				elementsExistence();
				termsOfServiceTextValidation();
				
				//login();
				close_browser();
				}finally
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


	private void validateVersionNumber() throws IOException, InterruptedException
	{
		page.SetCurrentPage("LandingPage");
		if(new WebElement(browser,"RAAVNSignIn").Exists())
		ReportEvents.Reporter("Pass", " Page Validation", "User is on Landing page.");
		else
		ReportEvents.Reporter("Fail", " Page Validation", "Landing page is not loaded properly.");	
		String expectedVersionNumber = testdata.get("VersionNumber");
		String actualVersionNumber = new WebElement(browser,"VersionNumber").GetValue();	
		if(expectedVersionNumber.equals(actualVersionNumber))
			ReportEvents.Reporter("Pass", "Version Number Check", "Correct Version number is getting displayed");
		else
			ReportEvents.Reporter("fail", "Version Number Check", "InCorrect Version number is getting displayed");
		
	}

	
	private void elementsExistence() throws IOException, InterruptedException
	{
		page.SetCurrentPage("LandingPage");
		if(new WebElement(browser,"RAAVNSignIn").Exists() && new WebElement(browser,"RAAPSignIn").Exists() && new WebElement(browser,"TermsOfService").Exists())
			ReportEvents.Reporter("Pass", "Landing Page elements Validation", "All the buttons and links are present on landing page as expected.");
			else
			ReportEvents.Reporter("Fail", "Landing Page elements Validation","All the buttons and links are NOT present on landing page as expected.");
	}
	
	
	private void termsOfServiceTextValidation() throws IOException, InterruptedException 
	{
		page.SetCurrentPage("LandingPage");
		new WebElement(browser,"TermsOfService").Click();
		
		if(new WebElement(browser,"TermsOfServiceModalTitle").Exists())
			ReportEvents.Reporter("Pass", "Click Terms Of Service", "User successfully clicked on Terms Of Service link and modal is getting displayed");
		else
			ReportEvents.Reporter("Fail", "Click Terms Of Service", "Terms of Service modal is not getting displayed");
		String expectedTermsOfServiceText = testdata.get("TermsOfServiceText");
		String actualTermsOfServiceText = new WebElement(browser,"TermsOfServiceText").GetValue();
		if(expectedTermsOfServiceText.equals(actualTermsOfServiceText))
			ReportEvents.Reporter("Pass", "Terms of Service Text Validation", "Correct Terms of Service text is getting displayed");
		else
			ReportEvents.Reporter("Fail", "Terms of Service Text Validation", "Terms of Service text is not correct");
		
	}
	
	
	private void close_browser() throws Exception
	{
		browser.Quit();
	}

}
}