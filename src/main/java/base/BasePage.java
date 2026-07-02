package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.LoggerUtils;

import java.time.Duration;

/**
 * Base Page class with common web element operations
 * All Page Object classes should extend this class
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 
            Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout"))));
        PageFactory.initElements(driver, this);
    }

    
    /**
     * Wait for element to be visible
     */
    protected void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable
     */
    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Click on element
     */
    protected void click(WebElement element, String elementName) {
        waitForElementClickable(element);
        element.click();
        LoggerUtils.info("Clicked on: " + elementName);
    }
    
    /**
     * Type text into element
     */
    protected void type(WebElement element, String text, String elementName) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
        LoggerUtils.info("Typed '" + text + "' into: " + elementName);
    }
    
    /**
     * Get text from element
     */
    protected String getText(WebElement element, String elementName) {
        waitForElementVisible(element);
        String text = element.getText();
        LoggerUtils.info("Got text '" + text + "' from: " + elementName);
        return text;
    }
    
    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(WebElement element, String elementName) {
        try {
            waitForElementVisible(element);
            boolean displayed = element.isDisplayed();
            LoggerUtils.info(elementName + " is displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            LoggerUtils.error(elementName + " is not displayed");
            return false;
        }
    }
    
    /**
     * Get page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        LoggerUtils.info("Page title: " + title);
        return title;
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        LoggerUtils.info("Current URL: " + url);
        return url;
    }
}
