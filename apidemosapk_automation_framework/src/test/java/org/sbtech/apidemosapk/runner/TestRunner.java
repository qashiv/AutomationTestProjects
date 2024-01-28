package org.sbtech.apidemosapk.runner;

import org.sbtech.apidemosapk.pages.HomePage;
import org.testng.annotations.Test;

public class TestRunner extends BaseTest {

	@Test
	public void runner() throws InterruptedException {
	
		HomePage home = new HomePage(util);
		home.clickOnAccessibility();

	}
}
