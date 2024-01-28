package org.rahulshetty.pagewise;

import org.openqa.selenium.support.PageFactory;
import org.rahulshetty.pagewise_or.FillFormPageOr;
import org.rahulshetty.utils.AndroidUtil;
import org.testng.Assert;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FillFormPage extends FillFormPageOr {

	private AndroidUtil util;

	public FillFormPage(AndroidUtil util) {
		this.util = util;
		PageFactory.initElements(new AppiumFieldDecorator(util.getDriver()), this);
	}
	
	public void verifyFormPage() {
		String actHeaderTxt = util.getText(getHeaderTxt());
		String expHeaderTxt = "General Store";
		Assert.assertEquals(actHeaderTxt,expHeaderTxt);
	}
	
	public void selectCountryForShopping(String countryName) {
		util.click(getCountryDropDown());
		util.staticWait(2000);
		util.scrollToText(countryName);
		util.scrollAndClickElement(countryName);
		
	}
	
	public void setGender(String gender) {
		if(gender.contains("female")) {
			util.click(getChooseFemaleGender());
			}else {
				util.click(getChooseMaleGender());
			}
	}
	public void setNameField(String customerName) {
		util.sendKeys(getCustomerName(), customerName);
	}
	
	public void clickLetsShop() {
		util.click(getLetsShopBtn());
	}
	public void verifyErrorToast() {
		util.click(getLetsShopBtn());
		String toastMessage = util.getAttribute(getToastMessage(), "name");
		Assert.assertEquals(toastMessage, "Please enter your name");
	}

}
