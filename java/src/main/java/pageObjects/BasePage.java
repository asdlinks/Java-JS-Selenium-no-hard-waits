package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    public void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
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
}
