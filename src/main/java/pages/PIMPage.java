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
    @FindBy(css = "h6.oxd-topbar-header-breadcrumb-module")
    private WebElement pimHeader;
    
    @FindBy(css = "button.oxd-button--secondary")
    private WebElement addEmployeeButton;
    
    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement employeeNameSearchField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;
    
    @FindBy(className = "oxd-table-card")
    private WebElement employeeTable;
    
    @FindBy(name = "firstName")
    private WebElement firstNameField;
    
    @FindBy(name = "middleName")
    private WebElement middleNameField;
    
    @FindBy(name = "lastName")
    private WebElement lastNameField;
    
    @FindBy(css = "button[type='submit']")
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
        type(middleNameField, middleName, "Middle Name field");
        type(lastNameField, lastName, "Last Name field");
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
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        return getText(successMessage, "Success message");
    }
}
