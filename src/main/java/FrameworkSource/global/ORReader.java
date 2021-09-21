package FrameworkSource.global;


import FrameworkSource.global.reporter.ReportEvents;

import FrameworkSource.web.Browser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * ORReader class consists of all the functions related to OR(Object Repository) i.e Properties.xlsx.
 */
public class ORReader {

	static LinkedHashMap<String, Object> mapOR = null;
	private static BufferedReader reader = null;
	private static Properties properties = null;
	private static DesiredCapabilities capabilities = null;
	final static String propertyFilePath= System.getProperty("user.dir") + "\\src\\test\\resources\\configuration\\Configuration.properties";
	final static String propertyFile= System.getProperty("user.dir") + "\\src\\test\\java\\OR\\";
	private static final String STREXCELEXTENSION = ".xlsx";
	
	Browser b;
	
	public LinkedHashMap fnGetOR() {
		return mapOR;
	}
	
	public Properties fnGetProperty()
	{
		return properties;
	}
	
	public Browser fnGetDevice()
	{
		return b;
	}
	
	public ORReader(Browser b) throws IOException
	{
		this.b = b;
		fngetProperties();
	}
	
	private static Properties fngetProperties() throws IOException
	{
		if (properties == null)
		{
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			properties.load(reader);
			reader.close();
		}
		
		return properties;
	}
	
	public String[][] Properties(String page) throws IOException, InterruptedException
	{
		try{
			
				ReportEvents.Done("Reading the OR File Using Excel", "Starting the Excel-OR Reading",true); 
				if (properties.getProperty("Properties_FileName").toString().contains(";"))
				{
					String[] arrMulPath = properties.getProperty("Properties_FileName").toString().split(";");
					for(int iCount = 0;iCount<arrMulPath.length;iCount++)
					{
						if(!arrMulPath[iCount].toString().contains(STREXCELEXTENSION))
							arrMulPath[iCount] = arrMulPath[iCount]+STREXCELEXTENSION;
						if (arrMulPath[iCount].length() > 0 && func_ReadExcel(arrMulPath[iCount],page))
						{
							//break;
						}
					}
				}
				else
				{
					func_ReadExcel( properties.getProperty("Properties_FileName").toString(),page);
				}
				ReportEvents.Done("Reading the OR File Using Excel", "Completed the Excel-OR Reading",true);
				
			}
		catch(Exception objRead)
		{
			ReportEvents.Error("Reading the OR File", objRead,true);
		}
		return null;
		
	}
	
	private boolean func_ReadExcel(String strSheet,String orSheet) throws IOException, InterruptedException
	{
		
		boolean blnResult = false;
		if(mapOR == null)
			mapOR = new LinkedHashMap<String, Object>();
		File file =    new File(propertyFile + strSheet);
	    FileInputStream inputStream = new FileInputStream(file);
	    Workbook workbook = null;
	    workbook = new XSSFWorkbook(inputStream);
	    Sheet sheet;

	    try{
	    	sheet = workbook.getSheet(orSheet);
	    	if(sheet == null)
	    	{
	    		ReportEvents.Done("WARNING::Error Locationg the sheet ","Sheet: "+orSheet+" is not present in excel " + strSheet);
	    		//Assert.fail();
	    		blnResult = false;
	    	}
	    	else
	    	{
			    for (int i = 1; i < sheet.getLastRowNum()-sheet.getFirstRowNum() +1; i++) 
			    {
			    	Row row = sheet.getRow(i);
			    	
			    	
		            		//if(clsInitalize.strLanguage.equalsIgnoreCase("english"))
			    			if(b.strLanguage.equalsIgnoreCase("English"))
		            		{
		            			mapOR.put(row.getCell(0).getStringCellValue().trim(), row.getCell(1).getStringCellValue().trim() + ";" + row.getCell(2).getStringCellValue().trim());
		            		}
		            		else
		            		{
		            			mapOR.put(row.getCell(0).getStringCellValue().trim(), row.getCell(1).getStringCellValue().trim() + ";" + row.getCell(3).getStringCellValue().trim());
		            		}
			    			
		            	 
			    }
			    blnResult = true;
                
	    	}
		    workbook.close();//Newly added
	    	
	    }catch(Exception obj)
	    {
	    	blnResult = false;
			//ReportEvents.Done("Error Reading the excel File", "Returning False");
	    }
	    
	   return blnResult;
	  
	}
	

	public boolean FindProperty(String Value) throws IOException
	{
		boolean blnResult = false;
		try{
			
			if(mapOR.get(Value)!= null)
			{
				blnResult = true;
			}
			else
			{
				blnResult = false;
			}
			
		}catch(Exception eOR)
		{
			blnResult = false;
			ReportEvents.Done("WARNING::Error Locationg Object","Unable to Locate the Element " + Value);
		}
		return blnResult;
	}
	
	public String[] FindExactvalue(String Value) throws IOException
	{

		String[] FoundValue = new String[2];
		try{
			
				FoundValue[0] = mapOR.get(Value).toString().trim().split(";")[0];
				FoundValue[1] = mapOR.get(Value).toString().trim().split(";")[1];
			
			ReportEvents.Done("Element Located in OR","Element with "+Value+" Found in OR",true);
		}catch(Exception objFindExactValue)
		{
			ReportEvents.Done("WARNING::Error Locationg Object","Unable to Locate the Element " + Value,true);
			FoundValue = null;
		}
		return FoundValue;
	
	}
	
	public boolean fnSetProperty(String strLogicalName, String strPropertyID, String strPropValue) throws IOException
	{
		boolean blnUpdateProp = false;
		try{
			
			if(mapOR.containsKey(strLogicalName))
			{
				mapOR.put(strLogicalName, strPropertyID + ";" + strPropValue);
				ReportEvents.Done("Element Located in OR","Element with "+strLogicalName+" Updated in Run Time OR: " + strPropertyID + " : " + strPropValue,true);
			}
			else
			{
				mapOR.put(strLogicalName, strPropertyID + ";" + strPropValue);
				ReportEvents.Done("Element Added in OR","Element with "+strLogicalName+" Updated in Run Time OR: " + strPropertyID + " : " + strPropValue,true);
			}

		}catch(Exception objFindExactValue)
		{
			ReportEvents.Done("WARNING::Error Adding Object to Runtime OR","Unable to Locate the Element " + strLogicalName,true);
		}
		return blnUpdateProp;
	
	}
	
	
}
