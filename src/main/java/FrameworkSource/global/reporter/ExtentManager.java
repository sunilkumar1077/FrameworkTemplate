package FrameworkSource.global.reporter;

import java.util.Arrays;
import java.util.List;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
//import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {
    
    static ExtentReports extent;
    
    public static ExtentReports getInstance() {	
        return extent;
    }
    
    public static synchronized ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Alqimi Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setReportName("Alqimi Tests - Execution Results");
        htmlReporter.config().setTimeStampFormat("mm/dd/yyyy hh:mm:ss a");
        
        //htmlReporter.config().setT
        htmlReporter.config().setJS(" $(document).ready(function() {"
        		+ "$('td.status.info').parent().hide();"
        		+ "$('span.test-status.right.warning').text('Passed with Warnings');"
        		+ "$('a.brand-logo.blue.darken-3').text('Alqimi');"
				 + "$('span.label.blue.darken-3').hide();"
        		+ "});" + 	
				"$('i.material-icons').click(function(){"
				+ "$('td.status.info').parent().show();});");

       // htmlReporter.config().setJS("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
        //htmlReporter.
        //htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"\\src\\main\\resources\\extent.xml");
        //htmlReporter.config().setJS("$('.test.warning').each(function() { $(this).addClass('pass').removeClass('warning'); }); $('.test-status.warning').each(function() { $(this).addClass('pass').removeClass('warning').text('pass'); });$('.tests-quick-view .status.warning').each(function() { $(this).addClass('pass').removeClass('warning').text('PASS'); }); testSetChart(); ");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        List statusHierarchy = Arrays.asList(
                Status.FATAL,
                Status.FAIL,
                Status.ERROR,
                Status.PASS,
                Status.DEBUG,
                Status.INFO,
                Status.WARNING
        		);

        extent.config().statusConfigurator().setStatusHierarchy(statusHierarchy);
        
       extent.setSystemInfo("OS", System.getProperty("os.name").toLowerCase());
        return extent;
    }
    
}



