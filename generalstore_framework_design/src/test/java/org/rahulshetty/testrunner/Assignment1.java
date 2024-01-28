package org.rahulshetty.testrunner;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Assignment1 {

	@Test
	public void apiDemos() throws Exception {

		// Open ApiDemos App > App > Alert Dialogue > try all manadatory fields

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Demo");
		options.setApp(
				"D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\Appium_Demo\\Resources\\ApiDemos-debug.apk");

		URL url = new URL("http://127.0.0.1:4723/");
		AndroidDriver driver = new AndroidDriver(url, options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(AppiumBy.accessibilityId("App")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Alert Dialogs']")).click();
		driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with a message")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(AppiumBy.className("android.widget.TextView")).getText());
		driver.findElement(By.id("android:id/button2")).click();
		driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with a long message")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.id("android:id/message")).getText());
		driver.findElement(By.id("android:id/button3")).click();
		driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with ultra long message")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.id("android:id/message")).getText());
		driver.findElement(By.id("android:id/button1")).click();
		driver.findElement(AppiumBy.accessibilityId("List dialog")).click();
		System.out.println(driver.findElement(By.id("android:id/alertTitle")).getText());
		List<WebElement> alertsTitle = driver.findElements(By.xpath("//android.widget.TextView"));
		for (WebElement ele : alertsTitle) {
			String title = ele.getText();
			System.out.println(title);
			if (title.equalsIgnoreCase("Command four")) {
				ele.click();
			}
		}

		
		String message = driver.findElement(By.id("android:id/message")).getText();
		Assert.assertEquals(message, "You selected: 3 , Command four");
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.findElement(AppiumBy.accessibilityId("Progress dialog")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Single choice list")));
		driver.findElement(AppiumBy.accessibilityId("Single choice list")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//android.widget.CheckedTextView"));
		for (WebElement ele : elements) {
			ele.click();
			String text = ele.getText();
			if (text.equalsIgnoreCase("Street view")) {
				break;
			}
		}
		driver.findElement(By.id("android:id/button1")).click();
		Thread.sleep(3000);
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		driver.quit();

	}
}
