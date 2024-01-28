package org.rahulshetty.testrunner;

import java.util.HashMap;
import java.util.List;

import org.rahulshetty.utils.AndroidUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import io.appium.java_client.android.nativekey.AndroidKey;

public class BaseTest {

	protected AndroidUtil util = AndroidUtil.getObject();

	@BeforeMethod
	public void configuration() {
		util.startAppiumServer();
		util.configuration("Demo");
	}

	@AfterMethod
	public void tearDown() {
		util.takesSnapShot();
		util.pressKey(AndroidKey.BACK);
		util.pressKey(AndroidKey.HOME);
		util.quit();
	    util.closeAppiumServer();	
	}

	@DataProvider
	public Object[][] getData() {
//   public Object[][] getData() {	
		util =  AndroidUtil.getObject();
//		Object[][] obj ={{"Aruba","Satttu","female","Motivational Quotes"},{"India","xyz","female","Positive thoughts"}};
		List<HashMap<String, String>> data = util.getJsonData("D:\\Selenium with Java Programs\\Appium By Rahul Shetty Sir\\generalstore_framework_design\\src\\test\\resources\\GeneralStoreData.json");
	   Object[][] obj = {{data.get(0)},{data.get(1)}} ;
	   return obj;	 
	}
	
}
