package FrameworkSource.web;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import FrameworkSource.global.reporter.*;
/**
 * The class consists of all the functions related to Tables.
 */

public class Table extends WebUIElement {
	/**
	 * Table class constructor, directly invoked when an object of table class is created. Class table extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */	
		
	public Table(Browser b,String Elementvalue)
	{
		super(b,Elementvalue);
	}
	
	public Table(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
	/**
	 * The function counts all the rows with in a web table.
	 * @return integer value.
	 * @throws IOException 
	 */
	
public int RowCount() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		List<WebElement>  rows = null;
		try{
		 rows = b.element.findElements(By.tagName("tr"));
		 ReportEvents.Done(callerClassName+":Table","Total Row Count: "+ rows.size(),b);
		// Logger.INFO("Table", "Total Row Count: "+ rows.size());
		return rows.size();
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":Table", e,b);
			//Logger.ERROR("Table", e);
			return rows.size();
		}
	}
/**
 * The function counts all the columns in a web table.
 * @return integer value.
 * @throws IOException 
 */
	public int ColumnCount() throws IOException
	{	
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		List<WebElement>  rows = b.element.findElements(By.tagName("tr"));
		int Columncount [] = new int[rows.size()];
		for (int row = 0; row < rows.size(); row++) 
		{
			List<WebElement> Columns = rows.get(row).findElements(By.tagName("td"));
			Columncount[row]=Columns.size();
		}
		ReportEvents.Done(callerClassName+":Table","Total Column Count: "+GetMax(Columncount),b);
		//Logger.INFO("Table", "Total Column Count: "+GetMax(Columncount));
		return GetMax(Columncount);
	}
	/**
	 * The function returns the maximum value from a array.
	 * @param inputArray is a array value.
	 * @return Integer value.
	 */
	public int GetMax(int[] inputArray)
	{ 
	    int maxValue = inputArray[0]; 
	    for(int i=1;i < inputArray.length;i++)
	    { 
	      if(inputArray[i] > maxValue){ 
	         maxValue = inputArray[i]; 
	      } 
	    } 
	    return maxValue; 
	  }
	/**
	 * The function determine dimension count of the table.
	 * @return integer size of the table.
	 * @throws IOException 
	 */
	public int DimensionCount() throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		int ColumnsSize = 0;
		int HeadersSize = 0;
		List<WebElement>  rows = b.element.findElements(By.tagName("tr"));
		for (int row = 0; row < rows.size(); row++) 
		{
			List<WebElement> Columns = rows.get(row).findElements(By.tagName("td"));
			List<WebElement> Headers = rows.get(row).findElements(By.tagName("th"));
			ColumnsSize=ColumnsSize+Columns.size();
			HeadersSize = HeadersSize+Headers.size();	
		}
		ReportEvents.Done(callerClassName+":Table","Total Dimension Count : "+rows.size()*ColumnsSize*HeadersSize,b);
		//Logger.INFO("Table", "Total Dimension Count : "+rows.size()*ColumnsSize*HeadersSize);
		return rows.size()*ColumnsSize*HeadersSize;
	}
	/**
	 * The function determine the cell value present in given row number and column number.
	 * @param rownum takes row number.
	 * @param columnnum takes column number.
	 * @return String value.
	 * @throws IOException 
	 */
	public String GetCellData(int rownum, int columnnum) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		List<WebElement>  rows = b.element.findElements(By.tagName("tr"));	
		if(rownum ==1)
		{
			List<WebElement>  Header = rows.get(0).findElements(By.tagName("th"));
			ReportEvents.Done(callerClassName+":Table","Value of the cell data: "+ Header.get(columnnum-1).getText(),b);
			//Logger.INFO("Table", "Value of the cell data: "+ Header.get(columnnum-1).getText());
			return Header.get(columnnum-1).getText();
		}
		else
		{
			List<WebElement> Columns = rows.get(rownum-1).findElements(By.tagName("td"));
			ReportEvents.Done(callerClassName+":Table","Value of the cell data: "+ Columns.get(columnnum-1).getText(),b);
			//Logger.INFO("Table", "Value of the cell data: "+ Columns.get(columnnum-1).getText());
			return Columns.get(columnnum-1).getText();
		}
		
	}
	
	/**
	 * The function finds the row number of a table corresponding to a searched text.
	 * @param cellvalue takes String to be searched.
	 * @return .
	 * @throws IOException 
	 */
	public int GetRowWithCellText(String cellvalue) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		int rowvalue = 0;
		List<WebElement>  rows = b.element.findElements(By.tagName("tr"));
		for (int row = 0; row < rows.size(); row++) 
		{
			List<WebElement> Columns = rows.get(row).findElements(By.tagName("td"));
			List<WebElement> Header = rows.get(row).findElements(By.tagName("th"));
			int HeaderSize=Header.size();
			int Columnsize=Columns.size();
			for(int column = 0;column<Columnsize;column++)
			{
				if(cellvalue.equals(Columns.get(column).getText()))
				{
					rowvalue = row;
					break;
				}
			}
			for(int header = 0;header<HeaderSize;header++)
			{
				if(cellvalue.equals(Header.get(header).getText()))
				{
					rowvalue = row;
					break;
				}
			}
		}
		ReportEvents.Done(callerClassName+":Table","Row value with the specific Cell text"+ rowvalue+1,b);
		//Logger.INFO("Table", "Row value with the specific Cell text"+ rowvalue+1);
		return rowvalue+1;	
		}
	
}
