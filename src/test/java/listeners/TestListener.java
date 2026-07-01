package listeners;

import base.DriverFactory;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportManager;
import utils.LoggerUtils;
import utils.ScreenshotUtils;

/**
 * TestNG Listener for handling test events and reporting
 */
public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtils.info("========================================");
        LoggerUtils.info("Test Started: " + result.getMethod().getMethodName());
        LoggerUtils.info("========================================");
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerUtils.info("Test PASSED: " + result.getMethod().getMethodName());
        
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(Status.PASS, 
                "Test PASSED: " + result.getMethod().getMethodName());
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        LoggerUtils.error("Test FAILED: " + result.getMethod().getMethodName());
        LoggerUtils.error("Reason: " + result.getThrowable().getMessage());
        
        // Capture screenshot on failure
        if (DriverFactory.getDriver() != null) {
            String screenshotPath = ScreenshotUtils.captureScreenshot(
                DriverFactory.getDriver(), 
                result.getMethod().getMethodName()
            );
            
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().log(Status.FAIL, 
                    "Test FAILED: " + result.getMethod().getMethodName());
                ExtentReportManager.getTest().log(Status.FAIL, 
                    "Reason: " + result.getThrowable().getMessage());
                
                if (screenshotPath != null) {
                    try {
                        ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
                    } catch (Exception e) {
                        LoggerUtils.error("Failed to attach screenshot to report: " + e.getMessage());
                    }
                }
            }
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerUtils.warn("Test SKIPPED: " + result.getMethod().getMethodName());
        
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(Status.SKIP, 
                "Test SKIPPED: " + result.getMethod().getMethodName());
        }
    }
    
    @Override
    public void onStart(ITestContext context) {
        LoggerUtils.info("========================================");
        LoggerUtils.info("Test Suite Started: " + context.getName());
        LoggerUtils.info("========================================");
    }
    
    @Override
    public void onFinish(ITestContext context) {
        LoggerUtils.info("========================================");
        LoggerUtils.info("Test Suite Finished: " + context.getName());
        LoggerUtils.info("Total Tests: " + context.getAllTestMethods().length);
        LoggerUtils.info("Passed: " + context.getPassedTests().size());
        LoggerUtils.info("Failed: " + context.getFailedTests().size());
        LoggerUtils.info("Skipped: " + context.getSkippedTests().size());
        LoggerUtils.info("========================================");
        
        ExtentReportManager.removeTest();
    }
}
