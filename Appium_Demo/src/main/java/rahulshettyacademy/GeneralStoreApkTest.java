package rahulshettyacademy;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;

public class GeneralStoreApkTest {

	private AndroidDriver driver;

	@BeforeMethod
	public void setUp() throws MalformedURLException {
		UiAutomator2Options options = new UiAutomator2Options();
		options.setChromedriverExecutable("C:\\Users\\Dell\\Downloads\\chromedriver_win32\\chromedriver.exe");
		options.setDeviceName("U8KVDMBYLFT4SK85");
		options.setApp(
				"D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\Appium_Demo\\Resources\\General-Store.apk");

		URL url = new URL("http://127.0.0.1:4723/");
		driver = new AndroidDriver(url, options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		driver.quit();
	}

	public void fillForm() throws Exception {

		WebElement header = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
		String headerText = header.getText();
		System.out.println(headerText);
		Assert.assertEquals(headerText, "General Store");
		driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Aruba\"))"));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//android.widget.TextView[@text='Aruba']")).click();
		driver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys("Mata-G");
		driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
		driver.findElement(AppiumBy.className("android.widget.Button")).click();

	}

//	@Test
//	public void errorValidation() {
//
//		driver.findElement(AppiumBy.className("android.widget.Button")).click();
//		String toastMessage = driver.findElement(By.xpath("//android.widget.Toast")).getAttribute("name");
//		System.out.println(toastMessage);
//		Assert.assertEquals(toastMessage, "Please enter your name");
//	}

	public void addToCartProduct1Product() throws Exception {
		fillForm();
		Thread.sleep(2000);
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"LeBron Soldier 12 \"))"));
		List<WebElement> productsName = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
		for (int i = 0; i < productsName.size(); i++) {
			String productName = productsName.get(i).getText();
			if (productName.equalsIgnoreCase("LeBron Soldier 12 ")) {
				driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
				break;
			}
		}
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Cart']")));
		// Verify same product is added on add to cart page

		String actProductName = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
		Assert.assertEquals(actProductName, "LeBron Soldier 12 ");
		driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
		WebElement ele = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));

		driver.findElement(By.id("android:id/button1")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(5000);
	}

	@Test
	public void add2ProductsAndComparePrice() throws Exception {
		fillForm();
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		List<WebElement> products = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));
		products.get(0).click();
		wait.until(ExpectedConditions.textToBePresentInElement(products.get(0), "ADDED TO CART"));
		driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(1).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Cart']")));
		// Verify same product is added on add to cart page

		List<WebElement> priceList = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
		Double sum = 0.0;
		for (int i = 0; i < priceList.size(); i++) {
			String priceWithDollor = priceList.get(i).getText();
			String priceWithoutDollar = priceWithDollor.substring(1);
			Double price = Double.parseDouble(priceWithoutDollar);
			sum = sum + price;
		}
		String totalPriceWithDollar = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl"))
				.getText();
		String totalPriceWithoutDollar = totalPriceWithDollar.substring(1);
		Double totalPrice = Double.parseDouble(totalPriceWithoutDollar);
		Assert.assertEquals(sum, totalPrice);
		driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
		WebElement ele = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "duration", 2000));

		driver.findElement(By.id("android:id/button1")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(10000);
		
		Set<String> contexts =  driver.getContextHandles();
		for(String contextName : contexts) {
			System.out.println(contextName);
			if(contextName.contains("WEB")) {
				driver.context(contextName);
			}
		}
		driver.findElement(By.name("q")).sendKeys("Rahul Shetty Academy", Keys.ENTER);
	}

}
