package FrameworkSource.web;

import java.io.IOException;

/**
 * The class consists of all the functions relates to Label of web page.
 */
public class Label extends WebUIElement {
	/**
	 * Label class constructor, directly invoked when an object of Label class is created. Class Label extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
	
public Label(Browser b,String Elementvalue)
{
	super(b,Elementvalue);
}

public Label(String strProp) throws IOException, InterruptedException
{
	super(strProp);
}
}


