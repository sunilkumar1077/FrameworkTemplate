package ETS;

//import framework_web.*;
import FrameworkSource.web.*;
//import framework_global.*;
import FrameworkSource.global.*;
//import framework_global.reporter.*;
import FrameworkSource.global.reporter.*;
import java.util.HashMap;
import java.io.IOException;
import org.junit.*;

public class ETS_Login {

	@Test
	public void main() throws Exception
	{
	DataReader datareader = new DataReader("ETS_Login");
	new ETS_Login().new TestFlow().driver(datareader);
	}

private class TestFlow {

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
				//Call methods
				open_browser(browser[j],datareader.getURL());
				login();
				homePage();
				//close_browser();
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
		page.SetCurrentPage("ETS_Login");
		new TextBox(browser, "LoginID").SendKeys(testdata.get("LoginID"));
		new TextBox(browser, "Password").SendKeys(testdata.get("Password"));
		new Button(browser, "SignIN").Click();
	}


	private void homePage() throws IOException, InterruptedException
	{
		page.SetCurrentPage("ETS_Login");
		Thread.sleep(20000);
		new TextBox(browser, "Username").Click();
		new TextBox(browser, "LogOff").Click();
		
		
		
	}
	private void close_browser() throws Exception
	{
		browser.Quit();
	}

}
}