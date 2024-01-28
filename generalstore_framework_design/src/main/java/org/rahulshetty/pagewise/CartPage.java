package org.rahulshetty.pagewise;

import org.openqa.selenium.support.PageFactory;
import org.rahulshetty.pagewise_or.CartPageOr;
import org.rahulshetty.utils.AndroidUtil;
import org.testng.Assert;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends CartPageOr {

	protected AndroidUtil util;

	public CartPage(AndroidUtil util) {
		this.util = util;
		PageFactory.initElements(new AppiumFieldDecorator(util.getDriver()), this);
	}

	public void verifyCartPage() {
		util.waitUntilElementVisible(getCartTxt());
		String ActVerificationTxt = util.getText(getCartTxt());
		String expVerificationTxt = "Cart";
		Assert.assertEquals(ActVerificationTxt, expVerificationTxt);
	}

	public void verifyTotalPrice() {

		Double sum = 0.0;
		for (int i = 0; i < getPriceList().size(); i++) {
			String priceWithDollor = getPriceList().get(i).getText();
			String priceWithoutDollar = priceWithDollor.substring(1);
			Double price = Double.parseDouble(priceWithoutDollar);
			sum = sum + price;
		}
		String totalPriceWithDollar = util.getText(getTotalAmount());
		String totalPriceWithoutDollar = totalPriceWithDollar.substring(1);
		Double totalPrice = Double.parseDouble(totalPriceWithoutDollar);
		Assert.assertEquals(sum, totalPrice);
	}
	
	public void checkInfoCheckbox() {
		util.click(getCheckboxOption());
	}
	
	public void readTermsCondition() {
		util.longClickGesture(getTermsCondition());
		util.click(getCloseBtn());
	}
	
	public void proceed() {
		util.click(getProceedBtn());
	}
}
