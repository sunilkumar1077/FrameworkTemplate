package FrameworkSource.global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import CentralConfig.DB_UpdateMetrics;
import FrameworkSource.global.Initialize.ClsIntialize;
import FrameworkSource.global.reporter.ReportEvents;
/**
 * DataReader class consists of all the functions related to external data sheet.
 */
public class DataReader extends ClsIntialize
{
	
	public String clName = "",Testname, OS,Browser , URL,ClassName,WebApp,Environment,DataTable = null,sheetpresent;
	public int StartRow, EndRow; 
	HashMap<String, String>  hm;
	String[][] arr;
	LinkedHashMap<String,String> hml;
	DateFormat dtStartDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date dtStartDate = new Date();
	int present = 0;
	
	public String strStartDate;
	public static HashMap<String, Integer> mapDBMetricesData = new LinkedHashMap<String, Integer>();
	public static HashMap<String, String> mapExecutionStatus = new LinkedHashMap<String, String>();
	public int iIterations = 0;
	public static LinkedHashMap<String, Integer> mapIteration = new LinkedHashMap<String, Integer>();
	public static LinkedHashMap<String, String> mapIterationStatus = new LinkedHashMap<String, String>();;
	public int iRowID = 0;
	public static Properties properties;
	
	private static final String LOG_TYPE = "LogType";
	private static final String DEBUG = "debug";
	private static final String LOG_DATAREADER = ":PreRequisites:DataReader";
	
	/**
	* The constructor retrieves different values of columns corresponding to specified data sheet.
	* @param className takes the name of data sheet inside Data.xlsx workbook.
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	*/
	
	public DataReader(String className) throws EncryptedDocumentException, InvalidFormatException, IOException  
	{
		try{
			String strTestcaseName = new Exception().getStackTrace()[1].getFileName();
			new ReportEvents(strTestcaseName);
			fnDataRead(className,strTestcaseName);
		}
		catch(Exception e){
			if(properties.getProperty(LOG_TYPE).equalsIgnoreCase(DEBUG))
				ReportEvents.Fatal(ClassName+LOG_DATAREADER, "Some Problem Occurs in Reading Data::" + e.getMessage());
			 Assert.fail();
		}
	     	
	}//End of Constructor 

	private void fnDataRead(String className,String strTestcaseName) throws IOException, ClassNotFoundException, SQLException, EncryptedDocumentException, InvalidFormatException {
				
				File file = null;
				FileInputStream inputStream;
				Workbook workbook = null;
				XSSFSheet sheet;
				DataFormatter formatter = new DataFormatter();;
				int rowCount,column_count;
				
				fnGetProperty();
				//String FilePath = System.getProperty("user.dir")+"\\src\\test\\java\\Data\\";
				String strDataSheet = properties.getProperty("TestData_FileName").trim();
				
				fnInitUpdateDBMetrices(strTestcaseName);
				this.clName = className;
				
			    for(int iDataSheet = 0;iDataSheet<strDataSheet.split(";").length;iDataSheet++){
			    	fnCheckExtension(FilePath, strDataSheet, iDataSheet);
					file = new File(DataTable);
					inputStream = new FileInputStream(file);
				    workbook = WorkbookFactory.create(inputStream);
				    sheet = (XSSFSheet) workbook.getSheet("Environment");
				   		    		  
				    rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
				    column_count = sheet.getRow(0).getPhysicalNumberOfCells();
				    fnGetEnvData(className, sheet, formatter, rowCount, column_count);//End of for
				  
				    if(present==1)
				    	break;
				}
			    if(present==0){
			    	if(properties.getProperty(LOG_TYPE).equalsIgnoreCase(DEBUG))
		    			ReportEvents.Fatal(ClassName+LOG_DATAREADER, className+" sheet name not found in Data_Mobile.xlsx");
					System.exit(0);
				    Assert.fail();
			    }
			    
		 	    /***************Fetching TestCase sheet values*****************/
		 		GetData();
			}
		


	private void fnInitUpdateDBMetrices(String strTestcaseName) throws SQLException, ClassNotFoundException, IOException {
		String strCompName,strUserName;
		Timestamp startingTime = null;
		
		if(!mapIteration.containsKey(strTestcaseName.replace(".java", ""))){
			iIterations = 0;
			strStartDate = dtStartDateFormat.format(dtStartDate);
			mapIteration.put(strTestcaseName.replace(".java", ""), iIterations);
		}

		DB_UpdateMetrics.InitiateDBconn();
		strCompName = DB_UpdateMetrics.getComputerName();
		strUserName = System.getProperty("user.name");
		startingTime = fnSetDBStartTimeStamp(startingTime);

		iRowID = DB_UpdateMetrics.UpdateEecutionMetrics(strTestcaseName, iIterations, startingTime, null,strCompName, strUserName, "Not Completed"); 
		mapExecutionStatus.put(strTestcaseName, "NC");
		mapDBMetricesData.put(strTestcaseName,iRowID);
	}

	private void fnCheckExtension(String FilePath, String strDataSheet, int iDataSheet) {
		if (strDataSheet.split(";")[iDataSheet].contains(".xlsx"))
			this.DataTable = FilePath+"\\"+strDataSheet.split(";")[iDataSheet];
		else
			this.DataTable = FilePath+strDataSheet.split(";")[iDataSheet] + ".xlsx";
	}

	private void fnGetEnvData(String className, XSSFSheet sheet, DataFormatter formatter, int rowCount,int column_count) throws IOException {
		Row row;
		String firstRow;
		for (int i = 1; i < rowCount + 1; i++) {
			row = sheet.getRow(i);
			firstRow = formatter.formatCellValue(row.getCell(0));

			if (firstRow.equalsIgnoreCase(className) && (column_count == 6)) {
				present = 1;
				fnGetTestDetails(formatter, row, firstRow);
				try {
					Environment = formatter.formatCellValue(row.getCell(4));
			     	 WebApp = formatter.formatCellValue(row.getCell(5));
				} catch (Exception e) {
						ReportEvents.Fatal(ClassName + LOG_DATAREADER,
								"Formatting Issues while retrieving data from Data_Mobile.xlsx",true);
				}
				break;
			} else if (firstRow.equalsIgnoreCase(className) && (column_count == 4)) {
				present = 1;
				fnGetTestDetails(formatter, row, firstRow);
				break;
			} 

			

		}
		
		if(present == 0)
				ReportEvents.Fatal(ClassName + LOG_DATAREADER," Incorrect Count of columns present in Data_Mobile.xlsx",true);
		
	}

	private void fnGetTestDetails(DataFormatter formatter, Row row, String firstRow) {
		Testname = firstRow;
		 StartRow = Integer.parseInt(formatter.formatCellValue(row.getCell(1)));
		 EndRow = Integer.parseInt(formatter.formatCellValue(row.getCell(2)));
		 Browser = formatter.formatCellValue(row.getCell(3));
		 
	}

	private Timestamp fnSetDBStartTimeStamp(Timestamp startingTime) {
		Calendar cal;
		dtStartDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		dtStartDate = new Date();
		strStartDate = dtStartDateFormat.format(dtStartDate);
		try{
			Timestamp StarttimeStamp = DB_UpdateMetrics.getStartingTimestamp(strStartDate); //Store the Starting Time from application.html session start time
			//Convert start sec to  Test execution Starting time
			cal = Calendar.getInstance();
			cal.setTimeInMillis(StarttimeStamp.getTime());
			startingTime = new Timestamp( cal.getTime().getTime());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return startingTime;
	}
	
	private static void fnGetProperty() throws IOException
	{
		/*String propertyFilePath= System.getProperty("user.dir")+"\\src\\test\\resources\\configuration\\Configuration.properties";
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(propertyFilePath));
		properties = new Properties();
		properties.load(reader);
		reader.close();*/
		
		properties = fnGetProp();
	}
	

	public DataReader(String className,String excelName) throws EncryptedDocumentException, InvalidFormatException, IOException, ClassNotFoundException, SQLException  
	{
		String strTestcaseName = new Exception().getStackTrace()[1].getFileName();
		fnDataRead(className,strTestcaseName);
		
	}//End of Constructor 

	

	/**
	 * This method is for backward compatibility, for returning the 2D array
	 * @return arr
	 * @throws IOException
	 */
	public String[][] getData() throws IOException
    {
          return arr;
    }
	
	/**
	 * Function takes input as array and converts it into hashmap and it is for backward compatibilty
	 * @param arr
	 * @param i
	 * @return
	 * @throws IOException
	 */
    
	/*
	 * This function is to provide the backward compatibility to the 250 scripts.
	 * This function will return read the linked hashmap and generate the 1D Array
	 * */
	
	public String[] SingleArr(String[][] arr,int i) throws IOException
    {
    	 String[] arrMod = null;
    	try{
	    	  int len = arr[0].length;  	           
	    	  arrMod = new String[len];
	          HashMap<String, String> hm_testdata_modified;
	          hm_testdata_modified = getTestData(i);
	          int iCount = 0;
	          for(Entry<String, String> entry: hml.entrySet())
	          {
	                 if (entry.getValue()!=null)
	                 {
	                        arrMod[iCount] =entry.getValue(); 
	                        iCount = iCount +1; 
	                 }
	          }
    	}catch(Exception eDataArray)
    	{
    		arrMod = null;
			ReportEvents.Warning("Get The Data", eDataArray.getMessage(),true);
    	}
          return arrMod;
    }

	
	/**
	* The function returns a 2-D array from data sheet
	* @throws IOException 
	* @return String 2-D array type
	*/

	public void GetData() throws IOException 
	{
		String ClassName = new Exception().getStackTrace()[2].getClassName();
		sheetpresent = "";
		File file = new File(DataTable);
		FileInputStream fis1 = new FileInputStream(file);
		XSSFWorkbook workbook1 = new XSSFWorkbook(fis1);
		XSSFSheet TestDataSheet = workbook1.getSheet(clName);
		DataFormatter formatter = new DataFormatter();

		if (TestDataSheet == null) { // If sheet don't exists it can happen
										// there is no data at all

				ReportEvents.Done(ClassName + "$:" + LOG_DATAREADER,
						"WARNING: Sheet name " + clName + " not found",true);

			sheetpresent = "false";
		} else {

			if (sheetpresent.equals("false")) {
					ReportEvents.Warning(ClassName + "$:" + LOG_DATAREADER,
							"Data from sheet " + clName + " cannot be found.",true);
			} else if (TestDataSheet.getRow(0) == null || TestDataSheet.getRow(1) == null) {
					ReportEvents.Warning(ClassName + "$:" + LOG_DATAREADER,
							"Navigated to sheet name : " + clName,true);
			}

			else {
					ReportEvents.Done(ClassName + "$:" + LOG_DATAREADER,
							"Navigated to sheet name : " + clName,true);
					sheetpresent = "true";
				XSSFRow row1 = TestDataSheet.getRow(0);
				String data = null;
				arr = new String[TestDataSheet.getPhysicalNumberOfRows()][row1.getLastCellNum()];
				for (int k = 0; k < TestDataSheet.getPhysicalNumberOfRows(); k++) {
					for (int l = 0; l < row1.getLastCellNum(); l++) {
						Cell cell = TestDataSheet.getRow(k).getCell(l);
						try {

							if (cell.getCellFormula() != null) {
								switch (cell.getCachedFormulaResultType()) {
								case Cell.CELL_TYPE_NUMERIC:
									data = NumberToTextConverter.toText(cell.getNumericCellValue());
									break;
								case Cell.CELL_TYPE_STRING:
									data = cell.getRichStringCellValue().toString();
									break;
								default:
									break;
								}

							}
						} catch (Exception e) {
							data = formatter.formatCellValue(TestDataSheet.getRow(k).getCell(l));
						}
						arr[k][l] = data;
					}
				}

			}
		}

		workbook1.close();
	}
	
	
	
	
	
	/**
	 * This function returns the hashmap that contains headers and their value for that particular test case.
	 * @param i specifies the row number of that test case sheet
	 * @return
	 * @throws IOException
	 */
	public  HashMap<String, String> getTestData(int i) throws IOException 
	{
			String ClassName = new Exception().getStackTrace()[1].getClassName();
			HashMap<String, String> hm_testdata = new HashMap<>();
			try
			{
				hml = new LinkedHashMap<String,String>();
				
				 if(arr == null )
				 {
						 ReportEvents.Done(ClassName+ LOG_DATAREADER," Data returned is empty.",true);
				 }
				 else
				 {
				
					 for(int j=0;j<arr[0].length;j++)
					 	{
					
						 	String header_value = arr[0][j].toString();
						 	String respective_val = arr[i+StartRow][j].toString();
						 	hm_testdata.put(header_value, respective_val);
						 	hml.put(header_value, respective_val);
					 	}
				 }
						
			}
		catch(Exception e)
		{
			//System.out.println("Some Problem Occurs in the creation of testdata hashmap");
			ReportEvents.Fatal(ClassName+LOG_DATAREADER, "Some Problem Occurs in the creation of testdata hashmap",true);
		}	
		
		return hm_testdata;
		
	}
	
	
	/**
	* The function returns the hashmap from the commomdata sheet
	* @return Hasmmap hm
	 * @throws IOException 
	*/
	
	public  HashMap<String, String> getCommomData() throws IOException 
	{
		String ClassName = new Exception().getStackTrace()[1].getClassName();
		try{
			File file = new File(DataTable);
			FileInputStream inputStream = new FileInputStream(file);
		    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);	
		    List<String> sheetNames = new ArrayList<String>();
		    int flag = 0;
		    
	        for (int i=0; i<workbook.getNumberOfSheets(); i++) {
	        	if(workbook.getSheetName(i).equalsIgnoreCase("CommonData")){	
	        		flag = 1;
	        		XSSFSheet sheet = workbook.getSheet("CommonData");
	        		XSSFRow header = sheet.getRow(0);
	        		XSSFRow value = sheet.getRow(1);
	        		DataFormatter formatter = new DataFormatter();
	        		String header_value="",respective_value="";
	        		
	        		if(header!= null && value != null){ 
	        			int colnum = header.getLastCellNum();
	        			hm = new HashMap<>();
	        			for(int iColcount = 0 ;  iColcount<colnum ; iColcount++){
	        				header_value = formatter.formatCellValue(header.getCell(iColcount));
	        				try{
	        	    		
		        	    		if(value.getCell(iColcount).getCellFormula() != null){
		        	    			switch(value.getCell(iColcount).getCachedFormulaResultType()) {
			        	                case Cell.CELL_TYPE_NUMERIC:
			        	                	respective_value = NumberToTextConverter.toText(value.getCell(iColcount).getNumericCellValue());
			        	                   
			        	                    break;
			        	                case Cell.CELL_TYPE_STRING:
			        	                	respective_value = value.getCell(iColcount).getRichStringCellValue().toString();
			        	                    break;
			        	                    
			        	                default:
											break;
		        	    			}	
		        	    		}
	        	    		}catch(Exception e){
	        	    			respective_value = formatter.formatCellValue(sheet.getRow(1).getCell(iColcount));
	        	    		}
	        				
	        				hm.put(header_value,respective_value );
	        			}
	        		}
	        		else
	        		{
	        				ReportEvents.Done(ClassName+LOG_DATAREADER, " CommonData sheet is empty in Data_Mobile.xlsx",true);
	        		}
	        		break;
	        				
	        		}
	        	else
	        	{
	        		flag=0;
	        	}
	        	
			}
	        if(flag == 0)
	        {
	        		ReportEvents.Done(ClassName+LOG_DATAREADER, " CommonData sheet is not present in Data_Mobile.xlsx",true);
	        }
		}
		catch(Exception e)
		{
				ReportEvents.Fatal(ClassName+LOG_DATAREADER, "Some Problem Occurs in the creation of common data hashmap",true);
		}	
        
		return hm;
		}

	
	/**
	 * This function returns the input browser
	 * @return
	 */
	public String getBrowser()
	{
		if (Browser==null)
			return Browser="";
		else
				
		return Browser.trim();
	}
	
	/**
	 * This function returns the input URL
	 * @return
	 */
	public String getUrl() 
	{
		if (URL==null)
		return URL="";
		else
		return URL.trim();
	}
	
	/*public String getUrl() throws Exception
	{
        File file = new File(DataTable);
		FileInputStream inputStream = new FileInputStream(file);
	    Workbook workbook = null;
	    workbook = WorkbookFactory.create(inputStream);
	    Sheet sheet = workbook.getSheet("MasterURLSheet");
	    DataFormatter formatter = new DataFormatter();
	    int sit_stage;
	    String URL="";
  
	    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
	    
        if(Environment.equalsIgnoreCase("SIT"))
        {
        	sit_stage = 1; //For SIT
        }
        else{
        	sit_stage = 2; //For STAGE
        }

	    
	    for (int i = 1; i < rowCount+1; i++) {

	           Row row = sheet.getRow(i);
	           String firstRow = formatter.formatCellValue(row.getCell(0));
	        
	     if(firstRow.equalsIgnoreCase(WebApp))
	     {
	    	 //Go to particular cell 1 or 2 and just fetch value
	            URL = formatter.formatCellValue(row.getCell(sit_stage));

	    	 break;
	     }
	    }
		return URL;
	}*/
	
	/**
	 * This function returns the number of execution times
	 * @return
	 */
	public int noOfTimes()
	{
		int times = (EndRow-StartRow)+1;
		return times;
	}
	
	/**
	* The function returns the browser list as string array
	* @return String array type
	*/
	public String[] browser()
	{
		String[] times = Browser.split(",");
		return times;
	}
	
	/**
	* The function returns the URL corresponding to specified test case.
	* @return String type
	 * @throws Exception 
	*/
	
	public String getURL() throws Exception
	{
        File file = new File(DataTable);
		FileInputStream inputStream = new FileInputStream(file);
	    Workbook workbook = null;
	    workbook = WorkbookFactory.create(inputStream);
	    Sheet sheet = workbook.getSheet("MasterURLSheet");
	    DataFormatter formatter = new DataFormatter();
	    int envIs;
	    String URL="";
  
	    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
	    
        if(Environment.equalsIgnoreCase("Dev"))
        {
        	envIs = 1; //For Dev
        }
        else{
        	envIs = 2; //For Test
        }

	    
	    for (int i = 1; i < rowCount+1; i++) {

	           Row row = sheet.getRow(i);
	           String firstRow = formatter.formatCellValue(row.getCell(0));
	        
	     if(firstRow.equalsIgnoreCase(WebApp))
	     {
	    	 //Go to particular cell 1 or 2 and just fetch value
	            URL = formatter.formatCellValue(row.getCell(envIs));

	    	 break;
	     }
	    }
		return URL;
	}
	
	

	/**
	* The function returns the number of browsers specified inside Browsers column of Environment sheet
	* @return int type
	*/
	public int noOfBrowsers()
	{
		String[] no = browser();
		return no.length;
	}
		

	
}
	