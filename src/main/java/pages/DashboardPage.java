package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.LoggerUtils;

/**
 * Page Object class for Dashboard Page
 */
public class DashboardPage extends BasePage {
    
    // Page Elements
    @FindBy(css = "h6.oxd-topbar-header-breadcrumb-module")
    private WebElement dashboardHeader;
    
    @FindBy(className = "oxd-userdropdown-name")
    private WebElement userDropdown;
    
    @FindBy(linkText = "Logout")
    private WebElement logoutLink;
    
    @FindBy(linkText = "Admin")
    private WebElement adminMenu;
    
    @FindBy(linkText = "PIM")
    private WebElement pimMenu;
    
    @FindBy(linkText = "Leave")
    private WebElement leaveMenu;
    
    @FindBy(linkText = "Time")
    private WebElement timeMenu;
    
    @FindBy(linkText = "Recruitment")
    private WebElement recruitmentMenu;
    
    @FindBy(linkText = "My Info")
    private WebElement myInfoMenu;
    
    @FindBy(linkText = "Performance")
    private WebElement performanceMenu;
    
    @FindBy(className = "oxd-grid-3")
    private WebElement quickLaunchPanel;
    
    // Constructor
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Get dashboard page title
     * @return dashboard title text
     */
    public String getDashboardTitle() {
        return getText(dashboardHeader, "Dashboard header");
    }
    
    /**
     * Verify dashboard page is displayed
     * @return true if dashboard is displayed
     */
    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardHeader, "Dashboard header");
    }
    
    /**
     * Navigate to Admin module
     * @return AdminPage
     */
    public AdminPage navigateToAdmin() {
        click(adminMenu, "Admin menu");
        return new AdminPage(driver);
    }
    
    /**
     * Navigate to PIM module
     * @return PIMPage
     */
    public PIMPage navigateToPIM() {
        click(pimMenu, "PIM menu");
        return new PIMPage(driver);
    }
    
    /**
     * Navigate to Leave module
     */
    public void navigateToLeave() {
        click(leaveMenu, "Leave menu");
    }
    
    /**
     * Navigate to My Info
     */
    public void navigateToMyInfo() {
        click(myInfoMenu, "My Info menu");
    }

    /**
     * Navigate to Recruitment module
     * @return RecruitmentPage
     */
    public RecruitmentPage navigateToRecruitment() {
        click(recruitmentMenu, "Recruitment menu");
        return new RecruitmentPage(driver);
    }

    /**
     * Perform logout
     * @return LoginPage
     */
    public LoginPage logout() {
        LoggerUtils.info("Logging out");
        click(userDropdown, "User dropdown");
        click(logoutLink, "Logout link");
        return new LoginPage(driver);
    }
    
    /**
     * Get logged in username
     * @return username
     */
    public String getLoggedInUsername() {
        return getText(userDropdown, "User dropdown");
    }
    
    /**
     * Verify all main menu items
     * @return true if all menus are present
     */
    public boolean verifyMainMenus() {
        boolean admin = isDisplayed(adminMenu, "Admin menu");
        boolean pim = isDisplayed(pimMenu, "PIM menu");
        boolean leave = isDisplayed(leaveMenu, "Leave menu");
        boolean time = isDisplayed(timeMenu, "Time menu");
        boolean recruitment = isDisplayed(recruitmentMenu, "Recruitment menu");
        
        return admin && pim && leave && time && recruitment;
    }
}
