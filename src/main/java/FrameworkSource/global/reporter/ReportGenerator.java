package FrameworkSource.global.reporter;

public class ReportGenerator {
	
	public static boolean TestSuiteRun = false; 
	
	public static void Generate(String status) 
	{	
		if(!TestSuiteRun)
			ExtentManager.getInstance().flush();
	}
	
	public static void GenerateSuitReport(String status) 
	{
		try{
			ExtentManager.getInstance().flush();
		}catch(Exception e)
		{
			
		}
	}
	
}