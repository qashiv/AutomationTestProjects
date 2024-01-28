package com.rahulshettyacademy.appium;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseClass {

	public AndroidDriver driver;

	@BeforeMethod
	public void configuration() throws MalformedURLException {
//		AppiumDriverLocalService service = new AppiumServiceBuilder()
//		.withAppiumJS(
//				new File("C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
//		.withIPAddress("127.0.0.1").usingPort(4723).build();
//
//service.start();
//		DesiredCapabilities cap = new DesiredCapabilities();
//		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
//		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
//		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "88af4262");

// When you find exception regarding to file location then use below 3 lines of
// code
//		File file = new File("Resources");
//		File appPath = new File(file, "ApiDemos-debug.apk") ;
//options.setApp(appPath.getAbsolutePath());

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("U8KVDMBYLFT4SK85");

		options.setApp(
				"D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\Appium_Demo\\Resources\\ApiDemos-debug.apk");
		URL url = new URL("http://127.0.0.1:4723/");

		driver = new AndroidDriver(url, options);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	public void longPressAction(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("mobile:longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));
	}

	public void scrollActionNoPriorIdea() {
		// No prior idea , where to scroll
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 3.0));
			System.out.println(canScrollMore + " Status of canScrollMore");

		} while (canScrollMore);
	}

	public void clickByGesture(WebElement element) {

		((JavascriptExecutor) driver).executeScript("mobile: clickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
	}

	public void scrollToElement(String elementTxt) {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + elementTxt + "\"))"));
	}

	public void swipeAction(WebElement ele, String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("elementId",
				((RemoteWebElement) ele).getId(), "direction", direction, "percent", 0.75));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
