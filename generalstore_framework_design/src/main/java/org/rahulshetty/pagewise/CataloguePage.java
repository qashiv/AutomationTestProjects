package org.rahulshetty.pagewise;

import org.openqa.selenium.support.PageFactory;
import org.rahulshetty.pagewise_or.CataloguePageOr;
import org.rahulshetty.utils.AndroidUtil;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CataloguePage extends CataloguePageOr {

	protected AndroidUtil util;

	public CataloguePage(AndroidUtil util) {
		this.util = util;
		PageFactory.initElements(new AppiumFieldDecorator(util.getDriver()), this);
	}

	public void addToCard2Products() {
		util.selectByIndexFromMultiElements(getProductList(), 0);
		util.waitUntilTextToBePresent(getProductList().get(0), "ADDED TO CART");
		util.selectByIndexFromMultiElements(getProductList(), 1);
	}
	
	public void clickOnAddToCart() {
		util.click(getAddToCartBtn());
	}
}
