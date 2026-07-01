package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.LoggerUtils;

/**
 * Base Test class with common setup and teardown methods
 * All test classes should extend this class
 */
public class BaseTest {
    
    protected WebDriver driver;
    
    @BeforeSuite
    public void suiteSetup() {
        LoggerUtils.info("===== Test Suite Started =====");
        ExtentReportManager.initReport();
        ConfigReader.loadProperties();
    }
    
    @BeforeMethod
    public void setUp() {
        LoggerUtils.info("Setting up test");
        driver = DriverFactory.initializeDriver();
        driver.get(ConfigReader.getProperty("url"));
        LoggerUtils.info("Navigated to: " + ConfigReader.getProperty("url"));
    }
    
    @AfterMethod
    public void tearDown() {
        LoggerUtils.info("Tearing down test");
        DriverFactory.quitDriver();
    }
    
    @AfterSuite
    public void suiteTearDown() {
        LoggerUtils.info("===== Test Suite Completed =====");
        ExtentReportManager.flushReport();
    }
}
