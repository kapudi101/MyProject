package pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import framework.BasePage;

public class HomePage extends BasePage {
	
	Actions action = new Actions(driver);
	
	// method to clear global search field
	public void clearSearchInputField() {
		WebElement searchField = driver.findElement(By.xpath("//input[@name='search' and @class='search-field ']"));
		searchField.clear();
	}

	// method to enter brand on the global search field
	public void enterBrandToSearch(String brand) {
		enterText(By.xpath("//input[@name='search' and @class='search-field ']"), brand);
	}
	
	// method to press enter key
	public void pressEnterKey() {		
		action.sendKeys(Keys.ENTER).perform();
	}

	// method to click the search icon on the global search field
	public void clickSearchIcon() {
		clickElement(By.xpath("//body/div[1]/header/div/div/div[2]/form/div/div[3]/button"));
	}
	
	// method to read the quantity of items/deals per brand
	public int getItemsQuantity() {
		String qty = driver.findElement(By.cssSelector(".search-count")).getText();
		int quantity = Integer.parseInt(qty.substring(0, qty.indexOf(" ")));		
		return quantity;
	}
	
	// deletes item from cart by first hovering mouse over the cart icon
	public void clearCart() {
		WebElement cart = driver.findElement(By.xpath("//button[@class='button badge-button mini-cart-button dark-green  badge-button-module_badge-button_3TXVp badge-button-module_badge-icon_LvKrF badge-button-module_badge-count_28PIS']"));
		action.moveToElement(cart).perform();
		clickElement(By.xpath("//button[@class='button cart-item-module_remove-item_3fJOK']"));
	}
	
	// method to accept cookies and close the banner
	public void closeCookiesBanner() {
		WebElement cookiesBanner = driver.findElement(By.xpath("//button[@class='button cookies-banner-module_dismiss-button_24Z98']"));
		try {
			if(cookiesBanner.isDisplayed()) {
			clickElement(By.xpath("//button[@class='button cookies-banner-module_dismiss-button_24Z98']"));
			}
		} catch(NoSuchElementException e) {
			throw new RuntimeException("Coockies banner not displayed");
		}	
	}	
	
	// method to minimise survey icon if popped up
	public void minimiseSurveyIcon() {
		WebElement surveyIcon = getElement(By.xpath("//span[@class='_hj-102w7__styles__openStateToggleIcon _hj-3Iftt__styles__surveyIcons']"));
		try {
			if(surveyIcon.isDisplayed()) {
				clickElement(
						By.xpath("//span[@class='_hj-102w7__styles__openStateToggleIcon _hj-3Iftt__styles__surveyIcons']"));
			}
		} catch(NoSuchElementException e) {
			throw new RuntimeException("Survey icon not popped up");
		}	
	}
}
