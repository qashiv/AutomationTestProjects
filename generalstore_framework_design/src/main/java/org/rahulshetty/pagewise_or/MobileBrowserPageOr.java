package org.rahulshetty.pagewise_or;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MobileBrowserPageOr {

	@FindBy(name = "q")
	private WebElement searchBox;

	public WebElement getSearchBox() {
		return searchBox;
	}
}
