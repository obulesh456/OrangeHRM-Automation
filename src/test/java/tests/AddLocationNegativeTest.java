package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddLocationPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

public class AddLocationNegativeTest extends BaseTest {

    private AddLocationPage loginAndOpenAddLocationForm(String testTitle, String description) {
        ExtentReportManager.createTest(testTitle, description);

        LoginPage loginPage = new LoginPage(getDriver());
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");

        AddLocationPage addLocationPage = new AddLocationPage(getDriver());
        addLocationPage.openAddLocationForm();
        ExtentReportManager.getTest().log(Status.INFO, "Opened Add Location form");
        return addLocationPage;
    }

    // Case 1: Empty Name & Empty Country (Required validation)
    @Test(priority = 1, description = "Verify Required validation when Name and Country are empty")
    public void testEmptyRequiredFields() {
        AddLocationPage addLocationPage = loginAndOpenAddLocationForm(
                "Add Location Negative - Empty Required Fields",
                "Verify Required error for Name and Country when Save is clicked empty"
        );

        addLocationPage.clickSave();
        ExtentReportManager.getTest().log(Status.INFO, "Clicked Save with empty Name and Country");

        Assert.assertEquals(addLocationPage.getFieldErrorMessage("Name"), "Required", "Name required error mismatch");
        Assert.assertEquals(addLocationPage.getFieldErrorMessage("Country"), "Required", "Country required error mismatch");
        ExtentReportManager.getTest().log(Status.PASS, "Verified Required errors on Name and Country");
    }

    // Case 2: Duplicate Location Name
    @Test(priority = 2, description = "Verify error when duplicate location name is entered")
    public void testDuplicateLocationName() {
        AddLocationPage addLocationPage = loginAndOpenAddLocationForm(
                "Add Location Negative - Duplicate Name",
                "Verify error message when entering an already existing location name"
        );

        addLocationPage.enterName("Texas R&D");
        addLocationPage.selectCountry("United States");
        addLocationPage.clickSave();
        ExtentReportManager.getTest().log(Status.INFO, "Submitted duplicate location name: Texas R&D");

        Assert.assertEquals(addLocationPage.getFieldErrorMessage("Name"), "Already exists", "Duplicate name error mismatch");
        ExtentReportManager.getTest().log(Status.PASS, "Verified Already exists error on Name field");
    }

    // Cases 3 & 4: Invalid Phone and Fax format
    @DataProvider(name = "invalidPhoneFaxData")
    public Object[][] invalidPhoneFaxData() {
        return new Object[][]{
                {"Phone", "invalid_phone_abc"},
                {"Fax", "invalid_fax_xyz"}
        };
    }

    @Test(priority = 3, dataProvider = "invalidPhoneFaxData", description = "Verify invalid format error for Phone and Fax")
    public void testInvalidPhoneAndFax(String fieldLabel, String invalidValue) {
        AddLocationPage addLocationPage = loginAndOpenAddLocationForm(
                "Add Location Negative - Invalid " + fieldLabel,
                "Verify format validation on " + fieldLabel + " field"
        );

        addLocationPage.enterName("Hyderabad Test " + fieldLabel);
        addLocationPage.selectCountry("India");

        if ("Phone".equals(fieldLabel)) {
            addLocationPage.enterPhone(invalidValue);
        } else {
            addLocationPage.enterFax(invalidValue);
        }

        addLocationPage.clickSave();
        ExtentReportManager.getTest().log(Status.INFO, "Entered invalid " + fieldLabel + ": " + invalidValue);

        Assert.assertEquals(
                addLocationPage.getFieldErrorMessage(fieldLabel),
                "Allows numbers and only + - / ( )",
                fieldLabel + " format error mismatch"
        );
        ExtentReportManager.getTest().log(Status.PASS, "Verified format validation error for " + fieldLabel);
    }

    // Cases 5 to 10: Max character limit validation across Name, City, State, Zip, Address, Notes
    @DataProvider(name = "maxLengthData")
    public Object[][] maxLengthData() {
        return new Object[][]{
                {"Name", "A".repeat(105), "Should not exceed 100 characters"},
                {"City", "C".repeat(55), "Should not exceed 50 characters"},
                {"State/Province", "S".repeat(55), "Should not exceed 50 characters"},
                {"Zip/Postal Code", "9".repeat(35), "Should not exceed 30 characters"},
                {"Address", "D".repeat(255), "Should not exceed 250 characters"},
                {"Notes", "N".repeat(255), "Should not exceed 250 characters"}
        };
    }

    @Test(priority = 4, dataProvider = "maxLengthData", description = "Verify max length validation across Add Location fields")
    public void testMaxLengthFields(String fieldLabel, String overLimitText, String expectedError) {
        AddLocationPage addLocationPage = loginAndOpenAddLocationForm(
                "Add Location Negative - Max Length (" + fieldLabel + ")",
                "Verify character limit error for " + fieldLabel
        );

        addLocationPage.enterName("Hyderabad Valid Base");
        addLocationPage.selectCountry("India");

        switch (fieldLabel) {
            case "Name":
                addLocationPage.enterName(overLimitText);
                break;
            case "City":
                addLocationPage.enterCity(overLimitText);
                break;
            case "State/Province":
                addLocationPage.enterState(overLimitText);
                break;
            case "Zip/Postal Code":
                addLocationPage.enterZip(overLimitText);
                break;
            case "Address":
                addLocationPage.enterAddress(overLimitText);
                break;
            case "Notes":
                addLocationPage.enterNotes(overLimitText);
                break;
            default:
                break;
        }

        addLocationPage.clickSave();
        ExtentReportManager.getTest().log(Status.INFO, "Submitted over-limit value for " + fieldLabel);

        Assert.assertEquals(addLocationPage.getFieldErrorMessage(fieldLabel), expectedError, fieldLabel + " length error mismatch");
        ExtentReportManager.getTest().log(Status.PASS, "Verified max length error for " + fieldLabel);
    }
}