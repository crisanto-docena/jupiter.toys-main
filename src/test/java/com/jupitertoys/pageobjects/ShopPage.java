package com.jupitertoys.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ShopPage {

	WebDriver driver;

	public ShopPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void buyItem(String item, int qty) {

		List<WebElement> listBuyItem = driver.findElements(By.xpath("//div/h4[text()='" + item + "']//following-sibling::p/a")); 

		//check if item is available in the page;
		if (listBuyItem.size() > 0) {
			for (int i=1;i<=qty;i++) {
				listBuyItem.get(0).click();
			}

		}

	}

}
