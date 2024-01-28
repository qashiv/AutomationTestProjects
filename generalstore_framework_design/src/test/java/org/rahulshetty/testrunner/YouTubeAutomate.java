package org.rahulshetty.testrunner;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class YouTubeAutomate {

	@Test
	public void ytReelAutomate() throws Exception {

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Demo");
		options.setCapability("appPackage", "com.google.android.youtube");
		options.setCapability("appActivity", "com.google.android.apps.youtube.app.watchwhile.WatchWhileActivity");
		options.setCapability("noReset", true);

		URL url = new URL("http://127.0.0.1:4723/");
		AndroidDriver driver = new AndroidDriver(url, options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Mix - #VIDEO | #Neelkamal Singh | झेलत बानी तोहरे अंश  | New Superhit Bhojpuri Song 2022 - Neelkamal Singh, Pawan Singh, Priyanshu Singh, and more - 50+ videos\"]/android.view.ViewGroup[1]/android.view.View[1]")).click();
		Thread.sleep(5000);

	}
}
