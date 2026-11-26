package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigReader;
import utils.LoggerUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * Factory class for WebDriver initialization
 * Supports Chrome, Firefox, and Edge browsers
 * Runs locally by default, or on Selenium Grid with -Dgrid=true
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

        // NEW: run on Selenium Grid only when -Dgrid=true is given
        boolean useGrid = Boolean.parseBoolean(System.getProperty("grid", "false"));
        String gridUrl = System.getProperty("gridUrl", "http://localhost:4444");

        LoggerUtils.info("Initializing " + browser + " driver"
                + (useGrid ? " on Selenium Grid: " + gridUrl : " locally"));

        switch (browser.toLowerCase()) {
            case "chrome":
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

                if (useGrid) {
                    driver.set(createRemoteDriver(gridUrl, chromeOptions));
                } else {
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(chromeOptions));
                }
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }

                if (useGrid) {
                    driver.set(createRemoteDriver(gridUrl, firefoxOptions));
                } else {
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver(firefoxOptions));
                }
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }

                if (useGrid) {
                    driver.set(createRemoteDriver(gridUrl, edgeOptions));
                } else {
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver(edgeOptions));
                }
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
     * NEW: Create a browser session on Selenium Grid
     * @param gridUrl address of the Grid hub, for example http://localhost:4444
     * @param options browser options (Chrome, Firefox or Edge)
     * @return RemoteWebDriver connected to the Grid
     */
    private static WebDriver createRemoteDriver(String gridUrl, Capabilities options) {
        try {
            return new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Grid URL: " + gridUrl, e);
        }
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