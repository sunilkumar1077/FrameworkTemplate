package FrameworkSource.web;

import java.io.IOException;

/**
 * The class consists of all the functions relates to buttons of web page.
 */
public class Button extends WebUIElement{
	/**
	 * Button class constructor, directly invoked when an object of Button class is created. Class Button extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	public Button(Browser b, String Elementvalue)
	{
		super(b,Elementvalue);
	}
	
	public Button(String strProp) throws IOException, InterruptedException
	{
		super(strProp);
	}
}