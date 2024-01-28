package com.eva.vtiger.utils;

import java.time.Duration; 
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUtilByRahulSir {

	public static WebDriver driver;  ////  null

	public static void launchBrowser() {
		try {
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			System.out.println("Browser launched successfully");
		}catch(Exception e) {

		}
	}


	public static void mouseOver(String xpath, String elementName) {
		WebElement we= myFindElement(xpath, elementName);    
		Actions actionsObj=new Actions(driver);
		actionsObj.moveToElement(we).build().perform();

	}



	public static void openUrl(String url) {

		try {
			driver.get(url);
			System.out.println("the Given url :-"+url+" has opened successfully");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("the Given url :-"+url+" hasn't opened successfully");
			throw e;

		}
	}


	public static WebElement myFindElement(String xpath,String elementName) {
		WebElement webObj=null;
		try {
			webObj=driver.findElement(By.xpath(xpath));
			System.out.println(elementName+" is found successfully");

		}catch(NoSuchElementException e) {
			WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(60));
			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			webObj=driver.findElement(By.xpath(xpath));
			System.out.println(elementName+"is found successfully");
			throw e;

		}catch(InvalidSelectorException e) {
			e.printStackTrace();
			System.out.println(elementName+"is not found successfully because the syntax of xpath :-- "+xpath+"  is wrong");

			throw e;

		}catch(NullPointerException e){

			driver=new ChromeDriver();	

			System.out.println("browser has launched");

			driver.get("http://localhost:8888");
			System.out.println("browser has lauched this link http://localhost:8888");

			webObj=driver.findElement(By.xpath("//input[@name='user_name']"));
			System.out.println(elementName+"is found successfully");
			throw e;

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(elementName+" is not found successfully");

			throw e;
		}
		return webObj;
	}
	public static void myClear(String xpath,String elementName) {
		WebElement webObj=myFindElement(xpath, elementName);
		System.out.println("we have found "+elementName+" successfully");

		try {
			webObj.clear();
			System.out.println(elementName+" textbox is cleared successfully");

		}catch(ElementNotInteractableException e) {

			JavascriptExecutor js =(JavascriptExecutor)driver;
			js.executeScript("arguments[0].value=''",webObj);
			System.out.println(elementName+" textbox is cleared successfully");
		}catch(StaleElementReferenceException e) {
			webObj=myFindElement(xpath, elementName);
			System.out.println("we have found "+elementName+" successfully");
			webObj.clear();
			System.out.println(elementName+" textbox is cleared successfully");
		}catch(Exception e) {

			e.printStackTrace();
			System.out.println(elementName+" textbox is  not cleared successfully");

			throw e;

		}

	}

	public  static void mySendKeys(String xpath,String elementName, String inputValue) throws ElementClickInterceptedException {

		WebElement webObj= myFindElement(xpath, elementName);

		try {
			webObj.sendKeys(inputValue);
			System.out.println(inputValue+" value is passed in "+elementName+" textbox successfully");
		}catch(ElementNotInteractableException e) {
			JavascriptExecutor jse=(JavascriptExecutor)driver;
			jse.executeScript("arguments[0].value='"+inputValue+"'",webObj );
			System.out.println(inputValue+" value has entered in "+elementName+" textbox successfully");

		}catch(StaleElementReferenceException e) {
			webObj=driver.findElement(By.xpath("//input[@name='"+inputValue+"']"));
			System.out.println("we have found "+elementName+" successfully");
			webObj.sendKeys(inputValue);
			System.out.println(inputValue+" value has entered in "+elementName+" textbox successfully");

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(inputValue+" value hasn't entered in "+elementName+" textbox successfully");
			throw e;
		}

	}

	public static void myClick(String xpath,String elementName) {
		WebElement webObj=myFindElement(xpath , elementName);
		try {
			webObj.click();
			System.out.println(elementName+" element is clicked successfully");

		}catch(ElementClickInterceptedException e) {

		}catch(ElementNotInteractableException e) {


			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", webObj);
			System.out.println(elementName+" element is clicked successfully");

		}catch(StaleElementReferenceException e) {

			webObj=myFindElement( xpath, elementName);
			webObj.click();
			System.out.println(elementName+" element is clicked successfully");

		}

		catch(Exception e) {
			e.printStackTrace();
			System.out.println(elementName+" element is not clicked successfully");
			throw e;

		}

	}

	public static String myGetText( String xpath, String elementName) {
		WebElement we=myFindElement( xpath, elementName);
		String innerText=null;
		try {
			innerText=we.getText();
			System.out.println(innerText+" - innertext of "+elementName+" got successfully");
		}catch(StaleElementReferenceException e) {

			we=myFindElement(xpath, elementName);
			innerText=we.getText();
			System.out.println(innerText+" - innertext of "+elementName+" got successfully");

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(" innertext not found");
			throw e;

		}
		return innerText;
	}

	public static void verifyString(String actualText, String expectedText) {
		if(actualText.equalsIgnoreCase(expectedText)) {
			System.out.println("Passed");
		}else {
			System.out.println("Failed");
		}

	}


	public static void selectByText(String xpath, String elementName, String selectText) {

		WebElement we = myFindElement(xpath, elementName);
		try {

			Select selectObj=new Select(we);
			selectObj.selectByVisibleText(selectText);
			///  css selector 
		}catch(ElementNotInteractableException e) {
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("documents.getElementById("+e+").click()");			
			WebElement weOption = driver.findElement(By.xpath("//option[text()='"+selectText+"']"));
			js.executeScript(" var element = document   arguments[0].click()", weOption);
		}

	}  ///  HTML DOM -  Document Object Model 

	///  Selenium IDE Java Script 
	//  Selnium RC  Java Script 


	public static void jsDragAndDrop() {

		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("var evt = element.ownerDocument.createEvent('MouseEvents');\r\n"
				+ "\r\n"
				+ "    var RIGHT_CLICK_BUTTON_CODE = 2; // the same for FF and IE\r\n"
				+ "\r\n"
				+ "    evt.initMouseEvent('contextmenu', true, true,\r\n"
				+ "         element.ownerDocument.defaultView, 1, 0, 0, 0, 0, false,\r\n"
				+ "         false, false, false, RIGHT_CLICK_BUTTON_CODE, null);\r\n"
				+ "\r\n"
				+ "    if (document.createEventObject){\r\n"
				+ "        // dispatch for IE\r\n"
				+ "       return element.fireEvent('onclick', evt)\r\n"
				+ "     }\r\n"
				+ "    else{\r\n"
				+ "       // dispatch for firefox + others\r\n"
				+ "      return !element.dispatchEvent(evt);\r\n"
				+ " ");			

	}

	public static String getSelectedText(String xpath, String elementName) {
		WebElement we=myFindElement(xpath, elementName);
		Select selObj =	new Select(we);
		WebElement weSelectedItem=selObj.getFirstSelectedOption();
		String selectedText=weSelectedItem.getText();
		return selectedText;
	}

	public static int getAllOptionsCount(String xpath, String elementName) {
		WebElement we=myFindElement(xpath, elementName);
		Select selectObj=new Select(we);
		List<WebElement> listElement=selectObj.getOptions();
		int itemCount=listElement.size();
		return itemCount;
	}

	public static List<String> getTextOfAllOptions(String xpath, String elementName) {
		List<String> listOptionsText =  new ArrayList<String>();
		WebElement we=myFindElement(xpath, elementName);
		Select selectObj=new Select(we);
		List<WebElement> weOptionsList=selectObj.getOptions();
		for(int i=0; i<=weOptionsList.size()-1; i++) {
			WebElement weOption =weOptionsList.get(i);
			String optionText=weOption.getText();
			listOptionsText.add(optionText);
		}
		return listOptionsText;
	}

	public static void switchToFrameByWebElemenet( String frameXpath, String elementName) {
		WebElement weFrame=myFindElement(frameXpath, elementName);
		driver.switchTo().frame(weFrame);
	}

	public static void switchToWindowByTitle(String expectedTitle) {
		Set<String> handles = driver.getWindowHandles();
		for (String handleValue : handles) {
			driver.switchTo().window(handleValue);
			String title = driver.getTitle();
			if (title.equalsIgnoreCase(expectedTitle)) {
				break;
			}
		}
	}

	public static void switchToWindowByTitleContains(String expectedTitle) {
		Set<String> handles = driver.getWindowHandles();
		for (String handleValue : handles) {
			driver.switchTo().window(handleValue);
			String title = driver.getTitle();
			if (title.contains(expectedTitle)==true) {
				break;
			}
		}
	}

	public static void switchToWindowByUrl(String expectedUrl) {

		Set<String> handles = driver.getWindowHandles();
		for (String handleValue : handles) {
			driver.switchTo().window(handleValue);
			String url = driver.getCurrentUrl();
			if (url.equalsIgnoreCase(expectedUrl)) {

				break;
			}
		}
	}

	public static void switchToWindowByUrlContains(String expectedUrl) {

		Set<String> handles = driver.getWindowHandles();
		for (String handleValue : handles) {
			driver.switchTo().window(handleValue);
			String url = driver.getCurrentUrl();
			if (url.contains(expectedUrl)) {

				break;
			}
		}
	}	

	///  findElements 

	public static void checkAllCheckboxes(String xpath, String elementCollectionName) {
		List<WebElement> weListCheckboxes=  driver.findElements(By.xpath(xpath));
		for(int i=0; i<=weListCheckboxes.size()-1;i++) {
			WebElement weCheckbox=weListCheckboxes.get(i);
			if(weCheckbox.isSelected()==false) {
				weCheckbox.click();
			}

		}
	}

	public static void uncheckAllCheckboxes(String xpath, String elementCollectionName) {
		List<WebElement> weListCheckboxes=  driver.findElements(By.xpath(xpath));
		for(int i=0; i<=weListCheckboxes.size()-1;i++) {
			WebElement weCheckbox=weListCheckboxes.get(i);
			if(weCheckbox.isSelected()==true) {
				try {

					weCheckbox.click();
				}catch(ElementNotInteractableException e) {

				}


			}

		}
	}

	public static int getTableRowCount(String tableXpath, String elementName) {
		
		List<WebElement> weRowsList=driver.findElements(By.xpath(tableXpath+"//tr"));
		int rowCount=weRowsList.size()-1;
		return rowCount;
	}

	public static int getTableColumnHeaderCount(String tableXpath, String elementName) {
		List<WebElement> weListColumns=driver.findElements(By.xpath(tableXpath+"//tr[1]//td"));
		int columnCount=weListColumns.size();
		return columnCount;
	}


	/*  in this method we want all the column header names in a list<String>   */
	public static List<String>  getTableColumnNamesList(String tableXpath, String elementName) {
		List<WebElement> weListColumns=driver.findElements(By.xpath(tableXpath+"//tr[1]//td"));
		List<String> listColumnNames=new ArrayList<String>();
		int columnCount=weListColumns.size();
		for(int i=0; i<=columnCount-1;i++) {
			WebElement  weColumnHeader=weListColumns.get(i);
			String columnName=weColumnHeader.getText();
			listColumnNames.add(columnName);
		}
		return listColumnNames;
	}

	/*  this method returns column number on the basis of column name*/
	public static int getColumnNumberByColumnName(String tableXpath, String tableName, String columnName) {
        int columnNumber=-1;
		List<WebElement> listColumnNames=driver.findElements(By.xpath(tableXpath+"//tr[1]//td"));
		int columnCount=listColumnNames.size();
		for(int i=0; i<=columnCount-1;i++) {
			WebElement weTableColumn=listColumnNames.get(i);
			String tablColumnName=weTableColumn.getText();
			if(tablColumnName.equalsIgnoreCase(columnName)==true) {
                 columnNumber=i;
                 break;
			}
		}
		
		return columnNumber;
				
	}


	/* this method returns row data in list on the basis of row number*/
	public static List<String>  getRowDataListByRowNumber(String tableXpath, String tableName, int rowNumber) {
		List<WebElement> weListRowData=driver.findElements(By.xpath(tableXpath+"//tr["+(rowNumber+1)+"]//td"));
		List<String> rowDataList=new ArrayList<String>();
		for(int i=0;i<=weListRowData.size()-1;i++) {
			WebElement weRowData=weListRowData.get(i);
			String data=weRowData.getText();
			rowDataList.add(data);
		}
		return rowDataList;
	}
	

	public static List<String> getColumnDataListByColumnNumber(String tableXpath,String tableName,int columnNumber) {
		List<WebElement> columnsList=driver.findElements(By.xpath( tableXpath+"//tr//td["+columnNumber+"]"));
		List<String> columnNameList =new ArrayList<>();
		for (int i = 1; i < columnsList.size(); i++) {
			String columnName=columnsList.get(i ).getText();
			columnNameList.add(columnName);
		}
		return columnNameList;
	}
	
	public static List<String> getColumnDataListByColumnName(String tableXpath,String tableName,String columnName) {
//		int columnNumber=-1;
//		List<WebElement> listColumnNames=driver.findElements(By.xpath(tableXpath+"//tr[1]//td"));
//		int columnCount=listColumnNames.size();
//		for(int i=0; i<=columnCount-1;i++) {
//			WebElement weTableColumn=listColumnNames.get(i);
//			String tablColumnName=weTableColumn.getText();
//			if(tablColumnName.equalsIgnoreCase(columnName)==true) {
//                 columnNumber=i;
//                 break;
//			}
//		}
//		
//		List<WebElement> columnsList=driver.findElements(By.xpath( tableXpath+"//tr//td["+columnNumber+"]"));
//		List<String> columnNameList =new ArrayList<>();
//		for (int i = 1; i < columnsList.size(); i++) {
//			String tableColumnName=columnsList.get(i ).getText();
//			columnNameList.add(columnName);
//		}
		int columnNumber=getColumnNumberByColumnName(tableXpath, tableName, columnName);
		List<String>columnNameList=getColumnDataListByColumnNumber(tableXpath, tableName, columnNumber);
		
		return columnNameList;
		
	}
	

	public static int getRowNumberByUniqueColumnRowID(String tableXpath, String tableName, String uniqueData, String uniqueColumnName) {
		int rowNumber=-1;
		List<String> columnDataList=getColumnDataListByColumnName(tableXpath, tableName,uniqueColumnName);
		for(int i=0; i<=columnDataList.size()-1;i++) {
			String uniqueColumnData=columnDataList.get(i);
			if(uniqueColumnData.equalsIgnoreCase(uniqueData)) {
				rowNumber=i;
				break;
			}
		}
		return rowNumber;
		
		
		
	}
	
	public static List<String> getRowDataListByRowID(String tableXpath, String tableName, String uniqueData, String uniqueColumnName) {
		int rowNumber=getRowNumberByUniqueColumnRowID(tableXpath, tableName, uniqueData, uniqueColumnName);
	    List<String>listRowData=getRowDataListByRowNumber(tableXpath, tableName, rowNumber);
	    return listRowData;
	}
	
	
	
	public static void printAllTableData() {
		
	}
	

	/// generic methods -  application independent methods 
	////  multiple time use 


	/////  selectByText

	///  getAttributeValue 
	////  Actions -  mouseClick , mouseOver, doubleCLick dragAndDrop , mouseRightClick , SendKeys

	/////  dropdown getSelectedText  , getOptionsCount ,  getTextListOfAllOptions

	///  getUrl
	/// getTitle

	//     switch

	//   1) 



}
