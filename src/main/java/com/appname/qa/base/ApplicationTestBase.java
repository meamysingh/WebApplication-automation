package com.appname.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.appname.qa.utils.TestUtil;
import com.appname.qa.utils.WebEventListener;

public class ApplicationTestBase {
		
		public static WebDriver driver;
		public static Properties prop;
		public  static EventFiringWebDriver e_driver;
		public static WebEventListener eventListener;
		
		public ApplicationTestBase(){
			try {
				prop = new Properties();
				FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\resources\\common\\config.properties");
				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void initialization(){
			
			try {
				
				
				String browserName = prop.getProperty("browser");
				
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
				
				switch(browserName.toUpperCase()) {
				
				case "CHROME":
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\servers\\chromedriver.exe");
//					Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
					ChromeOptions chromeOptions = new ChromeOptions();
					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("intl.accept_languages", "en-us");
					chromeOptions.setExperimentalOption("prefs", prefs);
					driver = new ChromeDriver(chromeOptions);
					Reporter.log(prop.getProperty("browser")+" launched successfuly",true);
					break;
					
					
				case "IE":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\servers\\IEDriverServer.exe");
					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
					driver = new InternetExplorerDriver();
					Reporter.log(prop.getProperty("browser")+" launched successfuly",true);
					break;
					
					
				case "FIREFOX":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\servers\\geckodriver.exe");
					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
					driver = new FirefoxDriver();
					Reporter.log(prop.getProperty("browser")+" launched successfuly",true);
					break;
				}
				
				
				e_driver = new EventFiringWebDriver(driver);
				// Now create object of EventListerHandler to register it with EventFiringWebDriver
				eventListener = new WebEventListener();
				e_driver.register(eventListener);
				driver = e_driver;
				
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
				
				driver.get(prop.getProperty("url"));
				Reporter.log("navigated to url: "+prop.getProperty("url"),true);
		
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println(e.getMessage());
			}
			
			
			
				
		}
		
		public void waitForIsDisplayed(WebElement we,long timeout)
		{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(we));
		}
	
		public void waitForIsCLickable(WebElement we,int timeout)
		{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(we));
		}
	
	
	
	
	
	
	
	
	
}
