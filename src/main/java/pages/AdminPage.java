package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object class for Admin Page
 */
public class AdminPage extends BasePage {

    // Page Elements
    @FindBy(css = "h6.oxd-topbar-header-breadcrumb-module")
    private WebElement adminHeader;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    private WebElement addButton;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement usernameSearchField;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(className = "oxd-table")
    private WebElement userTable;

    @FindBy(linkText = "User Management")
    private WebElement userManagementMenu;

    @FindBy(linkText = "Job")
    private WebElement jobMenu;

    @FindBy(linkText = "Organization")
    private WebElement organizationMenu;

    @FindBy(xpath = "(//div[@class='oxd-select-text-input'])[1]")
    private WebElement userRole;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement employeeName;

    @FindBy(xpath = "(//div[@class='oxd-select-text-input'])[2]")
    private WebElement statusDropdown;

    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    WebElement usernameField;

    @FindBy(xpath = "(//input[@type='password'])[1]")
    WebElement passwordField;

    @FindBy(xpath = "(//input[@type='password'])[2]")
    WebElement confirmPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement saveButton;


    // Constructor
    public AdminPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Verify Admin page is displayed
     * @return true if admin page is displayed
     */
    public boolean isAdminPageDisplayed() {
        return isDisplayed(adminHeader, "Admin header");
    }

    /**
     * Get Admin page title
     * @return admin title
     */
    public String getAdminTitle() {
        return getText(adminHeader, "Admin header");
    }

    /**
     * Click Add button
     */
    public void clickAdd() {
        click(addButton, "Add button");
    }

    /**
     * Search user by username
     * @param username
     */
    public void searchUser(String username) {
        type(usernameSearchField, username, "Username search field");
        click(searchButton, "Search button");
    }

    /**
     * Verify user table is displayed
     * @return true if table is displayed
     */
    public boolean isUserTableDisplayed() {
        return isDisplayed(userTable, "User table");
    }

    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clickByText(String text) {
        driver.findElement(By.xpath("//div[@role='option']//span[text()='" + text + "']")).click();
    }
    public  void searchAndSelectEmployee(String text){
        type(employeeName,text,"employee name search filed");
        waitForSeconds(2);
        clickFirstAutoCompleteOption();
        waitForSeconds(1);

    }
    public void clickFirstAutoCompleteOption(){
        waitForSeconds(1);
        driver.findElement(By.xpath("//div[@role='listbox']//div[@role='option'][1]")).click();

    }

    public void addEmployee(String role ,String EmployeeNameSearch,String status,String userName, String passWord) {
        clickAdd();
        waitForSeconds(3);
        click(userRole, "user role dorpdown");
        waitForSeconds(1);
        clickByText(role);
        searchAndSelectEmployee(EmployeeNameSearch);
        click(statusDropdown, "Status dropdown");
        waitForSeconds(1);
        clickByText(status);
        type(usernameField,userName, "Username field");
        type(passwordField,passWord, "password field");
        type(confirmPassword,passWord, "Confirm field");
        click(saveButton, "Save button");
        waitForSeconds(3);



    }


}
