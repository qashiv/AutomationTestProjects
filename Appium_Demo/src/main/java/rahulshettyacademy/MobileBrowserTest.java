package rahulshettyacademy;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class MobileBrowserTest {

	@Test
	public void mobileBrowserTesting() throws MalformedURLException, InterruptedException {
	
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("Demo");
		options.setChromedriverExecutable("C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		options.setCapability("browserName", "Chrome");
		
		URL url = new URL("http://127.0.0.1:4723/");
		AndroidDriver driver = new AndroidDriver(url,options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("span.navbar-toggler-icon")).click();
		driver.findElement(By.cssSelector("a[routerlink='/products'][class='nav-link']")).click();
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,1500)");
		driver.findElement(By.xpath("//a[text()='Devops']")).click();
		System.out.println(driver.getTitle());
		Thread.sleep(3000);
		driver.quit();
	}
}
