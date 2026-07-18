package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.LoggerUtils;

/**
 * Page Object class for PIM (Personnel Information Management) Page
 */
public class PIMPage extends BasePage {
    
    // Page Elements
    @FindBy(xpath = "//h6[contains(@class, 'oxd-topbar-header-breadcrumb-module')]")
    private WebElement pimHeader;
    
    @FindBy(xpath = "//button[contains(@class, 'oxd-button--secondary') and contains(., 'Add')]")
    private WebElement addEmployeeButton;
    
    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement employeeNameSearchField;
    
    @FindBy(xpath = "//form//button[@type='submit']")
    private WebElement searchButton;
    
    @FindBy(className = "oxd-table-card")
    private WebElement employeeTable;
    
    @FindBy(name = "firstName")
    private WebElement firstNameField;
    
    @FindBy(name = "middleName")
    private WebElement middleNameField;
    
    @FindBy(name = "lastName")
    private WebElement lastNameField;
    
    @FindBy(xpath = "//form//button[@type='submit' and contains(., 'Save')]")
    private WebElement saveButton;
    
    @FindBy(className = "oxd-toast-content-text")
    private WebElement successMessage;
    
    // Constructor
    public PIMPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Verify PIM page is displayed
     */
    public boolean isPIMPageDisplayed() {
        return isDisplayed(pimHeader, "PIM header");
    }
    
    /**
     * Get PIM page title
     */
    public String getPIMTitle() {
        return getText(pimHeader, "PIM header");
    }
    
    /**
     * Click Add Employee button
     */
    public void clickAddEmployee() {
        click(addEmployeeButton, "Add Employee button");
    }
    
    /**
     * Add new employee
     */
    public void addEmployee(String firstName, String middleName, String lastName) {
        LoggerUtils.info("Adding employee: " + firstName + " " + middleName + " " + lastName);
        clickAddEmployee();
        type(firstNameField, firstName, "First Name field");
        waitForSeconds(1);
        type(middleNameField, middleName, "Middle Name field");
        waitForSeconds(1);
        type(lastNameField, lastName, "Last Name field");
        waitForSeconds(1);
        click(saveButton, "Save button");
    }
    
    /**
     * Search employee by name
     */
    public void searchEmployee(String employeeName) {
        LoggerUtils.info("Searching for employee: " + employeeName);
        type(employeeNameSearchField, employeeName, "Employee name search field");
        click(searchButton, "Search button");
    }

    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        try {
            waitForElementVisible(successMessage);
            return getText(successMessage, "Success message");
        } catch (Exception e) {
            LoggerUtils.warn("Success message not found: " + e.getMessage());
            return "";
        }
    }
}
