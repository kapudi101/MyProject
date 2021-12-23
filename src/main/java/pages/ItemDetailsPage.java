package pages;

import org.openqa.selenium.By;

import framework.BasePage;

public class ItemDetailsPage extends BasePage{
	
	// method to click "Add to Card" button
	public void addItemToCart() {
		switchToTab();
		clickElement(By.xpath("//body/div[1]/div[4]/div[1]/div[2]/aside/div[1]/div[2]/div/div/div[2]/a"));
	}

	// method to grab "Added to cart" text on pop-up page
    public String getTextOnPage() {
        return getElementText(By.xpath("//span[contains(text(),'Added to cart')]"));
    }
    
    // method to capture item price
    public double getItemPrice() {    	
		String priceText = getElementText(By.xpath("//span[@class='currency plus currency-module_currency_29IIm']"));
		String formattedPriceText = priceText.substring(2).replace(",", "");
		double price = Double.parseDouble(formattedPriceText);
		return price;	
    }
    
    // method to click "Go to Cart" button
    public void clickGoToCart() {
    	clickElement(By.xpath("//button[@class='button checkout-now dark']"));
    }	
}
