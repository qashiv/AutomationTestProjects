package org.rahulshetty.pagewise_or;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class FillFormPageOr {

	
	@AndroidFindBy(id="com.androidsample.generalstore:id/toolbar_title")
	private WebElement headerTxt;

	@AndroidFindBy(id="com.androidsample.generalstore:id/spinnerCountry")
	private WebElement countryDropDown;

	@AndroidFindBy(className = "android.widget.EditText")
	private WebElement customerName;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	private WebElement femaleOption;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radiomale")
	private WebElement maleOption;
	
	@AndroidFindBy(className = "android.widget.Button")
	private WebElement letsShopBtn;

	@AndroidFindBy(xpath = "//android.widget.Toast")
	private WebElement toastMessage;
	
	public WebElement getToastMessage() {
		return toastMessage;
	}
	
	public WebElement getCustomerName() {
		return customerName;
	}
	
	public WebElement getChooseFemaleGender() {
		return femaleOption;
	}

	public WebElement getChooseMaleGender() {
		return maleOption;
	}
	
	public WebElement getLetsShopBtn() {
		return letsShopBtn;
	}

	
	public WebElement getHeaderTxt() {
		return headerTxt;
	}
	
	public WebElement getCountryDropDown() {
		return countryDropDown;
	}
	
}
