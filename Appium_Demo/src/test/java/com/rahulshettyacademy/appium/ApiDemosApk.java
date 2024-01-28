package com.rahulshettyacademy.appium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class ApiDemosApk extends BaseClass {

	@Test
	public void wiFiTesting() {
		// Android Device
		// Appium Code ---> Appium Server ---> mobile
		/**
		 * here we write appium code then execute it ... when execute it , it goes on
		 * appium server then server responds to the actual Android device for working
		 * according to command
		 * 
		 * And to automate mobile apps -- It is must to provide some basic information
		 * in your code like - Device name , Device version , Which Apps you want to
		 * execute
		 * 
		 * Actual Automation is start after providing above details (Above details are available in BaseClass)
		 */

        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='3. Preference dependencies']")).click();
		driver.findElement(By.id("android:id/checkbox")).click();
		driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
		String alertText =  driver.findElement(By.id("android:id/alertTitle")).getText();
		Assert.assertEquals(alertText, "WiFi settings");
		driver.findElement(By.id("android:id/edit")).sendKeys("Oppo Realme");
		driver.findElement(By.id("android:id/button1")).click();

	}
	
	
}
