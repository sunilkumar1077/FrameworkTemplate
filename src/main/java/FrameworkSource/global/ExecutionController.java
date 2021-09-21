package FrameworkSource.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
/**
 * ExecutionController class consists of function related to external data sheet i.e RunClassData.xlsx.
 */
public class ExecutionController {
	
	/**
     * The method retrieves different classes to be run with corresponding test suites.
	 * @throws Exception
	 * @return Class array type
 	*/
	public static Class[] ExecutionController() throws Exception
	{
		String DataTable = System.getProperty("user.dir") + "\\src\\test\\java\\Data\\RunClassData.xlsx";
		File file = new File(DataTable);
		
		FileInputStream inputStream = new FileInputStream(file);
	    Workbook workbook = null;
	    workbook = WorkbookFactory.create(inputStream);
	    Sheet ExecutionSheet = workbook.getSheet("ExecutionConfig");
	    Sheet MappingSheet = workbook.getSheet("ALMMapping");
	    DataFormatter formatter = new DataFormatter();
	    int rowCount = ExecutionSheet.getLastRowNum()-ExecutionSheet.getFirstRowNum();
	    int rowCount1 = MappingSheet.getLastRowNum()-MappingSheet.getFirstRowNum();
	    List<String> TestSuite = new ArrayList<String>();
	    List<String> TestClass = new ArrayList<String>();
	    for (int i = 1; i < rowCount+1; i++) {

	           Row row = ExecutionSheet.getRow(i);
	           String firstRow = formatter.formatCellValue(row.getCell(2));
	           if(firstRow.equalsIgnoreCase("Y"))
	           {
	        	   TestSuite.add(formatter.formatCellValue(row.getCell(0)));
	    //    	   System.out.println("Suite name is: "+formatter.formatCellValue(row.getCell(0)));
	           }
	    }
	    for (int i = 1; i < rowCount1+1; i++) {

	           Row row = MappingSheet.getRow(i);
	           String firstRow = formatter.formatCellValue(row.getCell(2));
	      //     System.out.println("Test suite from excel:"+firstRow);
	           for(int j=0;j<TestSuite.size();j++)
	           {
	        //	   System.out.println("Items from list: "+TestSuite.get(j));
	        	   if(firstRow.contains(TestSuite.get(j)))
	        	   {	
	        	//	   System.out.println("test case name: "+row.getCell(0));
	        		   TestClass.add(formatter.formatCellValue(row.getCell(0)));
	        		   break;
	        	   }
	           }
	           
	    } 
	    String TestClassName[]=new String[TestClass.size()];
	    Class TestClassForm[] = new Class[TestClass.size()];
	    ClassLoader cl = null;
	    for(int j=0;j<TestClass.size();j++)	
	    {
	    	TestClassName[j]="Tests."+TestClass.get(j);
	    	TestClassForm[j]=Class.forName(TestClassName[j]);    
	    }
		return TestClassForm;
		
	    
	}

}
