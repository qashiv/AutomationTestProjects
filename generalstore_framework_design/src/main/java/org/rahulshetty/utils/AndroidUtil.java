package org.rahulshetty.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AndroidUtil {

	private AndroidDriver driver;
	private WebDriverWait wait;
	private AppiumDriverLocalService service;
	private static AndroidUtil util;

	public AndroidDriver getDriver() {
		return driver;
	}

	public static AndroidUtil getObject() {
		if (util == null) {
			util = new AndroidUtil();
		}
		return util;
	}

	public void startAppiumServer() {
		File npmMainFile = new File(
				"C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");

		service = new AppiumServiceBuilder().withAppiumJS(npmMainFile).withIPAddress("127.0.0.1").usingPort(4723)
				.build();

		service.start();

	}

	public void closeAppiumServer() {
		service.close();
	}

	public void configuration(String deviceName) {

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(deviceName);
		String appPath = "D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\Appium_Demo\\Resources\\General-Store.apk";
		options.setApp(appPath);
		options.setChromedriverExecutable(
				"D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\appium_framework_design\\Driver\\chromedriver.exe");
		options.setCapability("browserName", "Chrome");
		URL url = null;
		try {
			url = new URL("http://127.0.0.1:4723/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		driver = new AndroidDriver(url, options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	public void pressKey(AndroidKey key) {
		driver.pressKey(new KeyEvent(key));
	}

	public void quit() {
		driver.quit();
	}

	public void staticWait(int wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getText(WebElement ele) {

		return ele.getText();
	}

	public List<String> getMultiElementText(List<WebElement> elements) {
		List<String> elementsList = new ArrayList<>();
		for (WebElement element : elements) {
			String eleTxt = element.getText();
			elementsList.add(eleTxt);
		}
		return elementsList;
	}

	public void selectByIndexFromMultiElements(List<WebElement> elements, int index) {
		for (int i = 0; i < elements.size(); i++) {
			if (i == index)
				elements.get(i).click();
		}
	}

	public String getAttribute(WebElement ele, String value) {
		return ele.getAttribute(value);
	}

	public void click(WebElement ele) {
		try {
			ele.click();
		} catch (Exception e) {
			Actions act = new Actions(driver);
			act.click(ele).build().perform();
			e.printStackTrace();
		}
	}

	public void sendKeys(WebElement ele, String keys) {
		ele.sendKeys(keys);
	}

	public void scrollToText(String text) {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"))"));
	}

	public void scrollAndClickElement(String text) {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"))"));
		click(driver.findElement(By.xpath("//android.widget.TextView[@text='" + text + "']")));
	}

	public void waitUntilTextToBePresent(WebElement ele, String textToBePresent) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.textToBePresentInElement(ele, textToBePresent));
	}

	public void waitUntilElementVisible(WebElement ele) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void longClickGesture(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));
	}

	public void moveToBrowser() {
		staticWait(2000);
		Set<String> contexts = driver.getContextHandles();
		for (String contextName : contexts) {
			if (contextName.contains("WEB")) {
				staticWait(2000);
				driver.context(contextName);
				break;
			}
		}
	}

	public String getTitleOfWeb() {
		return driver.getTitle();
	}

	public void takesSnapShot() {

		SimpleDateFormat sdf = new SimpleDateFormat("_dd_MMM_yyyy_hh_mm_ss");
		String dateTimeFormat = sdf.format(new Date());
		TakesScreenshot tss = (TakesScreenshot) driver;
		File srcFile = tss.getScreenshotAs(OutputType.FILE);
		String strFile = "D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\generalstore_framework_design\\Screenshot"
				+ dateTimeFormat + ".png";
		try {
			Files.copy(srcFile, new File(strFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<HashMap<String, String>> getJsonData(String filePath) {
		String jsonData = null;
		try {
			jsonData = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = null;
		try {
			data = mapper.readValue(jsonData, new TypeReference<List<HashMap<String, String>>>() {
			});
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return data;
	}

}
