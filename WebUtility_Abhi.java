package walmart.webutility.method;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebUtility {

//	private WebDriver thDriver.get();
	private static WebUtility wu;
//	ExtentTest thExtTest.get();
	private ExtentReports extReport;
	private Workbook workbook;
	private Properties prop;
	private Sheet sheetObj;
	protected static ThreadLocal<WebDriver> thDriver = new ThreadLocal<WebDriver>();
	protected static ThreadLocal<ExtentTest> thExtTest = new ThreadLocal<ExtentTest>();

	Logger log = LogManager.getLogger("WebUtility");

	Random random;

	private WebUtility() {

	}

	public static WebUtility getObject() {
		if (wu == null) {
			wu = new WebUtility();
		}
		return wu;
	}

	public void launchBrowser(String browsername, String systemType) throws Exception {
		if (null != systemType && systemType.equalsIgnoreCase("grid")) {
			// To run within docker based selenium grid
			DesiredCapabilities cap = new DesiredCapabilities();

			if (browsername.equalsIgnoreCase("Firefox")) {
				cap.setBrowserName("firefox");
			} else if (browsername.equalsIgnoreCase("Chrome")) {
				cap.setBrowserName("chrome");
			} else if (browsername.equalsIgnoreCase("Edge")) {
				cap.setBrowserName("MicrosoftEdge");
			}

			thDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap));
		} else {
			if (browsername.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				if (wu.getProp().getProperty("headless").equalsIgnoreCase("on")) {
					options.addArguments("headless");
					options.addArguments("--window-size=1920,1080");
				}
				String downloadFilepath = System.getProperty("user.dir") + "/DownloadedFiles";
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("download.default_directory", downloadFilepath);
				options.addArguments("--remote-allow-origins=*");
				HashMap<String, Object> imagesMap = new HashMap<String, Object>();
				imagesMap.put("images", 2);
				HashMap<String, Object> prefsMap = new HashMap<String, Object>();
				prefsMap.put("profile.default_content_setting_values", imagesMap);
				options.setExperimentalOption("prefs", prefsMap);
				options.setExperimentalOption("prefs", chromePrefs);
				thDriver.set(new ChromeDriver(options));
			} else if (browsername.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				thDriver.set(new FirefoxDriver());
			}
		}

		thDriver.get().manage().window().maximize();
		thDriver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getCharacter(String url, String remove) {
		return (StringUtils.substringBeforeLast(url, remove));
	}

	public String getCharacterAfter(String url, String remove) {
		return (StringUtils.substringAfterLast(url, remove));
	}

	public Boolean verifyingUpda(String updatedtextshop, String updatedtextwal) {
		Boolean bool = verifyingUpdate(updatedtextshop, updatedtextwal);
		return bool;

	}

	public Boolean verifyingUpdate(String updatedtextshop, String updatedtextwal) {
		try {
			Reporter.log("The updated text from shopify is :  " + updatedtextshop);
			Reporter.log("The updated text from walmart is :  " + updatedtextwal);
			Assert.assertEquals(updatedtextwal, updatedtextshop);
			return true;
		} catch (AssertionError e) {
			Reporter.log("The product is not updated. Try again");
			return false;
		}

	}

	public String readJsonKeyValue(String json, String keyName) {
		String valueArr[] = json.split(",");

		String keyValue = "";
		for (int i = 0; i < valueArr.length; i++) {
			if (valueArr[i].contains(keyName)) {
				String p = valueArr[i];
				String priceValue[] = p.split(":");
				keyValue = priceValue[1].replace('"', ' ').replace(".00", " ").trim();
				break;
			}
		}
		return keyValue;
	}

	public String readJsonKeyValue1(String json, String keyName) {
		String valueArr[] = json.split(",");

		String keyValue = "";
		int temp = 1;
		for (int i = 0; i < valueArr.length; i++) {
			if (valueArr[i].contains(keyName)) {
				if (temp == 2) {
					String p = valueArr[i];
					String priceValue[] = p.split(":");
					keyValue = priceValue[1].replace('"', ' ').replace(".00", " ").trim();
					break;
				}
				temp++;
			}
		}
		return keyValue;
	}

	public String readJsonKeyValue2(String json, String keyName) {
		String valueArr[] = json.split(",");

		String keyValue = "";
		int temp = 1;
		for (int i = 0; i < valueArr.length; i++) {
			if (valueArr[i].contains(keyName)) {
				if (temp == 3) {
					String p = valueArr[i];
					String priceValue[] = p.split(":");
					keyValue = priceValue[1].replace('"', ' ').replace(".00", " ").trim();
					break;
				}
				temp++;
			}
		}
		return keyValue;
	}

	public void jsScrollByValue(String value) {
		JavascriptExecutor jse = (JavascriptExecutor) thDriver.get();
		jse.executeScript(value);
	}

	public void jsScrollBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) thDriver.get();
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void jsScrollOnElement(WebElement webEle) {
		JavascriptExecutor jse = (JavascriptExecutor) thDriver.get();
		jse.executeScript("arguments[0].scrollIntoView(true);", webEle);
	}

	public void jsScrollTop() {
		JavascriptExecutor jse = (JavascriptExecutor) thDriver.get();
		jse.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
	}

	public void browserInfo() {
		if (thDriver.get() instanceof ChromeDriver) {
			thExtTest.get().log(Status.INFO, "Chrome Browser is Launched Success");

		} else if (thDriver.get() instanceof FirefoxDriver) {
			thExtTest.get().log(Status.INFO, "fireFox Browser is Launched Success");

		} else if (thDriver.get() instanceof InternetExplorerDriver) {
			thExtTest.get().log(Status.INFO, " Browser is Launched Success");

		}
	}

	public void switchToIframe(WebElement webEle, String msg) {
		thDriver.get().switchTo().frame(webEle);
		thExtTest.get().log(Status.INFO, msg);
	}

	public void outIFrame(String msg) {
		thDriver.get().switchTo().defaultContent();
		thExtTest.get().log(Status.INFO, msg);
	}

	public void switchto(WebElement webEle) {
		thDriver.get().switchTo().alert().getText();
	}

	public void printExtentTestMsg(String msg) {
		thExtTest.get().log(Status.PASS, msg);
	}

	public void printExtentTestFailedMsg(String msg) {
		thExtTest.get().log(Status.FAIL, msg);
	}

	public void printPopupTextMsg(String msg) {
		thExtTest.get().log(Status.PASS, msg);
	}

	public void closeBrowser() {
		thDriver.get().close();
	}

	public void quiteBrowser() {
		thDriver.get().quit();
	}

	public void flushReport() {
		extReport.flush();
	}

	public void openUrl(String url) {
		try {
			thDriver.get().get(url);
		} catch (Exception e) {
			thExtTest.get().log(Status.FAIL, "url does't open successfully");

		}
	}

	public WebDriver getDriver() {
		return thDriver.get();
	}

	public String popuphandltest() {
		String text = thDriver.get().switchTo().alert().getText();
		return text;
	}

	public void popuphandl() {
		thDriver.get().switchTo().alert().accept();
	}

	public void selectByVisibleText(WebElement webEle, String selectText, String msg) {

		try {
			Select selobj = new Select(webEle);
			selobj.selectByVisibleText(selectText);
		} catch (StaleElementReferenceException e) {

			Select selobj = new Select(webEle);
			selobj.selectByVisibleText(selectText);

		} catch (ElementNotInteractableException e) {

		}
		thExtTest.get().log(Status.INFO, msg);
		log.info(msg);
	}

	public void verifySelectedDropDown(WebElement webEle) {
		Select select = new Select(webEle);
		WebElement option = select.getFirstSelectedOption();
		String SelectedText = option.getText();
		thExtTest.get().log(Status.PASS, "Selected text is -" + SelectedText);
	}

	public void verifyDropDownElement(WebElement webEle, List<WebElement> optionList) {

		Select select = new Select(webEle);
		for (int i = 0; i < optionList.size(); i++) {
			select.selectByIndex(i);
			String SelectedText = optionList.get(i).getText();
			thExtTest.get().log(Status.PASS, "DropDown Element is -" + SelectedText);
		}
	}

	public void verifygetTitle(String exptitlevalue, String msg, String snapshotname) {
		String ActualTitleText = thDriver.get().getTitle();
		if (ActualTitleText.equalsIgnoreCase(exptitlevalue)) {
			thExtTest.get().log(Status.PASS,
					" Where Actual Text is :- " + ActualTitleText + " & Expected is :- " + exptitlevalue);
		} else {
			thExtTest.get().log(Status.FAIL,
					" Where Actual Text is :- " + ActualTitleText + " & Expected is :- " + exptitlevalue);
			String img = wu.takeSnapShot(snapshotname);
			wu.snapShotCaptureReportattach(img);
		}
	}

	public void selectByValue(WebElement webEle, String selectText, String msg) {

		try {
			Select selobj = new Select(webEle);
			selobj.selectByValue(selectText);
		} catch (StaleElementReferenceException e) {

			Select selobj = new Select(webEle);
			selobj.selectByValue(selectText);

		} catch (ElementNotInteractableException e) {

		}
		thExtTest.get().log(Status.INFO, msg);
	}

	public void selectByIndex(WebElement webEle, int index, String msg) {

		try {
			Select selobj = new Select(webEle);
			selobj.selectByIndex(index);
		} catch (StaleElementReferenceException e) {

			Select selobj = new Select(webEle);
			selobj.selectByIndex(index);

		} catch (ElementNotInteractableException e) {

		}
		thExtTest.get().log(Status.INFO, msg);
	}

	public void openLoginPage(WebUtility webUtil, String browserName, String systemType, String Url) throws Exception {
		webUtil.launchBrowser(browserName, systemType);
		webUtil.openUrl(Url);
	}

	

	public void jsScroll(String value) {
		JavascriptExecutor jse = (JavascriptExecutor) thDriver.get();
		jse.executeScript(value);
	}

	public void inpuData(WebElement webEle, String inputvalue, String msg) {

		try {
			webEle.clear();
			webEle.sendKeys(inputvalue);
		} catch (StaleElementReferenceException e) {
			webEle.clear();
			webEle.sendKeys(inputvalue);
		} catch (ElementNotInteractableException e) {

		}
		thExtTest.get().log(Status.INFO, msg);
		log.info(msg);
	}

	public String dateInMM_DD_YY(int days) {
		LocalDate myObj = LocalDate.now();
		String str = myObj.toString();
		String tomorrow = LocalDate.parse(str).plusDays(days).toString();
		String arr[] = tomorrow.split("-");
		String year = arr[0];
		String month = arr[1];
		String day = arr[2];
		String date = month + "/" + day + "/" + year;
		return date;
	}

	public void robotMethod() {
		try {
			Robot rm = new Robot();
			rm.delay(5000);
			rm.keyPress(KeyEvent.VK_ENTER);
			rm.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void click(WebElement webEle, String msg) {

		try {
			webEle.click();
		} catch (ElementClickInterceptedException e) {
			WebDriverWait wait = new WebDriverWait(thDriver.get(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(webEle));
			wu.click(webEle, msg);
		} catch (StaleElementReferenceException e) {
			WebDriverWait wait = new WebDriverWait(thDriver.get(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(webEle));
			webEle.click();
		}
		thExtTest.get().log(Status.INFO, msg);
		log.info(msg);
	}

	public void actionMouseOver(WebElement element, String msg) {

		Actions act = new Actions(thDriver.get());
		act.moveToElement(element).build().perform();
		thExtTest.get().log(Status.INFO, msg);
	}

	public void actionDragandDrop(WebElement Drag, WebElement Drop, String msg) {
		Actions act = new Actions(thDriver.get());
		try {
			act.dragAndDrop(Drag, Drop).build().perform();
			thExtTest.get().log(Status.INFO, msg);
			log.info(msg);
		} catch (NoSuchElementException e) {
			holdOn(5);
			act.dragAndDrop(Drag, Drop).build().perform();
			thExtTest.get().log(Status.INFO, msg);
			log.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionDragAndDrop(WebElement element, WebElement wele, String msg) {
		Actions act = new Actions(thDriver.get());
		act.dragAndDrop(element, wele).build().perform();
		thExtTest.get().log(Status.INFO, msg);
	}

	public void dragAndDrop(WebElement from, WebElement to, String msg) {
		Actions actObj = new Actions(thDriver.get());
		try {
			actObj.clickAndHold(from).moveToElement(from).release(to).build().perform();
			thExtTest.get().pass(msg);
			log.info(msg);
		} catch (NoSuchElementException e) {
			holdOn(4);
			actObj.clickAndHold(from).moveToElement(from).release(to).build().perform();
			thExtTest.get().pass(msg);
			log.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void actionSendkeys(WebElement element, String inputvalue, String msg) {

		new Actions(thDriver.get()).moveToElement(element).build().perform();
		holdOn(5);

		WebDriverWait wait = new WebDriverWait(thDriver.get(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		holdOn(5);
		new Actions(thDriver.get()).sendKeys(element).build().perform();
	}

	public void actionClick(WebElement element, String msg) {

		new Actions(thDriver.get()).moveToElement(element).build().perform();
		holdOn(3);

		WebDriverWait wait = new WebDriverWait(thDriver.get(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(element));

		holdOn(5);
		new Actions(thDriver.get()).moveToElement(element).click().build().perform();
		thExtTest.get().log(Status.INFO, msg);
	}

	public void jsClick(WebElement element, String msg) {
		JavascriptExecutor executor = (JavascriptExecutor) thDriver.get();
		executor.executeScript("arguments[0].click();", element);
		thExtTest.get().log(Status.INFO, msg);
	}
	public void refresh() {
		thDriver.get().navigate().refresh();
	}
	public void back() {
		thDriver.get().navigate().back();
	}


	public void verifyTextContains(WebElement weEle, String expectedText, String snapShotName) {

		String actualText = weEle.getText();
		if (actualText.contains(expectedText)) {
			thExtTest.get().log(Status.PASS, "Text Verification is Passed where Actual is :- " + actualText
					+ " & Expected is :- " + expectedText);
		} else
			thExtTest.get().log(Status.PASS, "Text Verification is Failed where Actual is :- " + actualText
					+ " & Expected is :- " + expectedText);

	}

	public void verifyEnabled(WebElement weEle, String snapShotName) {

		boolean status = weEle.isEnabled();
		if (status) {
			thExtTest.get().log(Status.PASS, "Element is Enabled");
		} else {
			thExtTest.get().log(Status.FAIL, "Element is Disabled");

		}
	}

	public void verifyDisabled(WebElement weEle, String snapShotName) {

		boolean status = weEle.isEnabled();
		if (!status) {
			thExtTest.get().log(Status.PASS, "Element is Disabled");
		} else {
			thExtTest.get().log(Status.FAIL, "Element is Enabled");
		}
	}

	public void verifyAttributeValue(WebElement weEle, String attributeName, String expectedAttributeValue) {

		String actualAttributeValue = weEle.getAttribute(attributeName);
		if (actualAttributeValue.equalsIgnoreCase(expectedAttributeValue)) {
			thExtTest.get().log(Status.PASS, " Where Actual Attribute Value is :- " + actualAttributeValue
					+ " & Expected Attribute Value is :- " + expectedAttributeValue);
		} else {
			thExtTest.get().log(Status.FAIL, " Where Actual Attribute Value is :- " + actualAttributeValue
					+ " & Expected Attribute Value is :- " + expectedAttributeValue);

		}
	}
	
	public void verifyCheckBox(WebElement weEle, String msg) {
		try {
			Boolean status = weEle.isSelected();
			thExtTest.get().log(Status.PASS, status + " :- " + msg);
			log.info(status + " " + ":-" + " " + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyAttributeContains(WebElement weEle, String attributeName, String expectedAttributeValue) {

		String actualAttributeValue = weEle.getAttribute(attributeName);
		if (actualAttributeValue.contains(expectedAttributeValue)) {
			thExtTest.get().log(Status.PASS, " Where Actual Attribute Value is :- " + actualAttributeValue
					+ " & Expected Attribute Value is :- " + expectedAttributeValue);
		} else {
			thExtTest.get().log(Status.FAIL, " Where Actual Attribute Value is :- " + actualAttributeValue
					+ " & Expected Attribute Value is :- " + expectedAttributeValue);

		}
	}

	public void verifyElementVisible(WebElement weEle, String mgs) {
		try {
			boolean weStatus = weEle.isDisplayed();
			Dimension dim = weEle.getSize();
			int height = dim.getHeight();
			int width = dim.getWidth();
			if (weStatus && height > 0 && width > 0) {
				thExtTest.get().log(Status.PASS, weEle.getText() + " " + mgs);
			} else
				thExtTest.get().log(Status.FAIL, mgs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public String getAttribute(WebElement weEle, String value) {
		String text = null;
		try {
			text = weEle.getAttribute(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public String getText(WebElement weEle) {
		String text = null;
		try {
			text = weEle.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public void verifyElementInvisible(WebElement weEle, String mgs) {

		boolean weStatus = weEle.isDisplayed();
		Dimension dim = weEle.getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		if (weStatus && height > 1 && width > 1) {
			thExtTest.get().log(Status.PASS, mgs);
		} else
			thExtTest.get().log(Status.FAIL, mgs);

	}

	public void robotMethod(String path) {
		try {
			StringSelection ss = new StringSelection(System.getProperty("user.dir") + path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			Robot robot = new Robot();
			robot.delay(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(90);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void verifyElementIsClickablebyXpath(String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(thDriver.get(), 30);
			WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			thExtTest.get().log(Status.PASS, "Element is clickable");
		} catch (TimeoutException e) {
			thExtTest.get().log(Status.FAIL, "Element is not clickable");
		}
	}

	public void verifyElementIsClickable(WebElement webEle, String msg) {
		try {
			WebDriverWait wait = new WebDriverWait(thDriver.get(), 5);
			wait.until(ExpectedConditions.elementToBeClickable(webEle));
			thExtTest.get().log(Status.PASS, msg);
		} catch (TimeoutException e) {
			thExtTest.get().log(Status.PASS, "Element is not clickable");
		}
	}

	public void pressEnter(String msg) {
		Actions action = new Actions(thDriver.get());
		action.keyDown(Keys.ENTER).build().perform();
		action.keyUp(Keys.ENTER).build().perform();
		thExtTest.get().log(Status.INFO, msg);
	}

	public void verifyElementsVisible(List<WebElement> ListWeEle, String msg) {
		try {
			for (WebElement weEle : ListWeEle) {
				boolean weStatus = weEle.isDisplayed();
				Dimension dim = weEle.getSize();
				int height = dim.getHeight();
				int width = dim.getWidth();
				if (weStatus && height > 0 && width > 0) {
					thExtTest.get().log(Status.PASS, weEle.getText() + " " + msg);
					log.info(msg);
				} else
					thExtTest.get().log(Status.FAIL, weEle.getText() + " " + msg);
				log.info(msg);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String takeSnapShot(String snapshotname) {

		TakesScreenshot tss = (TakesScreenshot) thDriver.get();
		File source = tss.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File("ExtReport/" + snapshotname + ".jpeg");
		try {
			Files.copy(source, destinationFile);
		} catch (IOException e) {

		}
		return destinationFile.getAbsolutePath();
	}

	public void verifyInnerText(WebElement weEle, String expectedText, String snapshotname) {

		try {
			String actualText = weEle.getText();
			if (actualText.equalsIgnoreCase(expectedText)) {
				thExtTest.get().log(Status.PASS,
						" Where Actual Text is :- " + actualText + " & Expected is :- " + expectedText);
			} else {
				thExtTest.get().log(Status.FAIL,
						" Where Actual Text is :- " + actualText + " & Expected is :- " + expectedText);
				String img = wu.takeSnapShot(snapshotname);
				wu.snapShotCaptureReportattach(img);
			}
		} catch (NoSuchElementException e) {
			thExtTest.get().log(Status.FAIL, e);
		}
	}

	public void getElementText(WebElement weEle, String expectedText) {
		String actualText = weEle.getText();
		if (actualText.contains(expectedText)) {
			thExtTest.get().log(Status.PASS,
					"Where Actual Text is :- " + actualText + " & Expected is:- " + expectedText);
		} else {
			thExtTest.get().log(Status.FAIL, "Text Verification is failed where Actual Text is :- " + actualText
					+ " and Expected Text is:- " + expectedText);
		}
	}
	public void verifyPopText() {
	Alert alert = thDriver.get().switchTo().alert();

	String alertText = alert.getText();
	if (alertText.equals("Please select atleast one option")) {
		thExtTest.get().log(Status.PASS,
				alertText+":- Alert message is correct.");
	} else {
		thExtTest.get().log(Status.PASS,
				alertText+":- Alert message is incorrect.");
	}
	alert.accept();
	}
	public void acceptPopUp() {
		thDriver.get().switchTo().alert().accept();
	}

	public void dismissPopUp() {
		thDriver.get().switchTo().alert().dismiss();
	}

	public void closeBrowser(String msg) {
		thDriver.get().close();
		thExtTest.get().log(Status.PASS, msg);

	}

	public void holdOn(int seconds) {
		seconds *= 1000;
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void getWindoHandleByTitle(String ExpTitlevalue, String msg) {
		Set<String> AllWindowValue = thDriver.get().getWindowHandles();
		for (String AllValue : AllWindowValue) {
			thDriver.get().switchTo().window(AllValue);
			String getTitleValue = thDriver.get().getTitle();
			if (getTitleValue.contains(ExpTitlevalue)) {
				break;
			}

		}
		thExtTest.get().log(Status.INFO, msg);
	}

	public void getWindoHandleByUrl(String ExpURLvalue, String msg) {
		Set<String> AllWindowValue = thDriver.get().getWindowHandles();
		for (String AllValue : AllWindowValue) {
			thDriver.get().switchTo().window(AllValue);
			String getTitleValue = thDriver.get().getCurrentUrl();
			if (getTitleValue.contains(ExpURLvalue)) {
				break;
			}

		}
		thExtTest.get().log(Status.INFO, msg);
	}

	public String getRandomNameWithSpecialChar(int count) {
		random = new Random();
		char[] choices = ("abc-#$%" + "abc-#$%ri%^fd" + "0-#$%" + "%^" + "0123456789").toCharArray();
		StringBuilder salt = new StringBuilder(count);
		for (int i = 0; i < count; ++i)
			salt.append(choices[random.nextInt(choices.length)]);
		return salt.toString();
	}

	public String getrandomNameInt(int count) {
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder(count);
		random = new Random();
		for (int i = 0; i < count; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public String getrandomAlphanumericName(int count) {
		String s = RandomStringUtils.randomAlphanumeric(count);
		return s;

	}

	public String getrandomAlphabeticName(int count) {
		String s = RandomStringUtils.randomAlphabetic(count);
		return s;

	}

	public String getRandomName(int count) {
		String name = "";
		for (int i = 1; i <= count; i++) {
			int rnd = (int) (Math.random() * 52);
			Character base = (rnd < 26) ? 'A' : 'a';
			name = name + base.toString() + rnd % 26;
		}
		return name;

	}

	public String timeStamp() {
		SimpleDateFormat sft = new SimpleDateFormat("dd-MM-yyyy hh_mm_ss");
		String Time = sft.format(new Date());
		return Time;
	}

	public String Snapshot(String snapshortname) {
		TakesScreenshot scrtsho = (TakesScreenshot) thDriver.get();
		File soursefile = scrtsho.getScreenshotAs(OutputType.FILE);
		String Time = timeStamp();
		File distinationFile = new File("ExtReport//snap" + snapshortname + Time + ".jpeg");
		try {
			Files.copy(soursefile, distinationFile);
		} catch (IOException e) {

		}
		return distinationFile.getAbsolutePath();
	}

	public void snapShotCaptureReportattach(String imgPath) {
		try {
			thExtTest.get().addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void OnHoldTime(long miles) {
		try {
			Thread.sleep(miles * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public void htmlReport(String reportResultName) {
		ExtentSparkReporter ExtHtmlRport = new ExtentSparkReporter(reportResultName);
		ExtHtmlRport.config().setReportName(" Walmart Onborading Function Reports Automation");
		ExtHtmlRport.config().setDocumentTitle("walmart Reports");
		extReport = new ExtentReports();
		extReport.attachReporter(ExtHtmlRport);
		extReport.setSystemInfo("Abhishek", System.getProperty("user.name"));
		extReport.setSystemInfo("OS", System.getProperty("user.os"));
		extReport.setSystemInfo("Envoirment", "QA");

	}

	public void setExtentLogger(String testCaseName, String clName) {
		thExtTest.set(extReport.createTest(testCaseName));
		thExtTest.get().assignCategory(clName);
	}

	public ExtentTest getLogger() {
		return thExtTest.get();
	}

	public Properties loaderConfigFile() {
		prop = new Properties();
		try (FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/PropertiesFile/config.properties")) {
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public Properties getProp() {
		return prop;
	}

	public List<HashMap<String, String>> getJsonDataToHashmap(String jsonFileName) throws IOException {
//	reading json to string
		String jsonContent = FileUtils.readFileToString(
				new File(System.getProperty("user.dir") + "/src/test/resources/" + jsonFileName + ".json"),
				StandardCharsets.UTF_8);

//	String to Hashmap

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String extractWordAfterACharacter(String str, String character, int position) {
		String arr[] = str.split(character);
		return arr[position];
	}
	public void explicitWaitForElementVisibilty(WebElement weEle, long timeOutInSecond) {
		WebDriverWait explicitWait = new WebDriverWait(thDriver.get(), timeOutInSecond);
		explicitWait.until(ExpectedConditions.visibilityOf(weEle));
	}

	public void explicitWaitForElementInvisibilty(WebElement weEle, long timeOutInSecond) {
		WebDriverWait explicitWait = new WebDriverWait(thDriver.get(), timeOutInSecond);
		explicitWait.until(ExpectedConditions.invisibilityOf(weEle));
	}

	public void explicitWaitForElementToBeClickable(WebElement weEle, long timeOutInSecond) {
		WebDriverWait explicitWait = new WebDriverWait(thDriver.get(), timeOutInSecond);
		explicitWait.until(ExpectedConditions.elementToBeClickable(weEle));
	}

	public void explicitWaitTextToBePresentInElement(WebElement weEle, long timeOutInSecond, String expectedText) {
		WebDriverWait explicitWait = new WebDriverWait(thDriver.get(), timeOutInSecond);
		explicitWait.until(ExpectedConditions.textToBePresentInElement(weEle, expectedText));
	}

}
