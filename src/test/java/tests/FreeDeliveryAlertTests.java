package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.ExcelReader;
import pages.DailyDealsPage;
import pages.HomePage;
import pages.ItemDetailsPage;
import pages.ItemsPage;
import pages.ShoppingCartPage;

public class FreeDeliveryAlertTests {

	HomePage hp = new HomePage();
	ItemsPage ip = new ItemsPage();
	DailyDealsPage ddp = new DailyDealsPage();
	ItemDetailsPage idp = new ItemDetailsPage();
	ShoppingCartPage scp = new ShoppingCartPage();
	ExcelReader excelReader = new ExcelReader();

	@DataProvider(name = "getTestData")
	public Object[][] readFromExcel() throws IOException {
		String excelFileDirectory = excelReader.getDataConfigProperties("excelDataDir");
		Object[][] arrayObject = excelReader.getExcelData(excelFileDirectory + "TakealotData.xlsx", "Sheet2");
		return arrayObject;
	}

	@Test(dataProvider = "getTestData")
	public void TC4_Given_AlertToQualifyForFreeDeliveryDisplayed_When_MoreItemsAddedToQualify_Then_AlertDisappears(
			String brand, String itemsQty) throws Throwable {
		
		// GIVEN I added item/s to card
		hp.closesNewTabAndSwitchBackParentTab();
		hp.goToHomePage();
		hp.enterBrandToSearch(brand);
		hp.pressEnterKey();
		ip.selectItem(0);
		idp.addItemToCart();
		idp.clickGoToCart();
		
		// WHEN items on cart are cost less than R450
		if (scp.getfreeDeliveryElements() >= 1) {			
			Reporter.log("\"" + scp.getFreeDeliveryAlert() + "\"" + " is displayed on page");
			
			// THEN alert to add more items to qualify for free delivery is displayed
			String expectedMessage = "Spend R450 or more to get FREE DELIVERY. T&Cs apply";
			String actualMessage = scp.getFreeDeliveryAlert();
			Assert.assertEquals(actualMessage, expectedMessage);
			
			// WHEN I add more items and cart value exceeds R450
		} else if (scp.getfreeDeliveryElements() == 0) {

			// THEN free delivery alert disappears
			Reporter.log("The FREE DELIVERY alert message has disappeared on page");
			int expectedResult = 0;
			int actualResult = scp.getfreeDeliveryElements();			
			Assert.assertEquals(actualResult, expectedResult);
		}
	}

	@AfterSuite
	public void closeBrowser() {
		hp.closeBrowserSession();
	}
}
