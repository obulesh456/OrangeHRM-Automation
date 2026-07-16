package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LeavePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class AssignLeaveTests extends BaseTest {

    @Test(enabled = false, priority = 1, description = "Verify Leave page is accessable")
    public void testLeavePageAccess() {
        ExtentReportManager.createTest("Leave Page Access Test", "Verify employee can access the Leave page ");
        //login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        //Navigate to PIM page
        LeavePage leavePage = dashboardPage.navigateToLeave();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Leave page");

        //verify PIM page is dispalyed
        Assert.assertTrue(leavePage.isLeavePageDisplayed(), "Leave page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Leave page dispalyed successfully");

        String leaveTitle = leavePage.getLeaveTitle();
        Assert.assertEquals(leaveTitle, "Leave", "Leave Title should be match");
        ExtentReportManager.getTest().log(Status.PASS, " Test completed successfully");

    }

    @Test(priority = 2, description = "Verify Adding a new employee in Leave")

    public void testAddLeave() {
        ExtentReportManager.createTest("Leave Page Access Test", "Verify employee can access the Leave page ");
        //login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        //Navigate to Leave page
        LeavePage leavePage = dashboardPage.navigateToLeave();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Leave page");

        //verify Leave page is dispalyed
        Assert.assertTrue(leavePage.isLeavePageDisplayed(), "Leave page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Leave page dispalyed successfully");
        //Assign Leave
        leavePage.assignLeaveTrack("a", "CAN-bereavemnent", "16/07/2026", "17/07/2026");
        Assert.assertTrue(leavePage.isLeavePageDisplayed(), "User table  should be displayed after adding a employee");
        ExtentReportManager.getTest().log(Status.PASS, "Employee added successfully");

    }

}



