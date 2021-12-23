package framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	public static WebDriver driver;
	
	String baseURL = getDataConfigProperties("SUT");

	// constructor of the base class (manage browsers)
	public BasePage() {
		if (driver == null) {
			// get parameter values
			String driverDirectory = getDataConfigProperties("driverDir");
			String browserName = getDataConfigProperties("browser");

			if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", driverDirectory + "geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(90,TimeUnit.SECONDS);
				driver.get(baseURL);
				driver.manage().deleteAllCookies();

			} else if (browserName.equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver", driverDirectory + "msedgedriver.exe");
				driver = new EdgeDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(90,TimeUnit.SECONDS);
				driver.get(baseURL);
				driver.manage().deleteAllCookies();
			}
		}		
	}
	
	public void goToHomePage() {	
		driver.get(baseURL);
	}

	// method to read the config file
	public String getDataConfigProperties(String propertyKey) {
		// create properties object
		Properties properties = new Properties();
		// create InputStream object
		InputStream iS = null;
		// open config file using InputStream object
		try {
			iS = new FileInputStream("config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// load config file data using properties object
		try {
			properties.load(iS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return property key/value to the method
		return properties.getProperty(propertyKey);
	}

	  //create a method to Wait for Element
    public void waitForElement(int elementWait, By pLocator) {
        WebDriverWait wait = new WebDriverWait(driver, elementWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(pLocator));
    }

    //create a method to wait for the URL
    public void waitForUrl(int elementWait, String pLocator) {
        WebDriverWait wait = new WebDriverWait(driver, elementWait);
        wait.until(ExpectedConditions.urlContains(pLocator));
    }

    //create a method to wait for element to be clickable
    public void waitForClick(int elementWait, By pLocator) {
        WebDriverWait wait = new WebDriverWait(driver, elementWait);
        wait.until(ExpectedConditions.elementToBeClickable(pLocator));
    }

    //create a method to get Element
    public WebElement getElement(By pLocator) {
        waitForClick(30, pLocator);
        return driver.findElement(pLocator);
    }

    //create a method to get Element Text
    public String getElementText(By pLocator) {
        waitForElement(30, pLocator);
        String headerText = getElement(pLocator).getText();
        return headerText;
    }

    //create a method to click Element
    public void clickElement(By pLocator) {
        waitForClick(60, pLocator);
        getElement(pLocator).click();
    }

    //create a method to EnterText
    public void enterText(By pLocator, String pText) {
        waitForClick(30, pLocator);
        driver.findElement(pLocator).sendKeys(pText);
    }

    // create a method to select from dropdown
    public void selectDropDown(By pLocator, String pValue) {
        Select sDropDown = new Select(getElement(pLocator));
        sDropDown.selectByVisibleText(pValue);
    } 
	
	public void switchToTab() {
		for(String window : driver.getWindowHandles()) {
	        driver.switchTo().window(window);
		}
	}
    
    public void closesNewTabAndSwitchBackParentTab() {
    	Set<String> handles = driver.getWindowHandles();
    	Iterator<String> iterator = handles.iterator();
    	String parentWindowHandle = iterator.next();
    	String childWindowHandle = iterator.next();
    	driver.switchTo().window(childWindowHandle);
    	driver.close();
    	driver.switchTo().window(parentWindowHandle);
    }
	
	// method to delete item from cart by clicking on the Remove button, if it exists
	public void removeItemFromCart() {
		WebElement removeButton = getElement(By.xpath("//button[@class='button clear remove-item']"));
		try {
			if (removeButton.isDisplayed()) {
		clickElement(By.xpath("//button[@class='button clear remove-item']"));
			}
		} catch(Exception e) {
			System.out.println("Exception :: " + e.getMessage ());
			throw new RuntimeException("Cart has no item to delete");
			
		} finally{
			
		}	
}
	
	public void closeCurrentTab() {
		driver.close();
	}
	
	public void closeBrowserSession() {
        if(driver != null){
            driver.quit();
        }
	}
}
