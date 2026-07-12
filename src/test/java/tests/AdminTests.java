package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class AdminTests extends BaseTest {
    private AdminPage adminPage;

    @Test( priority = 1, description = "Verify Admin page is accessable")
    public void testAdminPageAccess() {
        ExtentReportManager.createTest("Admin Page Access Test", "Verify user can access the admin page ");
        //login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        //Navigate to admin page
        AdminPage adminpage= dashboardPage.navigateToAdmin();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to admin page");

        //verify admin page is dispalyed
        Assert.assertTrue(adminpage.isAdminPageDisplayed(), "Admin page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Admin page dispalyed successfully");

        String adminTitle= adminpage.getAdminTitle();
        Assert.assertEquals(adminTitle,"Admin", "Admin Title should be match");
        ExtentReportManager.getTest().log(Status.PASS, " Test completed successfully");

    }


    @Test(priority = 2, description = "Verify Adding a new employee user")
    public void testAddEmployee() {
        ExtentReportManager.createTest("Add employee Test", "Verify admin can add a new employee user ");
        //login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        //Navigate to admin page
        AdminPage adminpage= dashboardPage.navigateToAdmin();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to admin page");

        //verify admin page is dispalyed
        Assert.assertTrue(adminpage.isAdminPageDisplayed(), "Admin page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Admin page dispalyed successfully");

        String adminTitle= adminpage.getAdminTitle();
        Assert.assertEquals(adminTitle,"Admin", "Admin Title should be match");
        ExtentReportManager.getTest().log(Status.PASS, " Test completed successfully");

        //Add new employee
        String newUsername = "Ashwiniinfy" + System.currentTimeMillis();
        String newPassword = "Ashwini@123456" ;
        adminpage.addEmployee("ESS","P","Enabled",newUsername,newPassword);
        ExtentReportManager.getTest().log(Status.PASS, " Added new employee user:" + newUsername);

        Assert.assertTrue(adminpage.isUserTableDisplayed(), "User table  should be displayed after adding a employee");
        ExtentReportManager.getTest().log(Status.PASS, "Employee added successfully");


    }

}
