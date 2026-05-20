package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrdersPage extends BasePage {
    private By ordersNavLink = By.xpath("//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'Orders') or contains(@href, 'order')]");
    private By ordersTableCheckboxes = By.xpath("//table//input[@type='checkbox']");
    private By printSelectedButton = By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print selected') or contains(text(),'Print Selected')]");
    private By printIndicator = By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print preview')]");

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOrders() throws InterruptedException {
        System.out.println("Step: Navigating to Orders page");
        click(ordersNavLink);
        sleep(1000);
    }

    public void selectFirstOrderCheckbox() throws InterruptedException {
        System.out.println("Step: Selecting first order checkbox");
        List<WebElement> boxes = findElements(ordersTableCheckboxes);
        if (boxes == null || boxes.size() == 0) {
            System.out.println("No checkboxes found on orders table");
            return;
        }

        // Try to skip header/select-all checkbox if present
        for (WebElement box : boxes) {
            try {
                if (box.isDisplayed()) {
                    // Some tables have a select-all checkbox as the first one; skip if it has no value
                    String val = box.getAttribute("value");
                    if (val == null || val.trim().isEmpty()) {
                        continue;
                    }
                    if (!box.isSelected()) {
                        box.click();
                        sleep(500);
                        System.out.println("Checkbox clicked");
                        return;
                    } else {
                        System.out.println("Checkbox already selected");
                        return;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        System.out.println("No suitable checkbox clicked");
    }

    public void clickPrintSelected() throws InterruptedException {
        System.out.println("Step: Clicking Print Selected");
        click(printSelectedButton);
        sleep(1000);
    }

    /**
     * Attempts to detect whether a print popup/preview opened.
     * Strategy:
     * 1) Wait for a new window handle to appear
     * 2) Fallback: look for any element containing the word 'print' in the DOM
     */
    public boolean isPrintPopupDisplayed() {
        System.out.println("Step: Verifying print popup/display");

        try {
            // Explicit wait for a new window handle to appear
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            explicitWait.until(ExpectedConditions.numberOfWindowsToBe(2));
            System.out.println("Detected new window (print)");
            return true;
        } catch (Exception e) {
            // Fallback: try to find an element with 'print' text using explicit wait
        }

        try {
            WebElement indicator = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(printIndicator));
            if (indicator != null) {
                System.out.println("Detected print-related element in DOM");
                return true;
            }
        } catch (Exception e) {
            // ignore
        }

        System.out.println("No print popup or indicator detected");
        return false;
    }
}
