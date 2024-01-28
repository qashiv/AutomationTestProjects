package org.rahulshetty.testrunner;

import java.util.HashMap;
import java.util.List;

import org.rahulshetty.pagewise.CartPage;
import org.rahulshetty.pagewise.CataloguePage;
import org.rahulshetty.pagewise.FillFormPage;
import org.rahulshetty.pagewise.MobileBrowserPage;
import org.rahulshetty.utils.AndroidUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GeneralStoreApkTestRunner extends BaseTest {

	
	@Test(priority = 0)
	public void fillFormErrorValidation() {
		FillFormPage fillForm = new FillFormPage(util);
		fillForm.verifyFormPage();
		fillForm.verifyErrorToast();
	}
	
	@Test( dataProvider = "getData")
	public void fillFormPositiveFlow(HashMap<String, String> input) {

		FillFormPage fillForm = new FillFormPage(util);
		fillForm.verifyFormPage();
		fillForm.selectCountryForShopping(input.get("country"));
		fillForm.setNameField(input.get("name"));
		fillForm.setGender(input.get("gender"));
		fillForm.clickLetsShop();

		CataloguePage catal = new CataloguePage(util);
		catal.addToCard2Products();
		catal.clickOnAddToCart();
		
		CartPage cartPage = new CartPage(util);
		cartPage.verifyCartPage();
		cartPage.verifyTotalPrice();
		cartPage.checkInfoCheckbox();
		cartPage.readTermsCondition();
		cartPage.proceed();
		
		MobileBrowserPage browserPage = new MobileBrowserPage(util);
		browserPage.search(input.get("search"));
	}

	
}
