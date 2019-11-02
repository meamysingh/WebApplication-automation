package com.appname.qa.testCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appname.qa.base.ApplicationTestBase;
import com.appname.qa.pages.AddToCartPage;

public class AddToCartTest extends ApplicationTestBase{
	AddToCartPage atc;
	public AddToCartTest()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		initialization();
		atc = new AddToCartPage();
	}
	
	@Test(priority=1)
	public void validateAddToCartOperation()
	{
		atc.addToCartTest(prop.getProperty("address"));
	}
	
	
	
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}

}
