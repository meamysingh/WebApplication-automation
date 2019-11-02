package com.appname.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.appname.qa.base.ApplicationTestBase;

public class AddToCartPage extends ApplicationTestBase {
	
	@FindBy(xpath = "//a[@class='logo--link']")
	WebElement clubKitchenLogo;
//	
	@FindBy(xpath = "//a[contains(@class,'shop-menu-btn')]")
//	@FindBy(xpath = "(//li[@role='menuitem'])[1]")
	WebElement toTheMenuBtn;
	
	@FindBy(xpath = "//a[contains(@class,'select-restaurant')]")
	WebElement selectRestaurantBtn;
//	
	@FindBy(xpath = "//li//a[@title='Mamacita']")
	WebElement restaurantLogo;
	
	@FindBy(xpath = "//a[@class='btn change-address']")
	WebElement changeAddressBtn;
//	
	@FindBy(id = "address-input")
	WebElement addressTextFld;
	
	
	@FindBy(xpath = "//input[@type='submit']")
	WebElement submitAddressBtn;
	
	@FindBy(xpath = "(//button[@class='buybox--button '])[1]")
	WebElement addToCartBtn;
	
	@FindBy(xpath = "//div[@class='alert is--success is--rounded']//div[@class='alert--content']")
	WebElement confirmtionMessage;
	
	@FindBy(id = "topup-modal--close")
	WebElement confirmationAddToCartBtn;
	
	@FindBy(xpath = "//p[@id='customer_address']//span")
	WebElement dilieveryAddress;
	
	public WebElement getDynamicWEByText(String address)
	{
		return driver.findElement(By.xpath("//font[text()='"+address+"']"));
	}
	
	
	// Initializing the Page Objects:
		public AddToCartPage() {
			PageFactory.initElements(driver, this);
		}

		
		public boolean isHomePageLoaded() {
			waitForIsDisplayed(clubKitchenLogo, 20);
			if(clubKitchenLogo.isDisplayed())
			{
				Reporter.log("Successfuly navigated to homepage", true);
				return true;
			}
			else
			{
				Reporter.log("unable to navigated to homepage", true);
				return false;
			}
		}
		
		public void clickToTheMenuBtn()
		{
			waitForIsDisplayed(toTheMenuBtn,20);
			toTheMenuBtn.click();
			Reporter.log("Successfuly navigated to to the menu screen", true);
		}
		
		public void selectHotel()
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			waitForIsDisplayed(selectRestaurantBtn,20);
			waitForIsCLickable(selectRestaurantBtn, 20);
			executor.executeScript("arguments[0].click();", selectRestaurantBtn);
			Reporter.log("Clicked on select hotel button", true);
			waitForIsDisplayed(restaurantLogo,20);
			waitForIsCLickable(restaurantLogo, 20);
			executor.executeScript("arguments[0].click();", restaurantLogo);
			Reporter.log("Successfuly navigated to homepage", true);
			Reporter.log("Selected the desired hotel", true);

		}
		
		public void setAddress(String address)
		{
			waitForIsDisplayed(addressTextFld,20);
			addressTextFld.sendKeys(address);
			Reporter.log("Entered the address: "+address, true);
			waitForIsCLickable(submitAddressBtn, 20);
			submitAddressBtn.click();
//			changeAddressBtn.click();
			waitForIsDisplayed(dilieveryAddress, 20);
		    Assert.assertEquals(dilieveryAddress.getText(), prop.getProperty("address"));
		    Reporter.log("Address has been selected as: "+address, true);
		}
		
		public void performAddTocart()
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			waitForIsDisplayed(addToCartBtn, 20);
			waitForIsCLickable(addToCartBtn, 20);
			executor.executeScript("arguments[0].click();", addToCartBtn);
			waitForIsDisplayed(confirmationAddToCartBtn, 20);
			executor.executeScript("arguments[0].click();", confirmationAddToCartBtn);
			Reporter.log("clicked on add to cart button", true);
			waitForIsDisplayed(confirmtionMessage, 20);
			Assert.assertTrue(confirmtionMessage.isDisplayed(), confirmtionMessage.getText());
			Reporter.log("Successfuly added an item into cart with the success message: "+confirmtionMessage.getText(), true);
		}
		
		
		public void addToCartTest(String address) {
			
			isHomePageLoaded();
			clickToTheMenuBtn();
			setAddress(address);
			selectHotel();
			performAddTocart();
			
		}
}
