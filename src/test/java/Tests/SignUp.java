package Tests;


import FrameworkSource.web.*;
//import framework_global.*;
import FrameworkSource.global.*;
//import framework_global.reporter.*;
import FrameworkSource.global.reporter.*;
import java.util.HashMap;
import java.io.IOException;
import org.junit.*;

public class SignUp {

	@Test
	public void main() throws Exception
	{
	DataReader datareader = new DataReader("CCIntake");
	new SignUp().new TestFlow().driver(datareader);
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
				//Call methods
				try
				{
				open_browser(browser[j],datareader.getURL());
				navigateToCCSignUpForm();
				fill_CCEnrollmentForm();
				fill_CCPermissions();
				setPassword();
				close_browser();
				}
			finally
			{
				ReportGenerator.Generate("true");
			}}
		}
	}

	private void open_browser(String browserType, String url) throws Exception
	{
		browser.InitiateBrowser(browserType);
		browser.Maximize();
		browser.NavigateURL(url);
	}

	void navigateToCCSignUpForm() throws IOException, InterruptedException
	{
		page.SetCurrentPage("SignIn");
		new HyperLink(browser, "SignUpLink").Click();
		new HyperLink(browser, "CCSignUPLink").Click();
		if(page.GetTitle().equalsIgnoreCase("Sign Up"))
		{
		ReportEvents.Reporter("Pass", "Page Validation:- ", "User successfully navigated to CC Enrollment Form.");
		}
		else
		{
		ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: User not navigated to CC Enrollment Form.");
		}
		
	}

	
	private void fill_CCEnrollmentForm() throws IOException, InterruptedException
	{
		page.SetCurrentPage("CC_EnrollmentForm");
		new Button(browser, "Prefix").Click();
		new Button(browser, "PrefixMr").Click();
		//String PrefixValue = testdata.get("Prefix");
		//new DropDown(browser, "Prefix").SelectByText(PrefixValue);
		String firstName = testdata.get("FirstName");		
		new TextBox(browser,"FirstName").SendKeys(firstName);
		
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
		
	  String dob = testdata.get("DOB");
	  new TextBox(browser,"DOB").SendKeys(dob);
	  
	  //String gender = testdata.get("Gender"); new DropDown(browser,"Gender").SelectByText(gender);
	  new WebElement(browser,"Gender").Click();
	  new WebElement(browser,"GenderM").Click();
	  
	  
	  if(new Button(browser, "Prefix").GetValue()!=null &&
			  new WebElement(browser,"Gender").GetValue()!=null)
			ReportEvents.Reporter("Pass", "Page Validation:- ", "Personal Information field values are filled successfully.");
			else
		    ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while filling values in Personal Information.");
		  	
	  
	  //String orientation =testdata.get("SexualOrientation"); 
	  //new DropDown(browser,"SexualOrientation").SelectByText(orientation);
	  
	  
	  String addLine1 = testdata.get("AddressLine1"); 
	  new TextBox(browser,"AddressLine1").SendKeys(addLine1);
	 
	  String addLine2 = testdata.get("AddressLine2");
	  if(addLine2!=null)
	  new TextBox(browser,"AddressLine2").SendKeys(addLine2);
	  
	  new WebElement(browser,"State").Click();
	  new WebElement(browser,"StateAlaska").Click();
	  String city = testdata.get("City"); 
	  new TextBox(browser,"City").SendKeys(city);
	  
		/*
		 * String country =testdata.get("Country"); new
		 * DropDown(browser,"Country").SelectByText(country);
		 * if(country.equalsIgnoreCase("United States of America")) { String state =
		 * testdata.get("State"); new DropDown(browser,"State").SelectByText(state); }
		 */
	  
	  String zip = testdata.get("ZipCode"); 
	  new TextBox(browser,"ZipCode").SendKeys(zip);
	  
	  String email = testdata.get("Email"); 
	  new TextBox(browser,"Email").SendKeys(email);
	  

	  new WebElement(browser,"DialCode").Click();
	  new WebElement(browser,"Phone355").Click();
	  String phone = testdata.get("Phone"); 
	  new TextBox(browser,"Phone").SendKeys(phone);
	  
	  if(new WebElement(browser,"State").GetValue()!=null)
			ReportEvents.Reporter("Pass", "Page Validation:- ", "Contact Information field values are filled successfully.");
	  else 
		  	ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while filling values in Contact Information.");
		  	
	  
	  
	  new WebElement(browser,"APP").Click();
	  new WebElement(browser,"AppValue").Click();
	  
	  if(new WebElement(browser,"APP").GetValue()!=null)
		ReportEvents.Reporter("Pass", "Page Validation:- ", "Affiliated Partner Program value is filled successfully.");
	  else
		  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while filling value in Affiliated Partner Program.");
		  
		/*
		 * String APP =testdata.get("AffiliatedPartnerProgram"); if(APP!=null) new
		 * DropDown(browser,"APP").SelectByText(APP);
		 */
	  new WebElement(browser,"MilitaryServed").Click();
	  new WebElement(browser,"MilitaryServedNo").Click();
	  
	  page.Wait(2);
	  
	  new WebElement(browser,"MilitaryRelationship").Click();
	  new WebElement(browser,"MilitaryRelationshipValue").Click();
	  
	  String relationshipValue = testdata.get("MRDescription");
	  new WebElement(browser,"MRDescription").SendKeys(relationshipValue);
	  
	  if(new WebElement(browser,"MilitaryRelationship").GetValue()!=null)
			ReportEvents.Reporter("Pass", "Page Validation:- ", "Military History and Deployment(s) field values are filled successfully.");
		  else
			  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while filling values in Military History and Deployment(s) fields.");
	  
		/*
		 * String militaryServed =testdata.get("MilitaryServed"); new
		 * DropDown(browser,"MilitaryServed").SelectByText(militaryServed);
		 * if(militaryServed.equalsIgnoreCase("Yes")) { String branch
		 * =testdata.get("Branch"); new DropDown(browser,"Branch").SelectByText(branch);
		 * 
		 * String jobCode =testdata.get("JobCode"); if(jobCode!=null) new
		 * TextBox(browser,"JobCode").SendKeys(jobCode);
		 * 
		 * String highestRank =testdata.get("HighestRank"); new
		 * DropDown(browser,"HighestRank").SelectByText(highestRank);
		 * 
		 * String payEntryDate =testdata.get("EntryBaseDate"); new
		 * TextBox(browser,"EntryBaseDate").SendKeys(payEntryDate);
		 * 
		 * String etsDate =testdata.get("ETSDate"); if(etsDate!=null) new
		 * TextBox(browser,"ETSDate").SendKeys(etsDate);
		 * 
		 * //Deployments String operation =testdata.get("DeploymentOperation");
		 * if(operation!=null) new TextBox(browser,"DepOperation").SendKeys(operation);
		 * 
		 * String depCountry =testdata.get("DeploymentCountry"); if(depCountry!=null)
		 * new DropDown(browser,"DepCountry").SelectByText(depCountry);
		 * 
		 * String startYear =testdata.get("DeploymentStartYear"); if(startYear!=null)
		 * new DropDown(browser,"DepStartYear").SelectByText(startYear);
		 * 
		 * String endYear =testdata.get("DeploymentEndYear"); if(endYear!=null) new
		 * DropDown(browser,"DepEndYear").SelectByText(endYear);
		 * 
		 * //Add second Deployment new Button(browser,"AddDeployment").Click();
		 * 
		 * String operation1 =testdata.get("DeploymentOperation1"); if(operation1!=null)
		 * new TextBox(browser,"DepOperation1").SendKeys(operation1);
		 * 
		 * String depCountry1 =testdata.get("DeploymentCountry1"); if(depCountry1!=null)
		 * new DropDown(browser,"DepCountry1").SelectByText(depCountry1);
		 * 
		 * String startYear1 =testdata.get("DeploymentStartYear1"); if(startYear1!=null)
		 * new DropDown(browser,"DepStartYear1").SelectByText(startYear1);
		 * 
		 * String endYear1 =testdata.get("DeploymentEndYear1"); if(endYear1!=null) new
		 * DropDown(browser,"DepEndYear1").SelectByText(endYear1);
		 * 
		 * 
		 * }
		 */
	  
		/*
		 * String milRelation =testdata.get("MilRelationShip"); if(milRelation!=null)
		 * new DropDown(browser,"MilitaryRelationship").SelectByText(milRelation);
		 * 
		 * String milRelationDesc =testdata.get("MRDescription");
		 * if(milRelationDesc!=null) new
		 * DropDown(browser,"MRDescription").SelectByText(milRelationDesc);
		 * 
		 * 
		 */
	  
	  new Button(browser,"SaveAndContinue").Click();

	}
	

	private void fill_CCPermissions() throws IOException, InterruptedException
	{
		page.SetCurrentPage("CC_EnrollmentForm");
		new WebElement(browser,"PermissionPersonalInfo").Click();
		/*
		 * new WebElement(browser,"PermissionPersonalInfo")
		 * 
		 * this.intakeForm.get('permissionsDetails').get("permissionsPII").value
		 */	
		new WebElement(browser,"PermissionChat").Click();
		
		  if(new WebElement(browser,"PermissionsCon").Exists())
				ReportEvents.Reporter("Pass", "Page Validation:- ", "Mandatory Permission is selected successfully.");
			  else
				  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while selecting mandatory Permission.");
		  
		new Button(browser,"SaveAndContinue").Click();
		new Button(browser,"TermsAndCondition").Click();
		
		if(new WebElement(browser,"TermsAndConditionCond").Exists())
			ReportEvents.Reporter("Pass", "Page Validation:- ", "I hereby consent is selected successfully.");
		  else
			  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while selecting I hereby consent.");
		new WebElement(browser,"SaveAndFinish").Click();
		
		if(new WebElement(browser,"CreatePasswordCond").Exists())
			ReportEvents.Reporter("Pass", "Page Validation:- ", "User successfully navigates to the Create Password page.");
		  else
			  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while navigating to the Create Password page.");
		
		
	}
	
	private void setPassword() throws IOException, InterruptedException
	{
		page.SetCurrentPage("CreatePasswordPage");
		String password = testdata.get("Password"); 
		new TextBox(browser,"Password").SendKeys(password);
		new TextBox(browser,"ConfirmPassword").SendKeys(password);
		new WebElement(browser,"Save").Click();
		
		if(new WebElement(browser,"RegistrationSuccessfulCond").Exists())
			ReportEvents.Reporter("Pass", "Page Validation:- ", "User successfully sets the password and navigated to the Registration Successfull page.");
		  else
			  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while setting the password.");
		
		new WebElement(browser,"RegistrationSuccessfulOK").Click();
		
		if(new WebElement(browser,"SignInPageCond").Exists())
			ReportEvents.Reporter("Pass", "Page Validation:- ", "User successfully navigates to the Sign In page.");
		  else
			  ReportEvents.Reporter("Fail", "Page Validation:- ", "Error: Error while navigating to the Sign In page..");
		
	}
	
	
	
	private void close_browser() throws Exception
	{
		browser.Quit();
	}

}
}