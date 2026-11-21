package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddLocationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Navigation Locators
    private By adminMenu = By.xpath("//a[.//span[normalize-space()='Admin']]");
    private By moreTab = By.xpath("//li[contains(@class,'oxd-topbar-body-nav-tab') and .//span[normalize-space()='More']]");
    private By organizationTab = By.xpath("//*[contains(@class,'oxd-topbar-body-nav-tab') and normalize-space()='Organization' or contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space()='Organization']");
    private By locationsMenuItem = By.xpath("//a[normalize-space()='Locations']");
    private By addButton = By.xpath("//button[@type='button' and normalize-space()='Add']");

    // 2. Form Locators
    private By nameInput = By.xpath("//label[contains(normalize-space(.),'Name')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By cityInput = By.xpath("//label[contains(normalize-space(.),'City')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By stateInput = By.xpath("//label[contains(normalize-space(.),'State/Province')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By zipInput = By.xpath("//label[contains(normalize-space(.),'Zip/Postal Code')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By countryDropdown = By.xpath("//label[contains(normalize-space(.),'Country')]/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    private By countryListbox = By.xpath("//div[@role='listbox']");
    private By countryOptions = By.xpath("//div[@role='listbox']//div[@role='option']");
    private By phoneInput = By.xpath("//label[contains(normalize-space(.),'Phone')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By faxInput = By.xpath("//label[contains(normalize-space(.),'Fax')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private By addressTextarea = By.xpath("//label[contains(normalize-space(.),'Address')]/ancestor::div[contains(@class,'oxd-input-group')]//textarea");
    private By notesTextarea = By.xpath("//label[contains(normalize-space(.),'Notes')]/ancestor::div[contains(@class,'oxd-input-group')]//textarea");
    private By saveButton = By.xpath("//button[@type='submit' and normalize-space(.)='Save']");

    // 3. Assertion Locators (Toast & Table)
    private By successToast = By.xpath("//div[contains(@class,'oxd-toast--success')]");
    private By toastMessageText = By.xpath("//div[contains(@class,'oxd-toast--success')]//p[contains(@class,'oxd-text--toast-message')]");

    // 4. Constructor
    public AddLocationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // 5. Small Helper Methods
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

    private void typeText(By locator, String text) {
        wait.until(currentDriver -> {
            try {
                WebElement element = currentDriver.findElement(locator);
                if (!element.isDisplayed()) {
                    return false;
                }
                ((JavascriptExecutor) currentDriver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});", element);
                element.clear();
                element.sendKeys(text);
                return true;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
    }

    // 6. Navigation Method
    public void openAddLocationForm() {
        clickElement(adminMenu);
        openOrganizationMenu();
        clickElement(locationsMenuItem);
        clickElement(addButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }

    // 7. Country Dropdown Method using findElements
    public void selectCountry(String countryName) {
        clickElement(countryDropdown);
        wait.until(ExpectedConditions.visibilityOfElementLocated(countryListbox));
        wait.until(currentDriver -> {
            List<WebElement> options = currentDriver.findElements(countryOptions);

            for (WebElement option : options) {
                try {
                    if (option.getText().trim().equalsIgnoreCase(countryName)) {
                        ((JavascriptExecutor) currentDriver).executeScript(
                                "arguments[0].scrollIntoView({block:'center'});", option);
                        try {
                            option.click();
                        } catch (StaleElementReferenceException e) {
                            return false;
                        } catch (Exception e) {
                            ((JavascriptExecutor) currentDriver).executeScript("arguments[0].click();", option);
                        }
                        return true;
                    }
                } catch (StaleElementReferenceException e) {
                    break;
                }
            }
            WebElement listbox = currentDriver.findElement(countryListbox);
            ((JavascriptExecutor) currentDriver).executeScript(
                    "arguments[0].scrollTop += arguments[0].clientHeight;", listbox);
            return false;
        });
        wait.until(ExpectedConditions.invisibilityOfElementLocated(countryListbox));
        wait.until(currentDriver -> {
            try {
                WebElement dropdown = currentDriver.findElement(countryDropdown);
                return dropdown.getText().trim().equalsIgnoreCase(countryName);
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
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
        wait.until(ExpectedConditions.invisibilityOfElementLocated(countryListbox));
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

    public String getFieldErrorMessage(String fieldLabel) {
        By errorLocator = By.xpath("//label[contains(normalize-space(.),'" + fieldLabel + "')]/ancestor::div[contains(@class,'oxd-input-group')]//span[contains(@class,'oxd-input-field-error-message')]");
        return wait.until(currentDriver -> {
            try {
                List<WebElement> errors = currentDriver.findElements(errorLocator);
                if (errors.isEmpty() || !errors.get(0).isDisplayed()) {
                    return null;
                }
                String message = errors.get(0).getText().trim();
                return message.isEmpty() ? null : message;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }
}