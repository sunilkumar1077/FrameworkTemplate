package FrameworkSource.web;

import java.io.IOException;

/**
 * The class consists of all the functions relates to Image of web page.
 */
public class Image extends WebUIElement{
	/**
	 * Image class constructor, directly invoked when an object of Image class is created. Class Image extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	public Image(Browser b,String Elementvalue)
	{
		//<img> tag
		super(b,Elementvalue);
	}
	
	public Image(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}

}
