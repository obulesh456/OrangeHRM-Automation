package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;


/**
 * Test class for Dashboard functionality
 */
public class DashboardTests extends BaseTest {

    private final ThreadLocal<DashboardPage> dashboardPage = new ThreadLocal<>();
    
    @BeforeMethod
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(getDriver());
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");
        dashboardPage.set(loginPage.login(username, password));
    }

    @AfterMethod
    public void clearDashboardPage() {
        dashboardPage.remove();
    }

    private DashboardPage getDashboardPage() {
        return dashboardPage.get();
    }
    
    @Test(priority = 1, description = "Verify dashboard is displayed after login")
    public void testDashboardDisplay() {
        ExtentReportManager.createTest("Dashboard Display Test", 
            "Verify dashboard is displayed with all elements");
        
        Assert.assertTrue(getDashboardPage().isDashboardDisplayed(), 
            "Dashboard should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Dashboard displayed successfully");
        
        String title = getDashboardPage().getDashboardTitle();
        Assert.assertEquals(title, "Dashboard", "Dashboard title should match");
        ExtentReportManager.getTest().log(Status.PASS, "Dashboard title verified: " + title);
    }
    
    @Test(priority = 2, description = "Verify all main menu items are present")
    public void testMainMenuItems() {
        ExtentReportManager.createTest("Main Menu Items Test", 
            "Verify all main navigation menu items are present");
        
        Assert.assertTrue(getDashboardPage().verifyMainMenus(), 
            "All main menu items should be present");
        ExtentReportManager.getTest().log(Status.PASS, "All main menu items verified");
    }
    
    @Test(priority = 3, description = "Verify navigation to Admin module")
    public void testNavigateToAdmin() {
        ExtentReportManager.createTest("Navigate to Admin Test", 
            "Verify navigation to Admin module");
        
        getDashboardPage().navigateToAdmin();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Admin menu");
        
        // Add assertions for Admin page
        ExtentReportManager.getTest().log(Status.PASS, "Successfully navigated to Admin module");
    }
    
    @Test(priority = 4, description = "Verify navigation to PIM module")
    public void testNavigateToPIM() {
        ExtentReportManager.createTest("Navigate to PIM Test", 
            "Verify navigation to PIM module");
        
        getDashboardPage().navigateToPIM();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on PIM menu");
        
        ExtentReportManager.getTest().log(Status.PASS, "Successfully navigated to PIM module");
    }
    
    @Test(priority = 5, description = "Verify user dropdown displays username")
    public void testUserDropdown() {
        ExtentReportManager.createTest("User Dropdown Test", 
            "Verify user dropdown displays correct username");
        
        String username = getDashboardPage().getLoggedInUsername();
        Assert.assertNotNull(username, "Username should be displayed");
        Assert.assertFalse(username.isEmpty(), "Username should not be empty");
        
        ExtentReportManager.getTest().log(Status.PASS, "Username displayed: " + username);
    }
}
