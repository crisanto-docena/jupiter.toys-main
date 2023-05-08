package com.jupitertoys.pageobjects;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

	WebDriver driver;

	@FindBy(xpath = "//table[contains(@class, 'cart-item')]/tfoot//td/strong[contains(@class, 'total')]")
	private List<WebElement> tblSubTotal;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	public String getTblValuebyCol(String col, String row) throws ParseException {


		//get first the index of the column you want to get the value from
		int colIndex = this.getTblColIndexbyVal(col);

		//get first the index of the Row you want to get the value from
		int rowIndex = this.getTblRowIndexbyVal(row);

		//get the values in the cell
		//special case for column "Quantity" as the value is inside input tags with attribute value
		List<WebElement> otblCellValue;

		if (col.equals("Quantity")) {
			otblCellValue = driver.findElements(By.xpath("//table[contains(@class, 'cart-item')]/tbody//tr[" + rowIndex + "]/td[" + colIndex + "]/input"));

		}else{
			otblCellValue = driver.findElements(By.xpath("//table[contains(@class, 'cart-item')]/tbody//tr[" + rowIndex + "]/td[" + colIndex + "]"));
		}

		String tblCellVal = "";

		//highlightElement(otblCellValue.get(0));


		if (col.equals("Quantity")) { //special case for column "Quantity as the value is inside input tags with attribute value

			tblCellVal = otblCellValue.get(0).getAttribute("value");

		}else {

			tblCellVal = otblCellValue.get(0).getText();

		}
		//conversions needed for Price and Subtotal column value
		if (col.equals("Price") || col.equals("Subtotal")) {
			Locale locale = Locale.US;
			Number number = NumberFormat.getCurrencyInstance(locale).parse(tblCellVal);
			tblCellVal = number.toString();
		}

		System.out.println("Cell value: " + tblCellVal);

		return tblCellVal;
	}

	public int getTblRowIndexbyVal(String firstColVal) {

		List<WebElement> otblRow = driver.findElements(By.xpath("//table[contains(@class, 'cart-item')]/tbody//tr"));

		int tblRowLength = otblRow.size();

		int rowIndex = 0;

		for (int i = 0; i<tblRowLength;i++) {
			if (otblRow.get(i).getText().trim().contains(firstColVal)) {
				rowIndex = i+1;
				break;
			}

			if (i == tblRowLength) {
				System.out.println("Row value not found in table");
			}

		}

		System.out.println("Row: " + firstColVal + " has index of: " + rowIndex);

		return rowIndex;
	}

	public int getTblColIndexbyVal(String col) {

		//get first the column index
		List<WebElement> otblHeader = driver.findElements(By.xpath("//table[contains(@class, 'cart-item')]//th"));

		int tblHeadLength = otblHeader.size();

		int colIndex = 0;

		for (int i = 0; i<tblHeadLength;i++) {
			if (otblHeader.get(i).getText().trim().equals(col)) {
				colIndex = i+1;
				break;
			}

			if (i == tblHeadLength) {
				System.out.println("Column not found in table");
			}

		}

		System.out.println("Column: " + col + " has index of: " + colIndex);

		return colIndex;

	}

	public boolean checkIfitemExistInCart(String itemVal) {

		boolean exists = false;

		int index = getTblRowIndexbyVal(itemVal);

		if (index > 0) {
			exists = true;
		}else {
			exists = false;
		}

		return exists;
	}

	public double getTblCartTotal() {

		String sTotal = tblSubTotal.get(0).getText();
		sTotal = sTotal.replaceAll("[^0-9.]", "");        
		System.out.println(sTotal);
		double iTotal = Double.parseDouble(sTotal);

		System.out.println("Total: " + iTotal);
		return iTotal;
	}

	public void highlightElement(WebElement elem) {
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", elem);
	}
}
