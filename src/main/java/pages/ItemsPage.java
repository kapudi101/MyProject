package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import framework.BasePage;

public class ItemsPage extends BasePage {	

	// pass index for method to select item
	public void selectItem(int index) throws InterruptedException {
		Thread.sleep(3000);
		scrollDown();		
		List<WebElement> elements = driver.findElements(
				By.xpath("//div/div/a[@class='product-anchor product-card-module_product-anchor_TUCBV']"));		
		elements.get(index).click();
		}
	
	// use this method when we want to return calculated total price
	public double getCalculatedTotalPrice(String quantity) {
		String priceText = getElementText(By.xpath("//span[@class='currency plus currency-module_currency_29IIm']"));
		String formattedPriceText = priceText.substring(2).replace(",", "");
		double price = Double.parseDouble(formattedPriceText);
		int qty = Integer.parseInt(quantity);		
		return price * qty;
	}

	// use this method when we want to calculated total price within the test
	public double getPrice() {
		String priceText = getElementText(By.xpath("//span[@class='currency plus currency-module_currency_29IIm']"));
		String formattedPriceText = priceText.substring(2).replace(",", "");
		double price = Double.parseDouble(formattedPriceText);
		return price;
	}
	
	// method to scroll down on page
	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,150)");
		 // ((JavascriptExecutor)driver).executeScript("scroll(0,200)");
	}

}
