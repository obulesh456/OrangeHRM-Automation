package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

/**
 * Test class for Login functionality
 */
public class LoginTests extends BaseTest {
    
    @Test(priority = 1, description = "Verify login with valid credentials")
    public void testValidLogin() {
        ExtentReportManager.createTest("Valid Login Test", "Test login with valid username and password");
        
        LoginPage loginPage = new LoginPage(driver);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to login page");
        
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Login page displayed successfully");
        
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");
        
        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials and clicked login");
        
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should be displayed after login");
        ExtentReportManager.getTest().log(Status.PASS, "Login successful - Dashboard displayed");
        
        String dashboardTitle = dashboardPage.getDashboardTitle();
        Assert.assertEquals(dashboardTitle, "Dashboard", "Dashboard title should match");
        ExtentReportManager.getTest().log(Status.PASS, "Test completed successfully");

        // Pause for 5 seconds to see the browser
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test(priority = 2, description = "Verify login with invalid credentials")
    public void testInvalidLogin() {
        ExtentReportManager.createTest("Invalid Login Test", "Test login with invalid credentials");
        
        LoginPage loginPage = new LoginPage(driver);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to login page");
        
        loginPage.login("invalidUser", "invalidPass");
        ExtentReportManager.getTest().log(Status.INFO, "Entered invalid credentials");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Invalid credentials"), 
            "Error message should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Error message displayed correctly: " + errorMessage);
    }
    
    @Test(priority = 3, description = "Verify login with empty credentials")
    public void testEmptyCredentials() {
        ExtentReportManager.createTest("Empty Credentials Test", "Test login with empty username and password");
        
        LoginPage loginPage = new LoginPage(driver);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to login page");
        
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLogin();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked login with empty credentials");
        
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should remain on login page");
        ExtentReportManager.getTest().log(Status.PASS, "Remained on login page as expected");
    }
    
    @Test(priority = 4, description = "Verify login page elements")
    public void testLoginPageElements() {
        ExtentReportManager.createTest("Login Page Elements Test", "Verify all login page elements are present");
        
        LoginPage loginPage = new LoginPage(driver);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to login page");
        
        Assert.assertTrue(loginPage.verifyLoginPageElements(), 
            "All login page elements should be present");
        ExtentReportManager.getTest().log(Status.PASS, "All login page elements verified successfully");
    }
    
    @Test(priority = 5, description = "Verify successful logout")
    public void testLogout() {
        ExtentReportManager.createTest("Logout Test", "Test logout functionality");
        
        LoginPage loginPage = new LoginPage(driver);
        
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");
        
        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");
        
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should be displayed");
        
        loginPage = dashboardPage.logout();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked logout");
        
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be redirected to login page after logout");
        ExtentReportManager.getTest().log(Status.PASS, "Logout successful");
    }
}
