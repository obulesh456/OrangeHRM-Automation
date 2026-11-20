package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ADDLOCATIONPAGE {

    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Navigation Locators (static -> @FindBy)
    @FindBy(xpath = "//a[.//span[normalize-space()='Admin']]")
    private WebElement adminMenu;

    @FindBy(xpath = "//li[contains(@class,'oxd-topbar-body-nav-tab') and .//span[normalize-space()='More']]")
    private WebElement moreTab;

    @FindBy(xpath = "//a[normalize-space()='Locations']")
    private WebElement locationsMenuItem;

    @FindBy(xpath = "//button[@type='button' and normalize-space()='Add']")
    private WebElement addButton;

    // Dynamic (shown directly or under "More") -> keep By
    private By organizationTab = By.xpath("//*[contains(@class,'oxd-topbar-body-nav-tab') and normalize-space()='Organization' or contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space()='Organization']");

    // 2. Form Locators (static -> @FindBy)
    @FindBy(xpath = "//label[contains(normalize-space(.),'Name')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement nameInput;

    @FindBy(xpath = "//label[contains(normalize-space(.),'City')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement cityInput;

    @FindBy(xpath = "//label[contains(normalize-space(.),'State/Province')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement stateInput;

    @FindBy(xpath = "//label[contains(normalize-space(.),'Zip/Postal Code')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement zipInput;

    @FindBy(xpath = "//label[contains(normalize-space(.),'Country')]/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]")
    private WebElement countryDropdown;

    @FindBy(xpath = "//label[contains(normalize-space(.),'Phone')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement phoneInput;

    @FindBy(xpath = "//label[contains(normalize-space(.),'Fax')]/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement faxInput;

    @FindBy(xpath = "//label[contains(normalize-space(.),'Address')]/ancestor::div[contains(@class,'oxd-input-group')]//textarea")
    private WebElement addressTextarea;

    @FindBy(xpath = "//label[contains(normalize-space(.),'Notes')]/ancestor::div[contains(@class,'oxd-input-group')]//textarea")
    private WebElement notesTextarea;

    @FindBy(xpath = "//button[@type='submit' and normalize-space(.)='Save']")
    private WebElement saveButton;

    // Dynamic dropdown options -> keep By
    private By countryOptions = By.xpath("//div[@role='listbox']//div[@role='option']");

    // 3. Assertion Locators (Toast appears and disappears -> keep By)
    private By successToast = By.xpath("//div[contains(@class,'oxd-toast--success')]");
    private By toastMessageText = By.xpath("//div[contains(@class,'oxd-toast--success')]//p[contains(@class,'oxd-text--toast-message')]");

    // 4. Constructor
    public ADDLOCATIONPAGE(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    // 5. Small Helper Methods

    // --- For WebElement (Page Factory) ---
    private WebElement waitAndScroll(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void clickElement(WebElement element) {
        WebElement target = waitAndScroll(element);
        try {
            target.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", target);
        }
    }

    private void typeText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        element.clear();
        element.sendKeys(text);
    }

    // --- For By (dynamic elements) ---
    private WebElement waitAndScroll(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void clickElement(By locator) {
        WebElement element = waitAndScroll(locator);
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    private void openOrganizationMenu() {
        List<WebElement> directOrg = driver.findElements(organizationTab);
        if (directOrg.isEmpty() || !directOrg.get(0).isDisplayed()) {
            clickElement(moreTab);
        }
        clickElement(organizationTab);
    }

    // 6. Navigation Method
    public void openAddLocationForm() {
        clickElement(adminMenu);
        openOrganizationMenu();
        clickElement(locationsMenuItem);
        clickElement(addButton);
        wait.until(ExpectedConditions.visibilityOf(nameInput));
    }

    // 7. Country Dropdown Method using findElements
    public void selectCountry(String countryName) {
        clickElement(countryDropdown);
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(countryOptions));

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(countryName)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                try {
                    option.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                }
                break;
            }
        }
    }

    // 8. Fill Form and Save Methods
    public void enterName(String name) {
        typeText(nameInput, name);
    }

    public void enterCity(String city) {
        typeText(cityInput, city);
    }

    public void enterState(String state) {
        typeText(stateInput, state);
    }

    public void enterZip(String zip) {
        typeText(zipInput, zip);
    }

    public void enterPhone(String phone) {
        typeText(phoneInput, phone);
    }

    public void enterFax(String fax) {
        typeText(faxInput, fax);
    }

    public void enterAddress(String address) {
        typeText(addressTextarea, address);
    }

    public void enterNotes(String notes) {
        typeText(notesTextarea, notes);
    }

    public void clickSave() {
        clickElement(saveButton);
    }

    public void addLocation(String name, String city, String state, String zip,
                            String country, String phone, String fax,
                            String address, String notes) {
        enterName(name);
        enterCity(city);
        enterState(state);
        enterZip(zip);
        selectCountry(country);
        enterPhone(phone);
        enterFax(fax);
        enterAddress(address);
        enterNotes(notes);
        clickSave();
    }

    // 9. Verification Helper Methods
    public boolean isSuccessToastDisplayed() {
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
        return toast.isDisplayed();
    }

    public String getSuccessToastMessage() {
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessageText));
        return message.getText().trim();
    }

    public boolean isLocationPresentInPage(String locationName) {
        By locationCell = By.xpath("//div[contains(@class,'oxd-table-body')]//div[normalize-space()='" + locationName + "']");
        WebElement cell = wait.until(ExpectedConditions.visibilityOfElementLocated(locationCell));
        return cell.isDisplayed();
    }
}