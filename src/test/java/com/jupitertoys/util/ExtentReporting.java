package com.jupitertoys.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporting {
	WebDriver driver;
	ExtentTest test;
	ExtentReports report;
	String folderName;
	String testName;

	public ExtentReporting(WebDriver driver, String testName) {
		this.driver = driver;
		this.testName = testName;
		reportInitialize();

	}

	public void reportInitialize() {

		//initialize reporting
		DateFormat screenshotFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		Date date = new Date();


		folderName = "Test_Results_" + testName + "_" + screenshotFormat.format(date);

		report = new ExtentReports(System.getProperty("user.dir") + "/test_report.dir/" + folderName + "/" + testName + "_ReportResults.html");
		test = report.startTest("<b> Test Case: <b> " + testName);
	}

	public String takeScreenshot() throws IOException {
		try {
			TimeUnit.SECONDS.sleep(1); //added this hardwait to ensure no duplication of screenshot as script runs really fast.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_ss_mm");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(("test_report.dir") + "/" + folderName + "/" + dateTime + ".png"));

		return dateTime;

	}

	public void logResult(String description, boolean sShot, String passFail) throws IOException {
		String dateTime = "";
		if (sShot) {
			dateTime = takeScreenshot();
		}


		if (passFail.toLowerCase().equals("pass")) {
			test.log(LogStatus.PASS, description + test.addScreenCapture(dateTime + ".png"));
		}else {
			test.log(LogStatus.FAIL, description + test.addScreenCapture(dateTime + ".png"));
		}

	}

	public void endlogResult() {
		report.endTest(test);
		report.flush();
	}










}
