package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddLocationPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class AddLocationTest extends BaseTest {

    @Test(description = "Verify Admin can add a new location successfully")
    public void testAddLocation() {
        ExtentReportManager.createTest(
                "Add Location Positive - Create New Location",
                "Verify Admin logs in, opens Add Location form, fills valid details, saves, and sees confirmation"
        );

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        // 2. Open Add Location form, fill details, and save
        AddLocationPage addLocationPage = new AddLocationPage(driver);
        addLocationPage.openAddLocationForm();
        ExtentReportManager.getTest().log(Status.INFO, "Opened Add Location form");

        String locationName = "Hyderabad Branch " + System.currentTimeMillis();
        addLocationPage.addLocation(
                locationName,
                "Hyderabad",
                "Telangana",
                "500001",
                "India",
                "+91-40-12345678",
                "+91-40-87654321",
                "Hitech City Road, Madhapur",
                "Automated test location"
        );
        ExtentReportManager.getTest().log(Status.INFO, "Submitted location details for: " + locationName);

        // 3. Verify success toast and location in table
        Assert.assertTrue(addLocationPage.isSuccessToastDisplayed(), "Success toast was not displayed");
        Assert.assertTrue(
                addLocationPage.getSuccessToastMessage().toLowerCase().contains("successfully saved"),
                "Unexpected toast message: " + addLocationPage.getSuccessToastMessage()
        );
        Assert.assertTrue(addLocationPage.isLocationPresentInPage(locationName), "Created location not found in table");
        ExtentReportManager.getTest().log(Status.PASS, "Verified location saved and listed: " + locationName);
    }
}