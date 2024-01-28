package org.rahulshetty.pagewise;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.rahulshetty.pagewise_or.MobileBrowserPageOr;
import org.rahulshetty.utils.AndroidUtil;

public class MobileBrowserPage extends MobileBrowserPageOr {

	protected AndroidUtil util;

	public MobileBrowserPage(AndroidUtil util) {
		this.util = util;
		PageFactory.initElements(util.getDriver(), this);
	}

	public void search(String text) {
		util.moveToBrowser();
		util.sendKeys(getSearchBox(), text);
		getSearchBox().sendKeys(Keys.ENTER);
	}
}
