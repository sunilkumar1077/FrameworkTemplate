package FrameworkSource.web;

import java.io.IOException;

/**
 * The class consists of all the functions relates to Hyperlinks of web page.
 */
public class HyperLink extends WebUIElement {
	/**
	 * Hyperlink class constructor, directly invoked when an object of Hyperlink class is created. Class Hyperlink extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	public HyperLink(Browser b,String Elementvalue)
	{
		super(b,Elementvalue);
	}
	
	public HyperLink(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}


}

