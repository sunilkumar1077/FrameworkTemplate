package FrameworkSource.global.Initialize;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;

import FrameworkSource.global.reporter.ReportEvents;
import FrameworkSource.web.Browser;


public class ClsIntialize {

	public static Properties properties;
	public static String FilePath = System.getProperty("user.dir")+"\\src\\test\\java\\Data\\";
	public static Browser objBrowser;
	private static int defaultSleep = 2000;
	private static int intMillis = 1000;
	
	
	public static Properties fnGetProp() throws IOException
	{
		if (properties == null)
			fnGetProperty();
		return properties;
	}
	
	private static void fnGetProperty() throws IOException
	{
		String propertyFilePath= System.getProperty("user.dir")+"\\src\\test\\resources\\configuration\\Configuration.properties";
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(propertyFilePath));
		properties = new Properties();
		properties.load(reader);
		reader.close();
	}
	
	public static boolean waitForElementToAppear(WebElement UIElement , int seconds,String strElmName) throws IOException {
        boolean flag = false;
        long end = getCurrentTime() + (seconds * intMillis);
        while (getCurrentTime() < end) {
            try {
                if (UIElement !=null ) {
                    Thread.sleep(defaultSleep);
                    ReportEvents.Done("Dynamic Wait", "Waiting for "+ strElmName + "Element to Appear");
                } else {
                    flag = true;
                    break;
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(defaultSleep);
                    ReportEvents.Done("Dynamic Wait", "Waiting for "+ strElmName + "Element to Appear with default Sleep");
                } catch (Exception f) {
                	ReportEvents.Done("Dynamic Wait", "Waiting for "+ strElmName + "Element to Appear with default Sleep");
                }
            }
        }
        return flag;
    }
	
	private static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
