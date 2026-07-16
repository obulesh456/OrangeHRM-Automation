package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object class for Recruitment Page
 */
public class RecruitmentPage extends BasePage {
    
    // Page Elements
    @FindBy(css = "h6.oxd-topbar-header-breadcrumb-module")
    private WebElement recruitmentHeader;
    
    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addButton;
    
    @FindBy(className = "oxd-table")
    private WebElement candidatesTable;
    
    // Add Candidate Form Elements
    @FindBy(name = "firstName")
    private WebElement firstNameField;
    
    @FindBy(name = "middleName")
    private WebElement middleNameField;
    
    @FindBy(name = "lastName")
    private WebElement lastNameField;
    
    @FindBy(xpath = "(//div[@class='oxd-select-text-input'])[1]")
    private WebElement vacancyDropdown;
    
    @FindBy(xpath = "//label[text()='Email']/parent::div/following-sibling::div//input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Contact Number']/parent::div/following-sibling::div//input")
    private WebElement contactNumberField;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement resumeUpload;

    @FindBy(xpath = "//label[text()='Keywords']/parent::div/following-sibling::div//input")
    private WebElement keywordsField;

    @FindBy(xpath = "//label[text()='Date of Application']/parent::div/following-sibling::div//input")
    private WebElement dateOfApplicationField;

    @FindBy(xpath = "//label[text()='Notes']/parent::div/following-sibling::div//textarea")
    private WebElement notesField;

    @FindBy(xpath = "//span[contains(@class,'oxd-checkbox-input')]")
    private WebElement consentCheckbox;
    
    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;

    @FindBy(css = "button.oxd-button--ghost")
    private WebElement cancelButton;

    @FindBy(css = "p.oxd-text--toast-message")
    private WebElement successMessage;
    
    // Constructor
    public RecruitmentPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Verify Recruitment page is displayed
     * @return true if recruitment page is displayed
     */
    public boolean isRecruitmentPageDisplayed() {
        return isDisplayed(recruitmentHeader, "Recruitment header");
    }
    
    /**
     * Get Recruitment page title
     * @return recruitment title
     */
    public String getRecruitmentTitle() {
        return getText(recruitmentHeader, "Recruitment header");
    }
    
    /**
     * Click Add button to add new candidate
     */
    public void clickAdd() {
        click(addButton, "Add button");
        waitForSeconds(2);
    }
    
    /**
     * Verify candidates table is displayed
     * @return true if table is displayed
     */
    public boolean isCandidatesTableDisplayed() {
        return isDisplayed(candidatesTable, "Candidates table");
    }

    /**
     * Check if success message is displayed
     * @return true if success message is displayed
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            waitForSeconds(2);
            return isDisplayed(successMessage, "Success message");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Add a new candidate
     * @param firstName - Candidate's first name
     * @param middleName - Candidate's middle name
     * @param lastName - Candidate's last name
     * @param email - Candidate's email
     * @param contactNumber - Candidate's contact number
     * @param dateOfApplication - Date of application (format: YYYY-MM-DD)
     */
    public void addCandidate(String firstName, String middleName, String lastName, String email,
                            String contactNumber, String dateOfApplication) {
        clickAdd();

        // Wait for form to load
        waitForSeconds(2);

        // Enter First Name
        type(firstNameField, firstName, "First Name field");
        waitForSeconds(1);

        // Enter Middle Name
        if (middleName != null && !middleName.isEmpty()) {
            type(middleNameField, middleName, "Middle Name field");
            waitForSeconds(1);
        }

        // Enter Last Name
        type(lastNameField, lastName, "Last Name field");
        waitForSeconds(1);

        // Select Vacancy (select first option from dropdown)
        click(vacancyDropdown, "Vacancy dropdown");
        waitForSeconds(2);
        clickFirstDropdownOption();
        waitForSeconds(1);

        // Enter Email
        type(emailField, email, "Email field");
        waitForSeconds(1);

        // Enter Contact Number (if provided)
        if (contactNumber != null && !contactNumber.isEmpty()) {
            type(contactNumberField, contactNumber, "Contact Number field");
            waitForSeconds(1);
        }

        // Enter Date of Application (if provided)
        if (dateOfApplication != null && !dateOfApplication.isEmpty()) {
            type(dateOfApplicationField, dateOfApplication, "Date of Application field");
            waitForSeconds(1);
        }

        // Scroll to consent checkbox
        scrollToElement(consentCheckbox);
        waitForSeconds(1);

        // Check consent checkbox
        click(consentCheckbox, "Consent checkbox");
        waitForSeconds(1);

        // Click Save
        click(saveButton, "Save button");
        waitForSeconds(5);
    }

    /**
     * Scroll to element
     */
    private void scrollToElement(WebElement element) {
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            // Ignore if scroll fails
        }
    }
    
    /**
     * Helper method to click first dropdown option
     */
    private void clickFirstDropdownOption() {
        driver.findElement(org.openqa.selenium.By.xpath("//div[@role='listbox']//div[@role='option'][1]")).click();
    }
    
    /**
     * Wait for specified seconds
     */
    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get current date in YYYY-MM-DD format
     * @return current date
     */
    public static String getCurrentDate() {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        return currentDate.toString();
    }
}
