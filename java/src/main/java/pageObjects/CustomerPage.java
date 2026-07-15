package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.CustomerPortalSnapshotData;

public class CustomerPage extends BasePage {
    // Locators for Terms & Conditions
    private final By termsConditionsButton = By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'conditions')] | //a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'conditions')] | //*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms & conditions') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms and conditions')]");
    private final By pageTitle = By.xpath("//h1 | //title");
    private final By heroSliderSection = By.xpath("//section[contains(@class, 'h-[80vh]') and contains(@class, 'overflow-hidden')]");
    private final By sliderPrevArrow = By.xpath("//section[contains(@class, 'h-[80vh]') and contains(@class, 'overflow-hidden')]//button[.//*[contains(@class, 'lucide-chevron-left')]]");
    private final By sliderNextArrow = By.xpath("//section[contains(@class, 'h-[80vh]') and contains(@class, 'overflow-hidden')]//button[.//*[contains(@class, 'lucide-chevron-right')]]");
    private final By activeSlideHeading = By.xpath("//section[contains(@class, 'h-[80vh]') and contains(@class, 'overflow-hidden')]//div[contains(@class, 'max-w-2xl')]//h2 | //section[contains(@class, 'h-[80vh]') and contains(@class, 'overflow-hidden')]//div[contains(@class, 'max-w-2xl')]//h3");

    public CustomerPage(WebDriver driver) {
        super(driver);
    }

    public void clickTermsAndConditions() {
        System.out.println("Step: Clicking Terms and Conditions button");
        click(termsConditionsButton);
    }

    public String getPageTitle() {
        System.out.println("Step: Getting page title");
        WebElement titleElement = findOptionalElement(pageTitle);
        if (titleElement != null) {
            String tagName = titleElement.getTagName();
            if ("title".equalsIgnoreCase(tagName)) {
                return driver.getTitle();
            }
            return titleElement.getText();
        }
        return driver.getTitle();
    }

    public boolean isTermsAndConditionsPageOpened() {
        System.out.println("Step: Verifying Terms and Conditions page opened");
        String currentUrl = driver.getCurrentUrl();
        String titleText = getPageTitle();
        
        System.out.println("Current URL: " + currentUrl);
        System.out.println("Page Title: " + titleText);
        
        boolean urlContainsTerms = currentUrl.toLowerCase().contains("terms") || currentUrl.toLowerCase().contains("condition");
        boolean titleContainsTerms = titleText.toLowerCase().contains("terms") && titleText.toLowerCase().contains("condition");
        
        return urlContainsTerms || titleContainsTerms;
    }

    public String getCurrentSlideTitle() {
        WebElement heading = findOptionalElement(activeSlideHeading);
        return heading != null ? heading.getText().trim() : "";
    }

    public boolean isCurrentSlideTitleVisible() {
        WebElement heading = findOptionalElement(activeSlideHeading);
        return heading != null && heading.isDisplayed() && !heading.getText().trim().isEmpty();
    }

    public void clickNextSlideArrow() {
        System.out.println("Step: Clicking next slider arrow");
        click(sliderNextArrow);
    }

    public void clickPreviousSlideArrow() {
        System.out.println("Step: Clicking previous slider arrow");
        click(sliderPrevArrow);
    }

    public void waitForSlideTitleToChange(String previousTitle) {
        WebDriverWait waitForChange = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForChange.until(ExpectedConditions.or(
                ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(activeSlideHeading, previousTitle)),
                ExpectedConditions.visibilityOfElementLocated(activeSlideHeading)
        ));
    }

    public boolean isHeroSliderVisible() {
        return isElementVisible(heroSliderSection);
    }

    public boolean isProductVisibleOnPortal(String productName) {
        System.out.println("Step: Checking customer portal for product: " + productName);
        return !findElements(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + productName.toLowerCase() + "')]")).isEmpty();
    }

    public String getPageTextContent() {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.body ? document.body.innerText : '';");
    }

    public CustomerPortalSnapshotData captureHomePortalSnapshot() {
        String pageText = getPageTextContent();
        return CustomerPortalSnapshotData.fromHomePage(pageText);
    }

    public CustomerPortalSnapshotData captureAccountPortalSnapshot() {
        String pageText = getPageTextContent();
        return CustomerPortalSnapshotData.fromAccountPage(pageText);
    }
}
