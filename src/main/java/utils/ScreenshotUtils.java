package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for taking screenshots
 */
public class ScreenshotUtils {
    
    private static final String SCREENSHOT_DIR = "screenshots/";
    
    /**
     * Capture screenshot and save to file
     * @param driver WebDriver instance
     * @param testName Test name for the screenshot
     * @return screenshot file path
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + fileName;
        
        try {
            // Create screenshots directory if not exists
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            FileUtils.copyFile(source, destination);
            
            LoggerUtils.info("Screenshot captured: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            LoggerUtils.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Capture screenshot with custom path
     * @param driver WebDriver instance
     * @param filePath Custom file path
     * @return screenshot file path
     */
    public static String captureScreenshot(WebDriver driver, String filePath, boolean customPath) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            
            // Create parent directory if not exists
            File parentDir = destination.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            FileUtils.copyFile(source, destination);
            LoggerUtils.info("Screenshot captured: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            LoggerUtils.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
