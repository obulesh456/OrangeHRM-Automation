package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ADDLOCATIONPAGE;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class AddLocationTest extends BaseTest {

    @Test(priority = 1, description = "Verify Adding a new location")
    public void testAddLocation() {
        ExtentReportManager.createTest("Add Location Test", "Verify admin can add a new location");

        // 1. Login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        // 2. Open Add Location form, fill details, and save
        ADDLOCATIONPAGE addLocationPage = new ADDLOCATIONPAGE(driver);
        addLocationPage.openAddLocationForm();
        ExtentReportManager.getTest().log(Status.INFO, "Opened Add Location form");

        String locationName = "Hyderabad Branch " + System.currentTimeMillis();
        addLocationPage.addLocation(
                locationName,
                "Hyderabad",
                "Telangana",
                "500081",
                "India",
                "0401234567",
                "0407654321",
                "Gachibowli, Hyderabad",
                "Main office location"
        );
        ExtentReportManager.getTest().log(Status.INFO, "Submitted location details: " + locationName);

        // 3. Real assertions after Save
        Assert.assertTrue(addLocationPage.isSuccessToastDisplayed(), "Success toast should be displayed after saving location");
        Assert.assertTrue(addLocationPage.getSuccessToastMessage().contains("Successfully Saved"), "Toast message should say Successfully Saved");
        Assert.assertTrue(addLocationPage.isLocationPresentInPage(locationName), "Saved location name should be visible in Locations list");
        ExtentReportManager.getTest().log(Status.PASS, "Verified toast and location in list: " + locationName);
    }
}