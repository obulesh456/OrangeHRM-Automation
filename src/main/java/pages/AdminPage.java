package pages;

import base.BasePage;
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
    
    @FindBy(css = "button.oxd-button--secondary")
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
}
