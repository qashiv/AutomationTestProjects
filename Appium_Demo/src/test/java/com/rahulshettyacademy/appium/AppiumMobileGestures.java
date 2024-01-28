package com.rahulshettyacademy.appium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class AppiumMobileGestures extends BaseClass {

	@Test
	public void longPressGestures() throws InterruptedException {

		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='1. Custom Adapter']")).click();
		WebElement ele = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));
		longPressAction(ele);
		Thread.sleep(2000);
	}

	@Test
	public void scrollGesture() throws InterruptedException {

		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		Thread.sleep(2000);
        scrollToElement("WebView");
		clickByGesture(driver.findElement(AppiumBy.accessibilityId("Visibility")));
		Thread.sleep(2000);
	}

	@Test
	public void swipeGesture() throws InterruptedException {

		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Gallery']")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='1. Photos']")).click();
		WebElement firstImage = driver.findElement(By.xpath("(//android.widget.ImageView)[1]"));
		Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
		swipeAction(firstImage, "left");
		Assert.assertEquals(firstImage.getAttribute("focusable"), "false");
		Thread.sleep(2000);
	}
}
