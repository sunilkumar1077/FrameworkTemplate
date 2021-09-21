package FrameworkSource.web;

import FrameworkSource.global.*;
import FrameworkSource.global.Initialize.ClsIntialize;
import FrameworkSource.global.POM.AndroidLocator;
import FrameworkSource.global.POM.IOSLocator;
import FrameworkSource.global.POM.WebLocator_Desktop;
import FrameworkSource.global.reporter.*;
import framework_global.reporter.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUIElement extends ClsIntialize {

	Browser b;
	List<WebElement> element = null;
	static final String WEBUIELEMENTOBJ = ":WebUIElement";
	static final String ELEMENTOBJ = "Element: ";
	static final String EXCEPTION = "Exception";

	public WebUIElement(String strFunctionName) throws IOException {
		b = objBrowser;
		String className = new Exception().getStackTrace()[2].toString();
		String strFinalClassName = fnGetAnnotationPackage(className);
		String strTempParam;

		fnGetProp_Desktop(strFunctionName, strFinalClassName);
		// mobileelemenlLocation();
		try {
			webelementlocation();
		} catch (Exception e) {

			b.element = null; // Setting element to null as if user calls
								// function like exists. Then this function will
								// perform operation on previous b.elment
		}

	}

	private void fnGetProp_Desktop(String strFunctionName, String strFinalClassName) {
		try {
			for (Method field : Class.forName(strFinalClassName).getDeclaredMethods()) {

				if (field.getName().equalsIgnoreCase(strFunctionName)) {
					Annotation annotation = field.getAnnotation(WebLocator_Desktop.class);
					if (annotation instanceof WebLocator_Desktop) {
						WebLocator_Desktop customAnnotation = (WebLocator_Desktop) annotation;
						b.FoundValue = new String[2];
						b.FoundValue[0] = customAnnotation.PropertyType();
						b.FoundValue[1] = customAnnotation.value();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String fnGetAnnotationPackage(String className) {
		className = className.split("\\(")[0];
		String strFinalClassName = "";
		String[] arrPackage = className.split("\\.");
		for (int iCount = 0; iCount < arrPackage.length - 1; iCount++) {
			if (iCount != arrPackage.length - 2)
				strFinalClassName = strFinalClassName + arrPackage[iCount] + ".";
			else
				strFinalClassName = strFinalClassName + arrPackage[iCount];
		}
		return strFinalClassName;
	}

	/**
	 * WebUIElement class constructor, directly invoked when an object of
	 * WebUIElement class is created. Constructor calls FindExactvalue function
	 * in ORReader class. Webelementlocation function is also invoked which
	 * returns an element value.
	 * 
	 * @param b
	 *            is a static global variable in Browser class which maintains
	 *            properties of open browser session.
	 * @param Elementvalue
	 *            takes a string value.
	 */
	public WebUIElement(Browser b, String elememntvalue) {

		final String ClassName = new Exception().getStackTrace()[2].getMethodName();

		try {
			this.b = b;
			boolean elementProperty = new ORReader(b).FindProperty(elememntvalue);
			if (elementProperty) {
				b.FoundValue = new ORReader(b).FindExactvalue(elememntvalue);

				ReportEvents.Done(ClassName + ":ElementValue",
						"Property Values returned from Properties.xlsx: " + b.FoundValue[0] + " & " + b.FoundValue[1]);
				webelementlocation();

			} else {
				ReportEvents.Fatal(ClassName + ":ElementValue",
						"Element '" + elememntvalue + "' Not Exists in the Properties Excel sheet");
				b.element = null; // Setting element to null as if user calls
									// function like exists. Then this function
									// will perform operation on previous
									// b.elment
			}
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			b.element = null; // Setting element to null as if user calls
								// function like exists. Then this function will
								// perform operation on previous b.elment
		}
	}

	/**
	 * Single parameterized constructor of WebUiElement
	 * 
	 * @param Browser
	 *            type
	 */
	public WebUIElement(Browser b) {
		this.b = b;
	}

	/**
	 * The function searches the element present on the web page depending upon
	 * various parameters.
	 * 
	 * @return WebElement type
	 * @throws IOException
	 */
	public List<WebElement> webelementlocation() throws IOException {
		String strProperty = b.FoundValue[0];
		String strPropertyValue = b.FoundValue[1];
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		int iWaitTime = Integer.parseInt(ClsIntialize.fnGetProp().getProperty("WaitTime"));
		try {
			switch (strProperty.toUpperCase()) {
			case "ID":
				element = b.driver.findElements(By.id(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "XPATH":
				element = b.driver.findElements(By.xpath(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "CLASS":
				element = b.driver.findElements(By.className(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "LINKTEXT":
				element = b.driver.findElements(By.linkText(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "PARTIALLINKTEXT":
				element = b.driver.findElements(By.partialLinkText(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "CSSSELECTOR":
				element = b.driver.findElements(By.cssSelector(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "NAME":
				element = b.driver.findElements(By.name(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			case "TAGNAME":
				element = b.driver.findElements(By.tagName(strPropertyValue));
				new WebDriverWait(b.driver, iWaitTime).until(ExpectedConditions.visibilityOfAllElements(element));
				break;

			default:
				ReportEvents.Done(callerClassName + ":UIElement: Finding Element",
						"UIStrategy: " + strProperty + " is not valid.");
				break;
			}
			b.element = element.get(0);
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			b.element = null;
			ReportEvents.Done(callerClassName + ":UIElement: Finding Element", "Issue occurs while finding element");
		}
		return element;

	}

	/**
	 * The function finds multiple elements
	 * 
	 * @throws IOException
	 */
	public List<WebElement> FindElements() {
		return element;

	}

	/**
	 * The function clicks on the web element.
	 * 
	 * @throws IOException
	 */
	public void Click() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {
			b.element.click();
			ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + "is clicked successfully.",
					b);
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			ReportEvents.Error(callerClassName + WEBUIELEMENTOBJ, e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The function sends the string value to the text box.
	 * 
	 * @param value
	 *            takes string value.
	 * @throws IOException
	 */
	public void SendKeys(CharSequence value) throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {
			if (value != null) {
				b.element.clear();
				b.element.sendKeys(value);
				ReportEvents.Done(callerClassName + ":WebUIElement",
						"Values are sucessfully entered in textbox." + value, b);
			} else {
				ReportEvents.Fatal(callerClassName + ":WebUIElement", "Value entered is not correct i.e. ." + value, b);
			}
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
		}
	}

	/**
	 * The function verifies different message labels being displayed.
	 * 
	 * @param value
	 *            takes an String value to be verified.
	 * @return boolean value. Returns True if the message is same and False if
	 *         not.
	 * @throws IOException
	 */
	public boolean VerifyMessage(String expmessage) throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		boolean flag = false;
		try {
			String actualmessage = b.element.getText();
			if (expmessage.equals(actualmessage)) {
				flag = true;
				ReportEvents.Done(callerClassName + ":WebUIElement", "Message " + expmessage + "is displayed.", b);
			} else {
				flag = false;
				ReportEvents.Done(callerClassName + ":WebUIElement", "Message " + expmessage + "is not displayed.", b);
			}
		} catch (Exception e) {
			flag = false;
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
		return flag;
	}

	/**
	 * The function checks on the existence of the web element.
	 * 
	 * @return boolean value. Returns True if the element is found else false.
	 * @throws IOException
	 */
	public boolean Exists() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		boolean flag = false;
		try {
			if (b.element != null) {
				if (b.element.isDisplayed()) {
					flag = true;
					ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " exists.", b);
				} else {
					flag = false;
					ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " doesn't exists.",
							b);
				}
			} else {
				flag = false;
				ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " doesn't exists.", b);

			}
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			flag = false;
			ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, "Element doesn't exists.");
		}

		return flag;
	}

	/**
	 * The function will highlight a web element with red color rectangular box.
	 * 
	 * @throws IOException
	 */
	public void Highlight() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();

		if (b.driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) b.driver).executeScript("arguments[0].style.border='3px solid red'", b.element);
			ReportEvents.Done(callerClassName + ":WebUIElement", "Element :" + b.element + " highlighted.", b);
		}

	}

	/**
	 * The function will highlight a web element with red color rectangular box
	 * for specified seconds.
	 * 
	 * @param seconds
	 *            accept integer values.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void Highlight(int seconds) throws IOException, InterruptedException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		int total = Integer.parseInt(seconds + "000");

		if (b.driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) b.driver).executeScript("arguments[0].style.border='3px solid red'", b.element);
			Thread.sleep(total);
			((JavascriptExecutor) b.driver).executeScript("arguments[0].setAttribute('style',arguments[0])", b.element);
			ReportEvents.Done(callerClassName + ":WebUIElement",
					"Element :" + b.element + " successfully highlighted for " + seconds + " seconds.", b);
		}

	}

	/**
	 * The function determines if an element is selected or not. It is widely
	 * used on check boxes, radio buttons and options in a select.
	 * 
	 * @return returns true if the element is selected and false if it is not.
	 * @throws IOException
	 */
	public boolean IsSelected() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		boolean flag = false;
		try {
			if (b.element.isSelected()) {
				flag = true;
				ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " is selected.", b);
			} else {
				flag = false;
				ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " is not selected.", b);
			}
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			flag = false;
			ReportEvents.Fatal(WEBUIELEMENTOBJ, "Element doesn't exists so cannot perform IsSelected operation");
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}

		return flag;

	}

	/**
	 * The function determines if an element is enabled or not.
	 * 
	 * @return returns true if element is enabled and false if not.
	 * @throws IOException
	 */
	public boolean IsEnabled() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		boolean flag = false;
		try {
			if (b.element.isEnabled()) {
				flag = true;
				ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " is enabled.", b);
			} else {
				flag = false;
				ReportEvents.Done(callerClassName + WEBUIELEMENTOBJ, ELEMENTOBJ + b.element + " is not enabled.", b);
			}
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			flag = false;
			ReportEvents.Fatal(WEBUIELEMENTOBJ, "Element doesn't exists so cannot perform IsEnabled operation");
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}

		return flag;
	}

	/**
	 * The function determines if an element is displayed or not.
	 * 
	 * @return returns true if element is displayed and false if not.
	 * @throws IOException
	 */
	public boolean IsDisplayed() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		boolean flag = false;
		try {
			if (b.element.isDisplayed()) {
				flag = true;
				ReportEvents.Done(callerClassName + ":WebUIElement", ELEMENTOBJ + b.element + " is displayed.", b);
			} else {
				flag = false;
				ReportEvents.Done(callerClassName + ":WebUIElement", ELEMENTOBJ + b.element + " is not displayed.", b);
			}
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			flag = false;
			ReportEvents.Fatal("WebUIElement", "Element doesn't exists so cannot perform IsDisplayed operation");
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}

		return flag;
	}

	/**
	 * This Function determines if an element is Empty or not.Example for Text
	 * Box with Default some text or Empty
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean IsEmpty() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		boolean flag = false;
		String text;
		try {
			text = b.element.getAttribute("value");
			if ((text.isEmpty())) {
				flag = true;
				ReportEvents.Done(callerClassName + ":WebUIElement", "Element: " + b.element + " is Empty.", b);
			} else {
				flag = false;
				ReportEvents.Done(callerClassName + ":WebUIElement", "Element: " + b.element + " is not Empty.", b);
			}
		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			flag = false;
			ReportEvents.Fatal("WebUIElement", "Element doesn't exists so cannot perform IsEmpty check");
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}

		return flag;
	}

	/**
	 * The function hovers the mouse pointer to the specified element.
	 * 
	 * @throws IOException
	 */
	public void MouseHover() throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		try {
			Actions object = new Actions(b.driver);
			object.moveToElement(b.element).build().perform();
			ReportEvents.Done(callerClassName + ":WebUIElement", "Mouse Hover to " + b.element + " done successfully.",
					b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The function returns text value of the web element.
	 * 
	 * @return String value.
	 * @throws IOException
	 */
	public String GetValue() throws IOException {

		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String text = null;
		try {
			text = b.element.getText();
			ReportEvents.Done(callerClassName + ":WebUIElement",
					"Text Value: " + b.element.getText() + " found successfully." + b.element.getText(), b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
		return text;

	}

	/**
	 * The function returns the attribute value of the attribute.
	 * 
	 * @param AttributeName
	 *            takes string value.
	 * @return String Value.
	 * @throws IOException
	 */

	public String AttributeValue(String attributeName) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String attribute = null;
		try {
			attribute = b.element.getAttribute(attributeName);
			ReportEvents.Done(callerClassName + ":WebUIElement",
					"Attribute Value Found successfully." + b.element.getAttribute(attributeName), b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
		return attribute;
	}

	/**
	 * The function find out the dimension size of the image.
	 * 
	 * @return Dimension size of the image.
	 */
	public Dimension Size() {
		return b.element.getSize();
	}

	/**
	 * The function find out the location of an element. Using object of Point
	 * we can find x and y coordinates of any element
	 * 
	 * @return Point i.e location of the element.
	 */
	public Point Location() {
		return b.element.getLocation();
	}

	/**
	 * The function performs different clicks of Mouse.
	 * 
	 * @param type
	 *            takes String value like Right, Double and Left.
	 * @throws IOException
	 */
	public void MouseClick(String type) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		try {

			if ("Right".equalsIgnoreCase(type)) {
				Actions action = new Actions(b.driver).contextClick(b.element);
				action.build().perform();
				ReportEvents.Done(callerClassName + ":WebUIElement",
						"Right Click is Pressed Successfully on element :" + b.element, b);
			} else if ("Double".equalsIgnoreCase(type)) {
				Actions actions = new Actions(b.driver).contextClick(b.element);
				actions.moveToElement(b.element).doubleClick().build().perform();
				ReportEvents.Done(callerClassName + ":WebUIElement",
						"Double Click is Pressed Successfully on element :" + b.element, b);
			} else if ("Left".equalsIgnoreCase(type)) {
				Actions actions = new Actions(b.driver).click(b.element);
				actions.moveToElement(b.element).click().build().perform();
				ReportEvents.Done(callerClassName + ":WebUIElement",
						"Left Click is Pressed Successfully on element :" + b.element, b);
			} else {
				ReportEvents.Fatal(callerClassName + ":WebUIElement",
						"Enter Valid Mouse Click out of: Right/Double/Left");
			}
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The functions moved the mouse cursor to defined xoffset and yoffset.
	 * 
	 * @param xOffset
	 *            takes integer value.
	 * @param yOffset
	 *            takes integer value.
	 * @throws IOException
	 */
	public void MouseMoveTo(int xOffset, int yOffset) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		try {
			Actions builder = new Actions(b.driver);
			builder.moveToElement(b.element, xOffset, yOffset).click().build().perform();
			ReportEvents.Done(callerClassName + ":WebUIElement",
					"Mouse successfully moved to location: " + xOffset + yOffset, b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The function inputs Ctrl along with other key from keyboard.
	 * 
	 * @param command
	 *            takes string value Ctrl.
	 * @param keyvalue
	 *            takes string value which needs to be pressed along with Ctrl
	 *            key.
	 * @throws IOException
	 */
	public void PressKeysWithCtrl(String keyvalue) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String keyvalue1 = keyvalue.toLowerCase();
		try {
			Actions act = new Actions(b.driver);
			act.keyDown(Keys.CONTROL).sendKeys(keyvalue1).keyUp(Keys.CONTROL).perform();
			ReportEvents.Done(callerClassName + ":WebUIElement", "Control Key is pressed successfully.", b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The function inputs various keys from keyboard specific to a web element.
	 * 
	 * @param command
	 *            takes string value which needs to be input from keyboard like
	 *            Down, Left, Up, Right, Backspace, Alt, Ctrl, Delete, Enter and
	 *            Spacebar
	 * @param times
	 *            takes a integer value of number of times the command is to be
	 *            executed
	 * @throws IOException
	 */
	public void PressKeyOnElement(String command, int times) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String command1 = command.substring(0, 1).toUpperCase() + command.substring(1).toLowerCase();
		try {

			switch (command1) {
			case "Down":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.ARROW_DOWN);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Arrow Down Key is pressed successfully.", b);
				break;
			case "Left":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.ARROW_LEFT);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Arrow Left Key is pressed successfully.", b);
				break;
			case "Up":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.ARROW_UP);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Arrow Up Key is pressed successfully.", b);
				break;
			case "Right":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.ARROW_RIGHT);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Arrow Right Key is pressed successfully.", b);
				break;
			case "Backspace":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.BACK_SPACE);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Back space Key is pressed successfully.", b);
				break;
			case "Delete":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.DELETE);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Delete Key is pressed successfully.", b);
				break;
			case "Enter":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.ENTER);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Enter Key is pressed successfully.", b);
				break;
			case "Tab":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.TAB);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Tab Key is pressed successfully.", b);
				break;
			case "Spacebar":
				for (int i = 1; i <= times; i++) {
					b.element.sendKeys(Keys.SPACE);
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Space Key is pressed successfully.", b);
				break;
			default:
				ReportEvents.Done(callerClassName + ":WebUIElement", "Entered Command " + command1 + " is not correct",
						b);
				break;
			}
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The function inputs various keys from keyboard on Element using Action
	 * Class.
	 * 
	 * @param command
	 *            takes string value which needs to be input from keyboard like
	 *            Down, Left, Up, Right, Backspace, Alt, Ctrl, Delete, Enter and
	 *            Spacebar
	 * @param times
	 *            takes a integer value of number of times the command is to be
	 *            executed
	 * @throws IOException
	 */
	public void PressKeyonElement_Action(String command, int times) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String command1 = command.substring(0, 1).toUpperCase() + command.substring(1).toLowerCase();
		Actions actions = new Actions(b.driver);
		try {
			switch (command1) {
			case "Enter":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.ENTER).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Enter Key is pressed successfully.");
				break;

			case "Down":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.DOWN).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "DownArrow Key is pressed successfully.");
				break;

			case "Up":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.UP).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "UpArrow Key is pressed successfully.");
				break;

			case "Left":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.LEFT).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "LeftArrow Key is pressed successfully.");
				break;

			case "Right":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.RIGHT).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "RightArrow Key is pressed successfully.");
				break;

			case "Backspace":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.BACK_SPACE).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Backspace Key is pressed successfully.");
				break;

			case "Spacebar":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.SPACE).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Spacebar Key is pressed successfully.");
				break;

			case "Tab":
				for (int i = 1; i <= times; i++) {
					actions.sendKeys(b.element, Keys.TAB).build().perform();
				}
				ReportEvents.Done(callerClassName + ":WebUIElement", "Spacebar Key is pressed successfully.");
				break;

			default:
				ReportEvents.Fatal(callerClassName + ":WebUIElement", "Invalid key name.");
			}

		} catch (Exception e) {
			Logger.ERROR(EXCEPTION, e);
			ReportEvents.Error(callerClassName + ":WebUIElement", e);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * The functions Presskey are added to provided backward compatibility to
	 * old scripts. Internally, they call the new function
	 */
	public void PressKey(String keyvalue) throws IOException {
		PressKeysWithCtrl(keyvalue);
	}

	public void PressKey(String command, int times) throws IOException {
		PressKeyOnElement(command, times);
	}

	/**
	 * The function scrolls till the particular web element.
	 * 
	 * @throws IOException
	 */
	public void ScrollTillElement() throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {

			((JavascriptExecutor) b.driver).executeScript("arguments[0].scrollIntoView();", b.element);
			ReportEvents.Done(callerClassName + ":WebUIElement", "Element: " + b.element + "is reached.", b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

	/**
	 * This functions helps to handle the slide bar and moves it according to
	 * the input percentage.
	 * 
	 * @param percentage
	 * @throws IOException
	 */
	public void slide(int percentage) throws IOException {
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {
			Actions moveSlider = new Actions(b.driver);
			int width = b.element.getSize().getWidth();
			Action action = moveSlider.dragAndDropBy(b.element, ((width * percentage) / 100), 0).build();
			action.perform();
			ReportEvents.Done(callerClassName + ":WebUIElement", "Slider is successfully moved to " + percentage + "%");
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e);
		}
	}

	/**
	 * 
	 * @param fileURL
	 * @param saveDir
	 * @throws IOException
	 * @throws Exception
	 */

	public void fileDownload(String attributeName, String saveDir) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();
		String fileURL = "fileurl";
		try {
			fileURL = b.element.getAttribute(attributeName);

		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}

		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();
			if (disposition != null) {

				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				fileName = fileURL.substring(fileURL.lastIndexOf('/') + 1, fileURL.length());
			}
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;
			try (FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {
				int bytesRead = -1;
				byte[] buffer = new byte[4096];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}
			ReportEvents.Done(callerClassName + ":WebUIElement",
					"File downloaded sucessfully. Details: " + "Content-Type = " + contentType
							+ " Content-Disposition = " + disposition + " Content-Length = " + contentLength
							+ " fileName = " + fileName,
					b);
		} else {
			ReportEvents.Fatal(callerClassName + ":WebUIElement", "File not downloaded.", b);
		}
		httpConn.disconnect();
	}

	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void fileUpload(String path) throws IOException {
		String callerClassName = new Exception().getStackTrace()[2].getMethodName();

		try {
			b.element.sendKeys(path);
			ReportEvents.Done(callerClassName + ":WebUIElement", "File uploaded sucessfully.", b);

		}

		catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e, b);
		}

	}

	/**
	 * The function is used to perform differnt operations using JavaScript
	 * executor
	 * 
	 * @return returns String value based don condition
	 * @throws IOException
	 */
	public String javaScriptExecutorOperations(String operationName) throws IOException {
		String text = null;
		JavascriptExecutor js = (JavascriptExecutor) b.driver;
		// js.executeScript(arg0, arg1)
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {

			switch (operationName.toLowerCase()) {

			case "click":
				js.executeScript("arguments[0].click();", b.element);
				break;

			case "highlight":
				js.executeScript("arguments[0].style.border='3px solid red'", b.element);
				break;

			case "checkboxtrue":
				js.executeScript("arguments[0].checked=true;", b.element);
				break;

			case "checkboxfalse":
				js.executeScript("arguments[0].checked=false;", b.element);
				break;

			default:
				ReportEvents.Done(callerClassName + ":WebUIElement",
						"Entered Operation Name " + operationName + " is not correct.", b);
				break;

			}
			ReportEvents.Done(callerClassName + ":WebUIElement", "Element: " + b.element + "is reached.", b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
		return text;

	}

	/**
	 * The function is used for sendkeys method using javascript executor
	 * 
	 * @throws IOException
	 */
	public void sendKeyJavaScriptExecutor(String value) throws IOException {

		JavascriptExecutor js = (JavascriptExecutor) b.driver;
		String callerClassName = new Exception().getStackTrace()[1].getMethodName();
		try {
			js.executeScript("arguments[0].value='" + value + "';", b.element);
			ReportEvents.Done(callerClassName + ":WebUIElement", "Element: " + value + "entered successfully.", b);
		} catch (Exception e) {
			ReportEvents.Error(callerClassName + ":WebUIElement", e);
			// Assert.fail(); removing assert.fail as it is breaking multiple
			// iterations
		}
	}

}
