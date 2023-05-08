package com.jupitertoys.testbase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;



public class Testbase {
	public Properties config;
	public int shortSec = 1;
	public int medSec = 10;
	public int longSec = 30;
	public String reportFolderName = "";
	public String browser = "" ; //default
	 
	@BeforeSuite
	@Parameters({"browser"})
	public void initialization(@Optional("CHROME") String browser) throws IOException {
		
		System.out.println("Before suite initialized");
		
		config = new Properties();
        FileInputStream configFile = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/config/RunConfiguration.properties");
        config.load(configFile);
        
        this.browser = browser;
        
	}
	
	public WebDriver openBrowser(WebDriver driver) throws IOException {
		
		if (config.getProperty("runType").trim().equals("local")) {
			
			System.out.println("Running in Local");
			
			String url = config.getProperty("url");
		
			switch (browser) {
			
			case "CHROME": //for Google Chrome browser
				System.out.println("Running in CHROME");
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")  + "/src/test/resources/drivers/chromedriver.exe");
				
				ChromeOptions options = new ChromeOptions();
	            options.addArguments("test-type");
	            options.addArguments("chrome.switches", "--disable-extensions");
	            options.addArguments("start-maximized");
				
				driver = new ChromeDriver(options);
				
				driver.get(url);
				
				driver.manage().timeouts().implicitlyWait(longSec, TimeUnit.SECONDS);				
				
				break;
			
			case "EDGE": //for Microsoft Edge browser
				System.out.println("Running in EDGE");
				
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")  + "/src/test/resources/drivers/msedgedriver.exe");
				
				driver = new EdgeDriver();
				
				driver.get(url);
				
				driver.manage().timeouts().implicitlyWait(longSec, TimeUnit.SECONDS);
				
				driver.manage().window().maximize();
				
				break;
			
			case "FIREFOX": //for firefox browser
				break;
				
				
			default:
				break;
			}
			
			
		}else if (config.getProperty("runType").trim().equals("saucelabs")) { 
			//for saucelabs
			System.out.println("Running in Saucelabs");
			
			
		}else { 
			//selenium grid 
			System.out.println("Running in Selenium Grid");	
		}
		
		return driver;
		
	}
	
	public void endProcess(WebDriver driver) {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
		
	}
	
	public void setTimeout(int seconds, WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
}
