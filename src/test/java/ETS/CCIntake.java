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



public class CCIntake {

	@Test
	public void main() throws Exception
	{
	DataReader datareader = new DataReader("CCIntake");
	new CCIntake().new TestFlow().driver(datareader);

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
				try {
				//Call methods
				open_browser(browser[j],datareader.getURL());
				navigate_CC_SignUpPage();
				fill_CCEnrollmentForm();
				//close_browser();
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

	private void navigate_CC_SignUpPage() throws IOException, InterruptedException
	{
		page.SetCurrentPage("ETS_Login");
		if (new TextBox(browser, "SignUp").Exists());
		
		ReportEvents.Reporter("Pass", "SignUp Button Validation","Sign Up button exists on the Login Page");
		new TextBox(browser, "SignUp").Click();
		new TextBox(browser,"CCSignUp").Exists();
		ReportEvents.Reporter("Pass", "CC SignUp Button Validation","User clicked on Sign Up button and navigated to the CC Sign Up page");
		new TextBox(browser,"CCSignUp").Click();
	}

	private void fill_CCEnrollmentForm() throws IOException, InterruptedException
	{
		page.SetCurrentPage("CC_EnrollmentForm");
		String PrefixValue = testdata.get("Prefix");
		new DropDown(browser, "Prefix").SelectByText(PrefixValue);
		String FirstName = testdata.get("FirstName");
		new TextBox(browser,"FirstName").SendKeys(FirstName);
		String MiddleName = testdata.get("MiddleName");
		if(MiddleName!=null)
		new TextBox(browser,"MiddleName").SendKeys(MiddleName);
		String LastName = testdata.get("LastName");
		new TextBox(browser,"LastName").SendKeys(LastName);
		String suffix = testdata.get("Suffix");
		if(suffix!=null)
		new TextBox(browser,"Suffix").SendKeys(suffix);
		
		String nickName = testdata.get("NickName");
		if(nickName!=null)
		new TextBox(browser,"NickName").SendKeys(nickName);
		
	  String dob = testdata.get("DOB"); new TextBox(browser,"DOB").SendKeys(dob);
	  
	  String gender = testdata.get("Gender"); new DropDown(browser,"Gender").SelectByText(gender); 
	  
	  String orientation =testdata.get("SexualOrientation"); 
	  new DropDown(browser,"SexualOrientation").SelectByText(orientation);
	  
	  
	  String addLine1 = testdata.get("AddressLine1"); 
	  new TextBox(browser,"AddressLine1").SendKeys(addLine1);
	 
	  String addLine2 = testdata.get("AddressLine2");
	  if(addLine2!=null)
	  new TextBox(browser,"AddressLine2").SendKeys(addLine2);
	  
	  String city = testdata.get("City"); 
	  new TextBox(browser,"City").SendKeys(city);
	  
	  String country =testdata.get("Country");
	  new DropDown(browser,"Country").SelectByText(country);
	  if(country.equalsIgnoreCase("United States of America"))
	  {
		  String state = testdata.get("State");
		  new DropDown(browser,"State").SelectByText(state);  
	  }
	  
	  String zip = testdata.get("ZipCode"); 
	  new TextBox(browser,"ZipCode").SendKeys(zip);
	  
	  String email = testdata.get("Email"); 
	  new TextBox(browser,"Email").SendKeys(email);
	  

	  String phone = testdata.get("Phone"); 
	  new TextBox(browser,"Phone").SendKeys(phone);
	  
	  String APP =testdata.get("AffiliatedPartnerProgram");
	  if(APP!=null)
	  new DropDown(browser,"APP").SelectByText(APP);
	  
	  String militaryServed =testdata.get("MilitaryServed");
	  new DropDown(browser,"MilitaryServed").SelectByText(militaryServed);
	  if(militaryServed.equalsIgnoreCase("Yes"))
	  {
		  String branch =testdata.get("Branch");
		  new DropDown(browser,"Branch").SelectByText(branch);
		  
		  String jobCode =testdata.get("JobCode");
		  if(jobCode!=null)
		  new TextBox(browser,"JobCode").SendKeys(jobCode);
		  
		  String highestRank =testdata.get("HighestRank");
		  new DropDown(browser,"HighestRank").SelectByText(highestRank);
		  
		  String payEntryDate =testdata.get("EntryBaseDate");
		  new TextBox(browser,"EntryBaseDate").SendKeys(payEntryDate);
		  
		  String etsDate =testdata.get("ETSDate");
		  if(etsDate!=null)
		  new TextBox(browser,"ETSDate").SendKeys(etsDate);
		  
		  //Deployments
		  String operation =testdata.get("DeploymentOperation");
		  if(operation!=null)
		  new TextBox(browser,"DepOperation").SendKeys(operation);
		  
		  String depCountry =testdata.get("DeploymentCountry");
		  if(depCountry!=null)
		  new DropDown(browser,"DepCountry").SelectByText(depCountry);
		  
		  String startYear =testdata.get("DeploymentStartYear");
		  if(startYear!=null)
			  new DropDown(browser,"DepStartYear").SelectByText(startYear);
		  
		  String endYear =testdata.get("DeploymentEndYear");
		  if(endYear!=null)
			  new DropDown(browser,"DepEndYear").SelectByText(endYear);
		  
		  //Add second Deployment
		  new Button(browser,"AddDeployment").Click();
		  
		  String operation1 =testdata.get("DeploymentOperation1");
		  if(operation1!=null)
		  new TextBox(browser,"DepOperation1").SendKeys(operation1);
		  
		  String depCountry1 =testdata.get("DeploymentCountry1");
		  if(depCountry1!=null)
		  new DropDown(browser,"DepCountry1").SelectByText(depCountry1);
		  
		  String startYear1 =testdata.get("DeploymentStartYear1");
		  if(startYear1!=null)
			  new DropDown(browser,"DepStartYear1").SelectByText(startYear1);
		  
		  String endYear1 =testdata.get("DeploymentEndYear1");
		  if(endYear1!=null)
			  new DropDown(browser,"DepEndYear1").SelectByText(endYear1);
		 	  
		  
	  }
	  
	  String milRelation =testdata.get("MilRelationShip");
	  if(milRelation!=null)
		  new DropDown(browser,"MilitaryRelationship").SelectByText(milRelation);
	
	  String milRelationDesc =testdata.get("MRDescription");
	  if(milRelationDesc!=null)
		  new DropDown(browser,"MRDescription").SelectByText(milRelationDesc);
	  
	  new Button(browser,"SaveAndContinue").Click();

	}
	

	private void fill_CCPermissions() throws IOException, InterruptedException
	{
		page.SetCurrentPage("CC_EnrollmentForm");
	
	}
	private void close_browser() throws Exception
	{
		browser.Quit();
	}

}
}