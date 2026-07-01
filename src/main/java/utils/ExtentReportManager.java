package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for managing Extent Reports
 */
public class ExtentReportManager {
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final String REPORT_DIR = "reports/";
    
    /**
     * Initialize Extent Report
     */
    public static void initReport() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = REPORT_DIR + "ExtentReport_" + timestamp + ".html";
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "OrangeHRM");
            extent.setSystemInfo("Environment", ConfigReader.getProperty("url"));
            extent.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            
            LoggerUtils.info("Extent Report initialized: " + reportPath);
        }
    }
    
    /**
     * Create a new test in the report
     * @param testName Test name
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        return test;
    }
    
    /**
     * Create a new test with description
     * @param testName Test name
     * @param description Test description
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        return test;
    }
    
    /**
     * Get current test instance
     * @return ExtentTest
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }
    
    /**
     * Flush and save the report
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            LoggerUtils.info("Extent Report flushed");
        }
    }
    
    /**
     * Remove current test from ThreadLocal
     */
    public static void removeTest() {
        extentTest.remove();
    }
}
