package org.rahulshetty.pagewise_or;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class CartPageOr {

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Cart']")
	private WebElement cartTxt;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> priceList;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalAmount;

	@AndroidFindBy(className = "android.widget.CheckBox")
	private WebElement checkBoxoption;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	private WebElement termsCondition;
	
	@AndroidFindBy(id = "android:id/button1")
	private WebElement closeBtn;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	private WebElement proceedBtn;
	
	public WebElement getProceedBtn() {
		return proceedBtn;
	}
	public WebElement getCloseBtn() {
		return closeBtn;
	}
	public WebElement getTermsCondition() {
		return termsCondition;
	}
	public WebElement getCheckboxOption() {
		return checkBoxoption;
	}
	
	public WebElement getTotalAmount() {
		return totalAmount;
	}

	public List<WebElement> getPriceList() {
		return priceList;
	}

	public WebElement getCartTxt() {
		return cartTxt;
	}
}
