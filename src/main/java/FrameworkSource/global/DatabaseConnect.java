package FrameworkSource.global;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import FrameworkSource.global.reporter.ReportEvents;

public class DatabaseConnect{

	  public String URL = null;
	  public String UserName = null;
	  public String Password = null;
	  public Connection conn = null;
	public DatabaseConnect(String DatabaseURL, String UserName, String Password) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		String dbURL = null;
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbURL = "jdbc:sqlserver://"+DatabaseURL+";user="+UserName+";password="+Password+"";
		conn = DriverManager.getConnection(dbURL);	
		ReportEvents.Done(callerClassName+":DatabaseConnect","Connection to Database: "+DatabaseURL+" successfully done.");
		}
		catch(Exception e)
		{
			ReportEvents.Error(callerClassName+":DatabaseConnect",e);
		}
		
	}
	public int InsertQuery(String SQLCommand) throws SQLException
	{
		boolean updatecheck = false;
		int iRowID = 0;
	    PreparedStatement stmt = null;
		String callerClassName = new Exception().getStackTrace()[2].getClassName();
		String sqlQuery;
		try
		{
			if(conn != null)
			{
				sqlQuery = SQLCommand;
				try{
				stmt = conn.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				iRowID = rs.getInt(1);
		        ReportEvents.Done(callerClassName+":InsertQuery","Data Base updated with execution Results");
				}
				catch(Exception e)
				{
					ReportEvents.Error(callerClassName+":InsertQuery",e);
				}
			}
			else
			{
				ReportEvents.Fatal(callerClassName+":InsertQuery","No Database connection exist.");
			}
		}
		catch (Exception e)
		{
			updatecheck = false;
		}
		conn.close();
		return iRowID;     
	}
	public void DeleteQuery(String SQLCommand) throws SQLException
	{
		boolean updatecheck = false;
		int iRowID = 0;
	    PreparedStatement stmt = null;
		String callerClassName = new Exception().getStackTrace()[2].getClassName();
		String sqlQuery;
		try
		{
			if(conn != null)
			{
				sqlQuery = SQLCommand;
				try{
				stmt = conn.prepareStatement(sqlQuery);
				stmt.executeUpdate();
		        ReportEvents.Done(callerClassName+":DeleteQuery","DeleteQuery ran successfully in Database.");
				}
				catch(Exception e)
				{
					ReportEvents.Error(callerClassName+":DeleteQuery",e);
				}
			}
			else
			{
				ReportEvents.Fatal(callerClassName+":DeleteQuery","No Database connection exist.");
			}
		}
		catch (Exception e)
		{
			updatecheck = false;
		}
		
		conn.close();
	}
	public ArrayList<String[]> SelectQuery(String SQLCommand) throws IOException
	{
		String callerClassName = new Exception().getStackTrace()[2].getClassName();
		PreparedStatement stmt = null;
		ArrayList <String[]> result = new ArrayList<String[]>();
		if(conn != null)
		{
			try{
			stmt = conn.prepareStatement(SQLCommand);
			ResultSet rs = stmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			while(rs.next())
			{
			    String[] row = new String[columnCount];
			    for (int i=0; i <columnCount ; i++)
			    {
			       row[i] = rs.getString(i + 1);
			    }
			    result.add(row);
			}
			 ReportEvents.Done(callerClassName+":SelectQuery","SelectQuery ran successfully in Database.");
		}
			catch(Exception e)
			{
				ReportEvents.Error(callerClassName+":SelectQuery",e);
			}
		}
		else
		{
			ReportEvents.Fatal(callerClassName+":SelectQuery","No Database connection exist.");
		}
		
		return result;
		
	}
	public void UpdateQuery(String SQLCommand) throws SQLException
	{
		boolean updatecheck = false;
		int iRowID = 0;
	    PreparedStatement stmt = null;
		String callerClassName = new Exception().getStackTrace()[2].getClassName();
		String sqlQuery;
		try
		{
			if(conn != null)
			{
				sqlQuery = SQLCommand;
				try{
				stmt = conn.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate();
		        ReportEvents.Done(callerClassName+":UpdateQuery","Data Base updated with execution Results");
				}
				catch(Exception e)
				{
					ReportEvents.Error(callerClassName+":UpdateQuery",e);
				}
			}
			else
			{
				ReportEvents.Fatal(callerClassName+":UpdateQuery","No Database connection exist.");
			}
		}
		catch (Exception e)
		{
			updatecheck = false;
		}
		conn.close();
		    
	}
}
