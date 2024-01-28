package rahulshettyacademy;

import java.io.File;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class MobileDialerTest {

	@Test
	public void mobileDialer() throws Exception {
		
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Demo");
		options.setCapability("appPackage", "com.android.contacts");
		options.setCapability("appActivity", "com.android.contacts.DialtactsActivityAlias");
		options.setCapability("noReset",true);
		URL url = new URL("http://127.0.0.1:4723/");
		AndroidDriver driver = new AndroidDriver(url,options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		try {
		driver.findElement(By.id("com.android.contacts:id/six")).click();
		driver.findElement(By.id("com.android.contacts:id/three")).click();
		driver.findElement(By.id("com.android.contacts:id/nine")).click();
		driver.findElement(By.id("com.android.contacts:id/two")).click();
		driver.findElement(By.id("com.android.contacts:id/four")).click();
		driver.findElement(By.id("com.android.contacts:id/nine")).click();
		driver.findElement(By.id("com.android.contacts:id/eight")).click();
		driver.findElement(By.id("com.android.contacts:id/two")).click();
		driver.findElement(By.id("com.android.contacts:id/nine")).click();
		driver.findElement(By.id("com.android.contacts:id/seven")).click();
		
		driver.findElement(AppiumBy.accessibilityId("Call with 'Airtel'")).click();
		}catch(Exception e) {
			e.printStackTrace();
		}
//		driver.findElement(By.id("com.android.dialer:id/call_log_tab")).click();
//		driver.findElement(By.id("com.android.dialer:id/contacts_tab")).click();
//		driver.findElement(By.id("com.android.dialer:id/voicemail_tab")).click();
//		driver.findElement(By.id("com.android.dialer:id/speed_dial_tab")).click();
//		driver.findElement(AppiumBy.accessibilityId("More options")).click();
//		Thread.sleep(2000);
//		driver.findElement(By.id("android:id/title")).click();
////		WebElement dialCall =  driver.findElement(By.xpath("//android.widget.LinearLayout[@content-desc='Call to (737) 906-9734, Texas, 4 hr. ago, .']/android.widget.LinearLayout"));
////		System.out.println(dialCall.getText());
//		driver.findElement(By.xpath("//android.widget.TextView[@text='MISSED']")).click();
//		String msg = driver.findElement(By.id("com.android.dialer:id/empty_list_view_message")).getText();
//		System.out.println(msg);
//		driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
		File sourceFile =  ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File("D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\Appium_Demo\\Screenshot\\InfoShot.png");
		Files.copy(sourceFile, destFile);
		Thread.sleep(3000);
		driver.quit();
	}
}
