package com.jupitertoys.testcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.verboseLogging", "true");
		
		WebDriver newDriver = new ChromeDriver();
		newDriver.get("https://jupiter.cloud.planittesting.com");

	}

}
