package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
    public boolean isPrintPopupDisplayed() throws InterruptedException {
        System.out.println("Step: Verifying print popup/display");
        // Wait briefly for a new window
        int attempts = 0;
        while (attempts < 10) {
            if (driver.getWindowHandles().size() > 1) {
                System.out.println("Detected new window (print)");
                return true;
            }
            Thread.sleep(300);
            attempts++;
        }

        // Fallback: try to find an element with 'print' text
        try {
            WebElement indicator = waitForElement(printIndicator);
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
