package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;
import utils.LoggerUtils;

import java.time.Duration;

/**
 * Factory class for WebDriver initialization
 * Supports Chrome, Firefox, and Edge browsers
 */
public class DriverFactory {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver based on browser from config
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        String browser = ConfigReader.getProperty("browser");
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        
        LoggerUtils.info("Initializing " + browser + " driver");
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver.set(new ChromeDriver(chromeOptions));
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver.set(new EdgeDriver(edgeOptions));
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        getDriver().manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout")))
        );
        getDriver().manage().window().maximize();
        
        LoggerUtils.info(browser + " driver initialized successfully");
        return getDriver();
    }
    
    /**
     * Get the current WebDriver instance
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    /**
     * Quit the WebDriver and clean up
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            LoggerUtils.info("Quitting driver");
            driver.get().quit();
            driver.remove();
        }
    }
}
