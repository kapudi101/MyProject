package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import framework.ExcelReader;
import pages.DailyDealsPage;
import pages.ItemDetailsPage;
import pages.ItemsPage;
import pages.ShoppingCartPage;
import pages.HomePage;

public class GlobalSearchTests {
    
	HomePage hp = new HomePage();
	ItemsPage ip = new ItemsPage();
	DailyDealsPage ddp = new DailyDealsPage();
	ItemDetailsPage idp = new ItemDetailsPage();
	ShoppingCartPage scp = new ShoppingCartPage();
	ExcelReader excelReader= new ExcelReader();
	
	@DataProvider(name = "getTestData")
    public Object[][] readFromExcel() throws IOException {    	
        String excelFileDirectory = excelReader.getDataConfigProperties("excelDataDir");
        Object[][] arrayObject = excelReader.getExcelData(excelFileDirectory + "TakealotData.xlsx", "Sheet1");
        return arrayObject;
    }

	@Test
	public void TC1_Given_LandedOnHomePage_When_BrandIsSearched_Then_NumberOfItemsMustBeMoreThanZero() {
		
		// GIVEN I am on Takealot home page
		hp.goToHomePage();
		
		// WHEN I search for a brand
		hp.enterBrandToSearch("Xiaomi"); // Not using excel, no need to execute this test more than once
		hp.pressEnterKey();
		
		// THEN quantity of items must be more than zero
		int itemsQuantity = hp.getItemsQuantity();		
		boolean result = false;
		if (itemsQuantity > 0) {
			result = true;
		}else {
			throw new SkipException("Selected brand has no deals available");
		}
		Reporter.log("This brand has " + itemsQuantity + " items available");
		assertTrue(result);
	}
	
	@Test
	public void TC2_Given_BrandIsSearched_And_FirstItemSelected_When_ItemIsAddedToCart_Then_AddedToCartIsDisplayed()
			throws Throwable {
		
		// GIVEN I search for a brand on home page
		hp.goToHomePage();	
		hp.enterBrandToSearch("Defy"); // Not using excel, no need to execute this test more than once
		hp.pressEnterKey();
		
		// AND select first item
		ip.selectItem(0);
		
		// WHEN I add item to cart
		idp.addItemToCart();
		
		// THEN "Added to cart" text is displayed on page
		String expectedText = "Added to cart";
		String actualText = idp.getTextOnPage();
		Reporter.log("Expected text is " + "\"" + expectedText + "\"" +
				"; text displayed on pop-up is " + "\"" + actualText + "\"");
		assertEquals(actualText, expectedText);
		
	}
	
	@Test(dataProvider = "getTestData")
	public void TC3_Given_ItemsAddedToCart_When_AddingUpTotalPricePerBrand_Then_CorrectCartValueIsDisplayed(
			String brand, String itemsQty) throws Throwable {
		
		int quantity = Integer.parseInt(itemsQty);
		
		// GIVEN I searched for a brand on home page
		hp.closesNewTabAndSwitchBackParentTab();
		hp.goToHomePage();		
		hp.enterBrandToSearch(brand);		
		hp.pressEnterKey();
		
		// AND select first item
		ip.selectItem(0);
		double itemPrice = ip.getPrice();
		double expectedTotalPrice = ip.getCalculatedTotalPrice(itemsQty);
		//double expectedTotalPrice = itemPrice * quantity;
		
		// WHEN I add item to cart
		idp.addItemToCart();		
		idp.clickGoToCart();
		
		// AND select item quantity		
		scp.selectQuantityFromDropDown(itemsQty);		
		Thread.sleep(2000); 	// wait 2 sec for qty to update
		
		// THEN actual (displayed) total price for each brand matches expected (calculated) price
		double actualTotalPrice = scp.getDisplayedTotalPrice();
		Reporter.log("Item price for " + brand + " is R " + itemPrice);
		Reporter.log("Selected quantity for " + brand + " is " + itemsQty);
		Reporter.log("Calculated total price for " + brand + " items is R " + expectedTotalPrice);
		Reporter.log("Displayed total price for " + brand + " items is R " + actualTotalPrice);		
		Assert.assertEquals(actualTotalPrice, expectedTotalPrice);	
	}
	
	@BeforeClass
	public void acceptCookies() {
		hp.closeCookiesBanner();		
	}
	
	@AfterClass
	public void clearCart() throws Exception {
	Thread.sleep(1000);
	scp.clearShoppingCart();
	}
	
//	@AfterClass
//	public void closeBrowser() {
//		hp.closeBrowserSession();
//	}
}
