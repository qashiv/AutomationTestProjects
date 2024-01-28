package org.rahulshetty.pagewise_or;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class CataloguePageOr {

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
	private List<WebElement> productList;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
	private WebElement addToCartBtn;
	
	public WebElement getAddToCartBtn() {
		return addToCartBtn;
	}
	public List<WebElement> getProductList(){
		return productList;
	}
   	
}
