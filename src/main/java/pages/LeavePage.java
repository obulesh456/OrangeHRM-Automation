package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeavePage extends BasePage {


    @FindBy(xpath = "//a[@class='oxd-main-menu-item active']")
    private WebElement leaveButton;


    @FindBy(xpath = "//h6[text()='Leave'] ")
   private WebElement leaveHeader;


    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private WebElement employeeName1;

    @FindBy(xpath = "//div[@class='oxd-select-text-input']")
    private WebElement leaveTypedropdown;


    @FindBy(xpath = "//input[@placeholder='yyyy-dd-mm']")
    private WebElement fromDate;

    @FindBy(xpath = "//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement toDate;


    @FindBy(xpath = "//button[@type='submit']")
    private WebElement assignButton;


    public LeavePage(WebDriver driver) {

        super(driver);
    }

    public void clickAssignLeave() {
        click(leaveButton, "user clicks to Assign Leave button");

    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clickByText(String text) {
        driver.findElement(By.xpath("//div[@role='option']//span[text()='" + text + "']")).click();
    }

    public void searchAndSelectEmployee(String text) {
        type(employeeName1, text, "employee name search filed");
        waitForSeconds(2);
        clickFirstAutoCompleteOption();
        waitForSeconds(1);

    }

    public void clickFirstAutoCompleteOption() {
        waitForSeconds(1);
        driver.findElement(By.xpath("//div[@role='listbox']//div[@role='option'][1]")).click();

    }

    public boolean isLeavePageDisplayed() {

        return isDisplayed(leaveHeader, "Leave header");
    }

    public String getLeaveTitle() {

        return getText(leaveHeader, "Leave header");
    }

    public void assignLeaveTrack(String employeeName, String leaveType, String fromDate123, String toDate123) {

        clickAssignLeave();
        waitForSeconds(1);
        searchAndSelectEmployee(employeeName);
        waitForSeconds(1);
        click(leaveTypedropdown, "verify status dropdown list values");
        waitForSeconds(1);
        clickByText(leaveType);
        waitForSeconds(1);
        type(fromDate, fromDate123, "verifying from date fields");
        type(toDate, toDate123, "verifying to date fields");
        click(assignButton, "click assign button");
    }

}