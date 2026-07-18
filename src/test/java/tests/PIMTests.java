package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class PIMTests extends BaseTest {

    @Test(enabled = false, priority = 1, description = "Verify Admin page is accessable")
    public void testPIMPageAccess() {
        ExtentReportManager.createTest("PIM Page Access Test", "Verify employee can access the PIM page ");
        //login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        //Navigate to PIM page
        PIMPage pimPage = dashboardPage.navigateToPIM();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to PIM page");

        //verify PIM page is dispalyed
        Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "PIM page dispalyed successfully");

        String pimTitle = pimPage.getPIMTitle();
        Assert.assertEquals(pimTitle, "PIM", "PIM Title should be match");
        ExtentReportManager.getTest().log(Status.PASS, " Test completed successfully");

    }

    @Test(priority = 2, description = "Verify Adding a new employee in PIM")
    public void testAddPIMEmployee() {
        ExtentReportManager.createTest("Add PIM employee Test", "Verify PIM can add a new employee user ");
        //login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        //Navigate to PIM page
        PIMPage pimPage = dashboardPage.navigateToPIM();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to PIM page");

        //verify PIM page is displayed
        Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "PIM page displayed successfully");

        //Add new employee
        pimPage.addEmployee("Ashwini", "galam", "obulesh");
        ExtentReportManager.getTest().log(Status.INFO, "Added employee successfully");
        
        //Verify success message
        String successMsg = pimPage.getSuccessMessage();
        ExtentReportManager.getTest().log(Status.INFO, "Success message: " + successMsg);
        Assert.assertNotNull(successMsg, "Success message should be displayed after adding employee");
        Assert.assertTrue(!successMsg.isEmpty(), "Success message should not be empty");
        ExtentReportManager.getTest().log(Status.PASS, "Employee added and verified successfully");


    }


}
