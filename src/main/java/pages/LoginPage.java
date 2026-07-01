package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.LoggerUtils;

/**
 * Page Object class for Login Page
 */
public class LoginPage extends BasePage {
    
    // Page Elements
    @FindBy(name = "username")
    private WebElement usernameField;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;
    
    @FindBy(className = "oxd-alert-content-text")
    private WebElement errorMessage;
    
    @FindBy(className = "orangehrm-login-branding")
    private WebElement loginLogo;
    
    @FindBy(className = "orangehrm-login-title")
    private WebElement loginTitle;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Perform login with username and password
     * @param username
     * @param password
     * @return DashboardPage
     */
    public DashboardPage login(String username, String password) {
        LoggerUtils.info("Attempting login with username: " + username);
        type(usernameField, username, "Username field");
        type(passwordField, password, "Password field");
        click(loginButton, "Login button");
        return new DashboardPage(driver);
    }
    
    /**
     * Enter username
     * @param username
     */
    public void enterUsername(String username) {
        type(usernameField, username, "Username field");
    }
    
    /**
     * Enter password
     * @param password
     */
    public void enterPassword(String password) {
        type(passwordField, password, "Password field");
    }
    
    /**
     * Click login button
     */
    public void clickLogin() {
        click(loginButton, "Login button");
    }
    
    /**
     * Get error message text
     * @return error message
     */
    public String getErrorMessage() {
        return getText(errorMessage, "Error message");
    }
    
    /**
     * Check if login page is displayed
     * @return true if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginLogo, "Login logo");
    }
    
    /**
     * Get login page title
     * @return login page title
     */
    public String getLoginTitle() {
        return getText(loginTitle, "Login title");
    }
    
    /**
     * Verify login page elements
     * @return true if all elements are present
     */
    public boolean verifyLoginPageElements() {
        boolean usernamePresent = isDisplayed(usernameField, "Username field");
        boolean passwordPresent = isDisplayed(passwordField, "Password field");
        boolean loginBtnPresent = isDisplayed(loginButton, "Login button");
        boolean logoPresent = isDisplayed(loginLogo, "Login logo");
        
        return usernamePresent && passwordPresent && loginBtnPresent && logoPresent;
    }
}
