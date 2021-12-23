package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import framework.BasePage;

public class DailyDealsPage extends BasePage {

	public void ScrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,700)");
	}
	
	public void searchBrand(String brand) {
	    enterText(By.xpath("//input[@id='_undefined' and @placeholder='Search by Brand']"), brand);
	}

	public void selectBrand() {
		clickElement(By.cssSelector(".checkbox"));
	}

	public void ScrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-250)");
	}
	
	public int getQuantity() {
		//String qty = driver.findElement(By.cssSelector(".search-count")).getText();
		String qty = driver.findElement(By.xpath("//body/div[1]/div[6]/div/div[2]/div[1]/div[2]/div[2]/div")).getText();
		//String qty = driver.findElement(By.xpath("//input[@value='28 deals']")).getAttribute("value");
		int quantity = Integer.parseInt(qty.substring(0, qty.indexOf(" ")));		
		return quantity;		
	}
	
	public void selectItem() {
		clickElement(By.cssSelector("a.button:nth-child(2)"));
	}
	
	public void addItemToCard() {
		clickElement(By.cssSelector(".drawer-title > span:nth-child(1) > span:nth-child(1)"));
	}
}
