package org.sbtech.apidemosapk.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class AndroidUtil {

	private static AndroidUtil util;
	private AppiumDriverLocalService service;
	private AndroidDriver driver;
	private ExtentTest extTest;

	private AndroidUtil() {

	}

	public static AndroidUtil getObject() {
		if (util == null) {
			util = new AndroidUtil();
		}
		return util;
	}

	public AndroidDriver getDriver() {
		return driver;
	}

	public void setExtTestObject(ExtentTest extTest) {
		this.extTest = extTest;
	}

	public ExtentTest getExtTestObject() {
		return extTest;
	}

	public void startAppiumServer() {
		File npmFile = new File("C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");
		service = new AppiumServiceBuilder().withAppiumJS(npmFile).withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();
		printInfoExtTestMsg("Appium server started...");

	}

	public void closeAppiumServer() {
		service.close();
		printInfoExtTestMsg("Appium Server closed.");
	}

	public void launchApk(String deviceName) {

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(deviceName);
		String appPath = "D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\apidemosapk_automation_framework\\src\\test\\resources\\ApiDemos-debug.apk";
		options.setApp(appPath);
		options.setChromedriverExecutable(
				"D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\apidemosapk_automation_framework\\Driver\\chromedriver.exe");

		URL url = null;
		try {
			url = new URL("http://127.0.0.1:4723/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		driver = new AndroidDriver(url, options);
		printPassExtTestMsg("apk launched successfully");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public void printPassExtTestMsg(String message) {
		try {
			extTest.log(Status.PASS, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printFailExtTestMsg(String message) {
		try {
			extTest.log(Status.FAIL, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printInfoExtTestMsg(String message) {
		try {
			extTest.log(Status.INFO, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss a");
		String dateTime = sdf.format(new Date());
		return dateTime;
	}

	public void click(WebElement ele, String elementName) {
		try {
			ele.click();
			printInfoExtTestMsg("clicked on "+elementName+" successfully");
		} catch (Exception e) {
			printFailExtTestMsg("unable to click on "+elementName);
			e.printStackTrace();
		}
	}

	public void clickGesture(WebElement ele, String elementName) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("mobile:clickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId()));
			printInfoExtTestMsg("gesture click perform on "+elementName+" successfully");
		} catch (Exception ex) {
			printFailExtTestMsg("unable to click on "+elementName);
			ex.printStackTrace();
		}
	}

	public String takeSnapShot(String testCaseName) {
		String dateTime = getTimeStamp();
		TakesScreenshot tss = (TakesScreenshot) driver;
		File sourceFile = tss.getScreenshotAs(OutputType.FILE);
		File destFile = new File("ExtentReport\\" + testCaseName + dateTime + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return destFile.getAbsolutePath();
	}

	public void holdOn(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tearDown() {
		driver.quit();
	}
}
