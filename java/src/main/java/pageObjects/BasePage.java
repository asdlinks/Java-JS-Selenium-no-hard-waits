package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement findOptionalElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public void clickSafely(By locator) {
        try {
            waitForClickable(locator).click();
        } catch (StaleElementReferenceException e) {
            waitForClickable(locator).click();
        }
    }

    public void clickWithJavaScript(By locator) {
        WebElement element = waitForElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void sendKeys(By locator, String keys) {
        waitForElement(locator).sendKeys(keys);
    }

    public void clearAndSendKeys(By locator, String keys) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(keys);
    }

    public String getAttribute(By locator, String attribute) {
        WebElement element = findOptionalElement(locator);
        if (element != null) {
            return element.getAttribute(attribute);
        }
        return null;
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isElementVisible(By locator) {
        WebElement element = findOptionalElement(locator);
        return element != null && element.isDisplayed();
    }

    public void waitForUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public void openNewTab(String url) {
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", url);
    }

    public void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    public void loginAndVerifySuccess(String username, String password) {
        sendKeys(By.name("username"), username);
        sendKeys(By.name("password"), password);
        click(By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'login')]"));
        if (!isElementVisible(By.xpath("//div[contains(text(),'Welcome')]"))) {
            throw new AssertionError("Expected welcome message after login");
        }
    }

    public void checkoutAndValidateOrder() {
        click(By.xpath("//button[contains(text(),'Checkout')]"));
        if (!isElementVisible(By.xpath("//h2[contains(text(),'Thank you')]"))) {
            throw new AssertionError("Order confirmation was not shown");
        }
    }

    public void waitForAnyElement(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void waitForPageToSettle() {
        new WebDriverWait(driver, Duration.ofSeconds(120)).until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
    }
}
