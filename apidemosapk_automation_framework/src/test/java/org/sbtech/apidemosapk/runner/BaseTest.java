package org.sbtech.apidemosapk.runner;

import java.lang.reflect.Method;

import org.sbtech.apidemosapk.utils.AndroidUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {

	protected AndroidUtil util = AndroidUtil.getObject();
	private ExtentReports extent;
	
	@BeforeSuite
	public void reports() {
		String dateTime = util.getTimeStamp();
	    extent = new ExtentReports();
		ExtentSparkReporter reporter = new ExtentSparkReporter("ExtentReport\\ExtReport"+dateTime+".html");
	    extent.attachReporter(reporter);
	}
	@BeforeMethod
	public void openApp(Method mt) {
		ExtentTest extTest = extent.createTest(mt.getName());
		util.setExtTestObject(extTest);
//		util.startAppiumServer();
		util.launchApk("Demo");
		util.holdOn(3);
	}
	
	@AfterMethod
	public void closure(ITestResult result, Method mt) {
		if(result.getStatus()==result.FAILURE) {
			String filePath = util.takeSnapShot(mt.getName());
			util.getExtTestObject().addScreenCaptureFromPath(filePath);
			
		}
		extent.flush();
	}
	
	@AfterSuite
	public void tearDown() {
		util.tearDown();
		extent.flush();
	}
}
