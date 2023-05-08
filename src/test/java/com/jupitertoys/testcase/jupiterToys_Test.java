package com.jupitertoys.testcase;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jupitertoys.pageobjects.CartPage;
import com.jupitertoys.pageobjects.ContactPage;
import com.jupitertoys.pageobjects.Navigate;
import com.jupitertoys.pageobjects.ShopPage;
import com.jupitertoys.testbase.Testbase;
import com.jupitertoys.util.ExtentReporting;

public class jupiterToys_Test extends Testbase{

	
	//Test Case 1
	@Test(dataProvider = "testData")
	public void testCase1(String dataForname, 
			String dataEmail, 
			String dataMessage,
			String dataErrForname,
			String dataErrEmail,
			String dataErrMessage) throws IOException {

		WebDriver driver = null;
		driver = this.openBrowser(driver);

		ExtentReporting reportLog = new ExtentReporting(driver, "testCase1");

		try {

			//1. From the home page go to contact page
			Navigate nav = new Navigate(driver);
			nav.navigateContactPage();
			reportLog.logResult("Step 1: From the home page go to contact page", true, "pass");

			//2. Click submit button
			ContactPage contactPage = new ContactPage(driver);
			contactPage.clickSubmit();
			reportLog.logResult("Step 2: Click submit button", true, "pass");

			//3. Validate errors
			Assert.assertEquals(contactPage.getErrorForname(), dataErrForname);
			Assert.assertEquals(contactPage.getErrorEmail(), dataErrEmail);
			Assert.assertEquals(contactPage.gettErrorMessage(), dataErrMessage);
			reportLog.logResult("Step 3:  Validate errors", true, "pass");

			//4. Populate mandatory fields
			contactPage.enterForname(dataForname);
			contactPage.enterEmail(dataEmail);
			contactPage.enterMessage(dataMessage);
			reportLog.logResult("Step 4:  Populate mandatory fields", true, "pass");


			//5. Validate errors are gone
			Assert.assertFalse(contactPage.isErrorFornameExist());
			Assert.assertFalse(contactPage.isErrorEmailExist());
			Assert.assertFalse(contactPage.isErrorMessageExist());
			reportLog.logResult("Step 5: Validate errors are gone", true, "pass");

			System.out.println("Test # 1 is completed");
			System.out.println("The thread ID for testCase1 is "+ Thread.currentThread().getId());

			reportLog.endlogResult();
			this.endProcess(driver);

		}catch(AssertionError e) {
			reportLog.logResult("Step Error: " + e, true, "fail");
			reportLog.endlogResult();
			this.endProcess(driver);
			Assert.fail();
		}
	}

	
	//Test Case 2
	@Test(dataProvider = "testData")
	public void testCase2(String dataForname,
			String dataEmail,
			String dataMessage,
			String dataSuccMsg) throws IOException {

		WebDriver driver = null;
		driver = this.openBrowser(driver);
		
		
		ExtentReporting reportLog = new ExtentReporting(driver, "testCase2");

		try {
			//1. From the home page go to contact page
			Navigate nav = new Navigate(driver);
			nav.navigateContactPage();
			reportLog.logResult("Step 1: From the home page go to contact page", true, "pass");

			//2. Populate mandatory fields
			ContactPage contactPage = new ContactPage(driver);
			contactPage.enterForname(dataForname);
			contactPage.enterEmail(dataEmail);
			contactPage.enterMessage(dataMessage);
			reportLog.logResult("Step 2: Populate mandatory fields", true, "pass");

			//3. Click submit button
			contactPage.clickSubmit();
			reportLog.logResult("Step 3: Click submit button", true, "pass");

			//4. Validate successful submission message
			Assert.assertEquals(contactPage.getSuccessMessage(), dataSuccMsg);
			reportLog.logResult("Step 4: Validate successful submission message", true, "pass");

			//Note: Run this test 5 times to ensure 100% pass rate
			System.out.println("Test # 2 is completed");

			System.out.println("The thread ID for testCase2 is "+ Thread.currentThread().getId());

			reportLog.endlogResult();
			this.endProcess(driver);

		}catch(Throwable e){
			reportLog.logResult("Step Error: " + e, true, "fail");
			reportLog.endlogResult();
			this.endProcess(driver);
			Assert.fail();

		}
	}

	
	//Test Case 3
	@Test(dataProvider = "testData")
	public void testCase3(String dataItem1, String dataItem2, String dataItem3) throws IOException, ParseException {

		WebDriver driver = null;
		driver = this.openBrowser(driver);
				
		ExtentReporting reportLog = new ExtentReporting(driver, "testCase3");

		try {

			Navigate nav = new Navigate(driver);
			nav.navigateShopPage();

			//1. Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear
			ShopPage shopPage = new ShopPage(driver);

			String[] arrItem1 = dataItem1.split("\\|"); //Stuffed frog

			String item1 = arrItem1[0];
			int item1Qty = Integer.parseInt(arrItem1[2]);
			double item1Price = Double.parseDouble(arrItem1[1]);
			shopPage.buyItem(item1, item1Qty);

			String[] arrItem2 = dataItem2.split("\\|"); //Fluffy Bunny
			String item2 = arrItem2[0];
			int item2Qty = Integer.parseInt(arrItem2[2]);
			double item2Price = Double.parseDouble(arrItem2[1]);
			shopPage.buyItem(item2, item2Qty);

			String[] arrItem3 = dataItem3.split("\\|"); //Valentine Bear
			String item3 = arrItem3[0];
			int item3Qty = Integer.parseInt(arrItem3[2]);
			double item3Price = Double.parseDouble(arrItem3[1]);
			shopPage.buyItem(item3, item3Qty);
			reportLog.logResult("Step 1: Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear", true, "pass");

			//2. Go to the cart page
			nav.navigateCartPage();
			reportLog.logResult("Step 2: Go to the cart page", true, "pass");

			//3. Verify the price for each product
			CartPage cartPage = new CartPage(driver);

			double priceSFrog = Double.parseDouble(cartPage.getTblValuebyCol("Price", item1));
			Assert.assertEquals(priceSFrog, item1Price);

			double priceFBunny = Double.parseDouble(cartPage.getTblValuebyCol("Price", item2));
			Assert.assertEquals(priceFBunny, item2Price);

			double priceVBear = Double.parseDouble(cartPage.getTblValuebyCol("Price", item3));
			Assert.assertEquals(priceVBear, item3Price);

			reportLog.logResult("Step 3: Verify the price for each product", true, "pass");

			//4. Verify that each product’s sub total = product price * quantity
			double qtySFrog = Double.parseDouble(cartPage.getTblValuebyCol("Quantity", "Stuffed Frog"));
			double qtyFBunny = Double.parseDouble(cartPage.getTblValuebyCol("Quantity", "Fluffy Bunny"));
			double qtyVBear = Double.parseDouble(cartPage.getTblValuebyCol("Quantity", "Valentine Bear"));

			double sTotalSFrog = Double.parseDouble(cartPage.getTblValuebyCol("Subtotal", "Stuffed Frog"));
			double sTotalFBunny = Double.parseDouble(cartPage.getTblValuebyCol("Subtotal", "Fluffy Bunny"));
			double sTotalVBear = Double.parseDouble(cartPage.getTblValuebyCol("Subtotal", "Valentine Bear"));

			Assert.assertEquals(sTotalSFrog, (priceSFrog * qtySFrog));
			Assert.assertEquals(sTotalFBunny, (priceFBunny * qtyFBunny));
			Assert.assertEquals(sTotalVBear, (priceVBear * qtyVBear));

			reportLog.logResult("Step 4: Verify that each product's sub total = product price * quantity", true, "pass");

			//5. Verify that total = sum(sub totals)
			double dblCartTotal = cartPage.getTblCartTotal();

			Assert.assertEquals(dblCartTotal, (sTotalSFrog + sTotalFBunny + sTotalVBear));
			reportLog.logResult("Step 5: Verify that total = sum(sub totals)", true, "pass");

			System.out.println("Test # 3 is completed");
			System.out.println("The thread ID for testCase3 is "+ Thread.currentThread().getId());

			reportLog.endlogResult();
			this.endProcess(driver);

		}catch(Throwable e){
			reportLog.logResult("Step Error: " + e, true, "fail");
			reportLog.endlogResult();
			this.endProcess(driver);
			Assert.fail();
		}

	}

	@DataProvider (name = "testData")
	public Object[][] dpMethod (Method m){
		switch (m.getName()) {
		case "testCase1": 
			return new Object[][] {
				{"Doc", "RaphDoc@test.com", "This is a test message!!", "Forename is required", "Email is required", "Message is required"}};
		case "testCase2": 
			return new Object[][] {{"Doc", "RaphDoc@test.com", "This is a test message!!", "Thanks Doc, we appreciate your feedback."},  //5 iterations for 5 sets of different data
				{"Rob", "CaseyRob@test.com", "This is a test message!!", "Thanks Rob, we appreciate your feedback."},
				{"Isah", "ReeceIsah@test.com", "This is a test message!!", "Thanks Isah, we appreciate your feedback."},
				{"Llans", "RoeLlans@test.com", "This is a test message!!", "Thanks Llans, we appreciate your feedback."},
				{"Sani", "CarissaSani@test.com", "This is a test message!!", "Thanks Sani, we appreciate your feedback."}};
		case "testCase3": 
			return new Object[][] {{"Stuffed Frog|10.99|2", "Fluffy Bunny|9.99|5", "Valentine Bear|14.99|3"}}; //Shop items|Price|Quantity
		}

		return null;

	}

}
