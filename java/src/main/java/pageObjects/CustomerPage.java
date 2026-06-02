package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerPage extends BasePage {
    // Locators for Terms & Conditions
    private By termsConditionsButton = By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'conditions')] | //a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms') and contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'conditions')] | //*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms & conditions') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'terms and conditions')]");
    private By pageTitle = By.xpath("//h1 | //title");

    public CustomerPage(WebDriver driver) {
        super(driver);
    }

    public void clickTermsAndConditions() throws InterruptedException {
        System.out.println("Step: Clicking Terms and Conditions button");
        click(termsConditionsButton);
        sleep(2000);
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
        String pageTitle = getPageTitle();
        
        System.out.println("Current URL: " + currentUrl);
        System.out.println("Page Title: " + pageTitle);
        
        boolean urlContainsTerms = currentUrl.toLowerCase().contains("terms") || currentUrl.toLowerCase().contains("condition");
        boolean titleContainsTerms = pageTitle.toLowerCase().contains("terms") && pageTitle.toLowerCase().contains("condition");
        
        return urlContainsTerms || titleContainsTerms;
    }
}
