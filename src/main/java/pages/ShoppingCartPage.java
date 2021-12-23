package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import framework.BasePage;
import java.util.List;

public class ShoppingCartPage extends BasePage{
	
	// method to select quantity from drop-down
	public void selectQuantityFromDropDown(String quantity) {
		selectDropDown(By.id("cart-item_undefined"),quantity);
	}
    
	// returns total price displayed on the cart summary
    public double getDisplayedTotalPrice() {
		String priceText = getElementText(By.xpath("//span[@class='currency plus currency-module_currency_29IIm']"));
		String formattedPriceText = priceText.substring(2).replace(",", "");
		double price = Double.parseDouble(formattedPriceText);
		return price;
    }
	
    // method to return free-delivery element
	public WebElement getFreeDeliveryElement() {
		WebElement element = getElement(By.xpath("//body/div[1]/div[4]/div[2]/section/div[2]/div[2]/div/div[1]/div[2]/div/div/div/p"));
		return element;
	}
	
	// method to return free-delivery element text
	public String getFreeDeliveryAlert() {
		String alert = getElementText(By.xpath("//body/div[1]/div[4]/div[2]/section/div[2]/div[2]/div/div[1]/div[2]/div/div/div/p"));
		return alert;
	}
	
	// method to return number of elements 
	public int getfreeDeliveryElements() {
		List<WebElement> dynamicElement = driver.findElements(By.xpath("//body/div[1]/div[4]/div[2]/section/div[2]/div[2]/div/div[1]/div[2]/div/div/div/p"));
		return dynamicElement.size();
	}
	
	// method to remove items from cart
	public void clearShoppingCart() throws Exception {	
		List<WebElement> removeButtonElements = driver.findElements(By.cssSelector("button[class='button clear remove-item']"));
		int items = removeButtonElements.size();	
		for (int i = items; i >= 0; i--) {
			Thread.sleep(1000);
			if (i == 0) {
				break;
			}
		Thread.sleep(1000);
		clickElement(By.cssSelector("button[class='button clear remove-item']"));	
			}
		}
}
