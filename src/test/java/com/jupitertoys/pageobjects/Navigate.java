package com.jupitertoys.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.jupitertoys.testbase.Testbase;

public class Navigate extends Testbase {

	WebDriver driver;

	@FindBy(id = "nav-home")
	private WebElement navHome;

	@FindBy(id = "nav-shop")
	private WebElement navShop;

	@FindBy(id = "nav-contact")
	private WebElement navContact;

	@FindBy(id = "nav-cart")
	private WebElement navCart;

	public Navigate(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);	
	}

	public void navigateContactPage() {	
		navContact.click();	
	}

	public void navigateShopPage() {
		navShop.click();
	}

	public void navigateCartPage() {
		navCart.click();
	}

}
