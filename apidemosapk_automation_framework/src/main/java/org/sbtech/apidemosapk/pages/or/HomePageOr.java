package org.sbtech.apidemosapk.pages.or;

import org.openqa.selenium.WebElement;

import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;

@Getter
public class HomePageOr {

	@AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Accessibility']")
	private WebElement accessibilityLk1;

}
