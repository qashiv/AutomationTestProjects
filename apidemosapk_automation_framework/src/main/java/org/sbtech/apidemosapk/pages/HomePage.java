package org.sbtech.apidemosapk.pages;

import org.openqa.selenium.support.PageFactory;
import org.sbtech.apidemosapk.pages.or.HomePageOr;
import org.sbtech.apidemosapk.utils.AndroidUtil;

public class HomePage extends HomePageOr {

	private AndroidUtil util;
	public HomePage(AndroidUtil util) {
		this.util=util;
		PageFactory.initElements(util.getDriver(), this);
	}
	public void clickOnAccessibility() {
		util.clickGesture(getAccessibilityLk1(),"accesibility link");
	}
	
}
