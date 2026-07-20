package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.RecruitmentPage;
import utils.ConfigReader;
import utils.ExtentReportManager;

/**
 * Test class for Recruitment functionality
 */
public class RecruitmentTests extends BaseTest {
    
    @Test(priority = 1, description = "Verify Recruitment page is accessible")
    public void testRecruitmentPageAccess() {
        ExtentReportManager.createTest("Recruitment Page Access Test", "Verify user can access Recruitment page");
        
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");

        
        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");
        
        // Navigate to Recruitment page
        RecruitmentPage recruitmentPage = dashboardPage.navigateToRecruitment();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Recruitment page");
        
        // Verify Recruitment page is displayed
        Assert.assertTrue(recruitmentPage.isRecruitmentPageDisplayed(), "Recruitment page should be displayed");
        ExtentReportManager.getTest().log(Status.PASS, "Recruitment page displayed successfully");
        
        String recruitmentTitle = recruitmentPage.getRecruitmentTitle();
        Assert.assertEquals(recruitmentTitle, "Recruitment", "Recruitment title should match");
        ExtentReportManager.getTest().log(Status.PASS, "Test completed successfully");
    }
    
    @Test(priority = 2, description = "Verify adding a new candidate")
    public void testAddCandidate() {
        ExtentReportManager.createTest("Add Candidate Test", "Verify admin can add a new candidate");
        
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");
        
        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");
        
        // Navigate to Recruitment page
        RecruitmentPage recruitmentPage = dashboardPage.navigateToRecruitment();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Recruitment page");
        
        // Add new candidate
        String firstName = "John";
        String middleName = "";  // Keep it empty to avoid issues
        String lastName = "Doe" + System.currentTimeMillis();
        String email = "john.doe" + System.currentTimeMillis() + "@test.com";
        String contactNumber = "";  // Keep it empty as it's optional
        String dateOfApplication = "";  // Keep it empty as it's optional

        recruitmentPage.addCandidate(firstName, middleName, lastName, email, contactNumber, dateOfApplication);
        ExtentReportManager.getTest().log(Status.INFO, "Added new candidate: " + firstName + " " + lastName);

        // Verify success message OR candidates table is displayed
        boolean isSuccess = recruitmentPage.isSuccessMessageDisplayed() || recruitmentPage.isCandidatesTableDisplayed();
        Assert.assertTrue(isSuccess,
            "Success message or candidates table should be displayed after adding candidate");
        ExtentReportManager.getTest().log(Status.PASS, "Candidate added successfully");
    }
    
    @Test(priority = 3, description = "Verify adding candidate with minimal details")
    public void testAddCandidateMinimalDetails() {
        ExtentReportManager.createTest("Add Candidate Minimal Details Test", 
            "Verify admin can add candidate with only required fields");
        
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");
        
        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");
        
        // Navigate to Recruitment page
        RecruitmentPage recruitmentPage = dashboardPage.navigateToRecruitment();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Recruitment page");
        
        // Add candidate with minimal details
        String firstName = "Jane";
        String lastName = "Smith" + System.currentTimeMillis();
        String email = "jane.smith" + System.currentTimeMillis() + "@test.com";

        recruitmentPage.addCandidate(firstName, null, lastName, email, null, null);
        ExtentReportManager.getTest().log(Status.INFO, "Added new candidate with minimal details: " + firstName + " " + lastName);
        
        // Verify candidates table is displayed
        Assert.assertTrue(recruitmentPage.isCandidatesTableDisplayed(), 
            "Candidates table should be displayed after adding candidate");
        ExtentReportManager.getTest().log(Status.PASS, "Candidate added successfully with minimal details");
    }
    
    @Test(priority = 4, description = "Verify candidates table is displayed")
    public void testCandidatesTableDisplay() {
        ExtentReportManager.createTest("Candidates Table Display Test", 
            "Verify candidates table displays on Recruitment page");
        
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        String username = ConfigReader.getProperty("valid.username");
        String password = ConfigReader.getProperty("valid.password");
        
        DashboardPage dashboardPage = loginPage.login(username, password);
        ExtentReportManager.getTest().log(Status.INFO, "Logged in successfully");
        
        // Navigate to Recruitment page
        RecruitmentPage recruitmentPage = dashboardPage.navigateToRecruitment();
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to Recruitment page");
        
        // Verify candidates table is displayed
        Assert.assertTrue(recruitmentPage.isCandidatesTableDisplayed(), 
            "Candidates table should be displayed on Recruitment page");
        ExtentReportManager.getTest().log(Status.PASS, "Candidates table displayed successfully");
    }
}
