package com.jupitertoys.pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.jupitertoys.testbase.Testbase;

public class ContactPage extends Testbase {

	WebDriver driver;

	@FindBy(xpath = "//a[text()='Submit']")
	private WebElement btnSubmit;

	//Error messages from each fields
	@FindBy(id = "forename-err")
	private List<WebElement> errForname;

	@FindBy(id = "email-err")
	private List<WebElement> errEmail;

	@FindBy(id = "message-err")
	private List<WebElement> errMessage;

	//input
	@FindBy(id = "forename")
	private WebElement txtForname;

	@FindBy(id = "surname")
	private WebElement txtSurname;

	@FindBy(id = "email")
	private WebElement txtEmail;

	@FindBy(id = "telephone")
	private WebElement txtTelephone;

	@FindBy(id = "message")
	private WebElement txtMessage;

	//success message
	@FindBy(xpath = "//div[@class='alert alert-success']")
	private List<WebElement> msgSuccess;

	public ContactPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickSubmit() {
		btnSubmit.click();
	}

	public boolean isErrorFornameExist() {

		driver.manage().timeouts().implicitlyWait(this.shortSec, TimeUnit.SECONDS);

		boolean exists = errForname.size() != 0;

		driver.manage().timeouts().implicitlyWait(this.longSec, TimeUnit.SECONDS);

		return exists;
	}

	public boolean isErrorEmailExist() {
		driver.manage().timeouts().implicitlyWait(this.shortSec, TimeUnit.SECONDS);

		boolean exists = errEmail.size() != 0;

		driver.manage().timeouts().implicitlyWait(this.longSec, TimeUnit.SECONDS);

		return exists;

	}

	public boolean isErrorMessageExist() {
		driver.manage().timeouts().implicitlyWait(this.shortSec, TimeUnit.SECONDS);

		boolean exists = errMessage.size() != 0;

		driver.manage().timeouts().implicitlyWait(this.longSec, TimeUnit.SECONDS);

		return exists;
	}

	public boolean isSuccessMessageExist() {
		return msgSuccess.size() != 0;
	}

	public String getErrorForname() {

		String errorVal = "";

		if (isErrorFornameExist()) {
			errorVal = errForname.get(0).getText();
		}


		return errorVal;
	}

	public String getErrorEmail() {

		String errorVal = "";

		if (isErrorEmailExist()) {
			errorVal = errEmail.get(0).getText();
		}

		return errorVal;

	}

	public String gettErrorMessage() {
		String errorVal = "";

		if (isErrorMessageExist()) {
			errorVal = errMessage.get(0).getText();
		}

		return errorVal;
	}

	public String getSuccessMessage() {

		String successMsg = "";

		if (isSuccessMessageExist()) {
			successMsg = msgSuccess.get(0).getText();
		}

		System.out.println("Success message is: " + successMsg);

		return successMsg;
	}

	public void validateErrors() {

		String fornameError = getErrorForname();

		String emailError = getErrorEmail();

		String messageError = getErrorEmail();

		Assert.assertEquals("Forename is required", fornameError);
		Assert.assertEquals("Email is required", emailError);
		Assert.assertEquals("Message is required", messageError);

	}

	public void enterForname(String value) {
		txtForname.sendKeys(value);
	}

	public void enterSurname(String value) {
		txtSurname.sendKeys(value);
	}

	public void enterEmail(String value) {
		txtEmail.sendKeys(value);
	}

	public void enterTelephone(String value) {
		txtTelephone.sendKeys(value);
	}

	public void enterMessage(String value) {
		txtMessage.sendKeys(value);
	}

}
