package com.rahulshettyacademy.appium;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class InstallAppAndroidEmulator extends BaseClass {

	@Test
	public void execution() throws InterruptedException {
		driver.findElement(AppiumBy.accessibilityId("Media")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='MediaPlayer']")).click();
	}
}
