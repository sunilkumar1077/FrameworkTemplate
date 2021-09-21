package FrameworkSource.web;
/**
 * The class consists of all the functions related to WebElement.
 */
public class WebElement extends WebUIElement {
	/**
	 * WebElement class constructor, directly invoked when an object of WebElement class is created. Class List extends WebUIElement class.
	 * @param b is a static global variable in Browser class which maintains properties of open browser session.
	 * @param Elementvalue takes a string value.
	 */
		
	public WebElement(Browser b,String Elementvalue)
	{
		super(b,Elementvalue);
	}
}
