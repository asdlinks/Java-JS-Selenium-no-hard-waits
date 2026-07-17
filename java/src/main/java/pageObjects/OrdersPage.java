package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrdersPage extends BasePage {
    private final By ordersNavLink = By.xpath("//div[1]/div[1]/nav[1]/a[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'orders') or contains(@href, 'order')]");
    private final By ordersTableCheckboxes = By.xpath("//div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/input[@type='checkbox']");
    private final By statusFilterSelect = By.xpath("//div[1]/div[1]/div[1]/div[1]/select[1][contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'status') or contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'status') or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'status') or contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'status')]");
    private final By statusFilterDropdownButton = By.xpath("//div[1]/div[1]/div[1]/button[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'status') and (self::button or self::span or self::div)]");
    private final By markAsDispatchedButton = By.xpath("//div[1]/div[1]/div[1]/button[2][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'mark as dispatched') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'dispatch')]");
    private final By confirmYesButton = By.xpath("//div[1]/div[1]/div[1]/button[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'yes') or contains(text(),'Yes')]");
    private final By successPopupMessage = By.xpath("//div[1]/div[1]/div[1]/div[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'success') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'dispatched') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'updated')]");
    private final By orderSearchInput = By.xpath("//div[1]/div[1]/div[1]/input[1][contains(translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'search') or contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'search') or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'search')]");
    private final By printSelectedButton = By.xpath("//div[1]/div[1]/div[1]/button[3][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print selected') or contains(text(),'Print Selected')]");
    private final By printIndicator = By.xpath("//div[1]/div[1]/div[1]/div[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print preview')]");
    private final By brittleOrderCell = By.xpath("/html/body/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]");

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToOrders() {
        System.out.println("Step: Navigating to Orders page");
        try {
            click(ordersNavLink);
        } catch (Exception e) {
            System.out.println("Orders nav link click failed, falling back to direct orders URL: " + e.getMessage());
            driver.get("https://test.chrisrichardcreations.com/admin/orders");
        }
        waitForVisible(ordersTableCheckboxes);
    }

    public void selectFirstOrderCheckbox() {
        System.out.println("Step: Selecting first order checkbox");
        List<WebElement> boxes = findElements(ordersTableCheckboxes);
        if (boxes == null || boxes.isEmpty()) {
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

    public void clickPrintSelected() {
        System.out.println("Step: Clicking Print Selected");
        click(printSelectedButton);
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

    public void applyStatusFilter(String status) {
        System.out.println("Step: Applying status filter: " + status);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        WebElement statusSelect = findOptionalElement(statusFilterSelect);
        if (statusSelect != null) {
            try {
                new Select(statusSelect).selectByVisibleText(status);
                waitForVisible(ordersTableCheckboxes);
                return;
            } catch (Exception ignored) {
                // fallback to dropdown-style filter if select cannot be used
            }
        }

        WebElement dropdownButton = findOptionalElement(statusFilterDropdownButton);
        if (dropdownButton != null) {
            dropdownButton.click();
        }

        By optionBy = By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + status.toLowerCase() + "') and (self::li or self::button or self::span or self::a)]");
        click(optionBy);
        waitForVisible(ordersTableCheckboxes);
    }

    public List<String> selectTopNOrders(int count) {
        System.out.println("Step: Selecting top " + count + " orders from the filtered list");
        List<WebElement> rows = findElements(By.xpath("//table//tr[td]"));
        if (rows == null || rows.size() < count) {
            throw new AssertionError("Expected at least " + count + " orders after filtering, but found " + (rows == null ? 0 : rows.size()));
        }

        List<String> orderNumbers = new ArrayList<>();
        int selectedCount = 0;
        for (WebElement row : rows) {
            if (selectedCount >= count) {
                break;
            }

            WebElement checkbox = null;
            try {
                checkbox = row.findElement(By.xpath(".//input[@type='checkbox']"));
            } catch (Exception e) {
                continue;
            }
            if (checkbox == null || !checkbox.isDisplayed()) {
                continue;
            }
            String value = checkbox.getAttribute("value");
            if (value == null || value.trim().isEmpty()) {
                continue;
            }

            if (!checkbox.isSelected()) {
                checkbox.click();
            }

            String orderNumber = "";
            try {
                WebElement orderCell = row.findElement(By.xpath(".//td[not(.//input) and normalize-space(.)!=''][1]"));
                orderNumber = orderCell.getText().trim();
            } catch (Exception ignored) {
            }
            if (orderNumber.isEmpty()) {
                List<WebElement> cells = row.findElements(By.xpath(".//td[not(.//input)]"));
                if (!cells.isEmpty()) {
                    orderNumber = cells.get(0).getText().trim();
                }
            }
            if (orderNumber.isEmpty()) {
                orderNumber = "order-" + selectedCount;
            }
            orderNumbers.add(orderNumber);
            selectedCount++;
        }

        if (orderNumbers.size() < count) {
            throw new AssertionError("Could not select " + count + " orders from the filtered list");
        }
        return orderNumbers;
    }

    public void clickMarkAsDispatched() {
        System.out.println("Step: Clicking Mark as dispatched");
        click(markAsDispatchedButton);
    }

    public void confirmYesOnPopup() {
        System.out.println("Step: Confirming Yes on popup");
        if (isElementVisible(confirmYesButton)) {
            click(confirmYesButton);
        }
    }

    public boolean isSuccessPopupDisplayed() {
        System.out.println("Step: Verifying success popup is displayed");
        try {
            WebElement popup = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(successPopupMessage));
            return popup != null && popup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void searchOrder(String orderNumber) {
        System.out.println("Step: Searching for order: " + orderNumber);
        WebElement searchField = findOptionalElement(orderSearchInput);
        if (searchField != null) {
            searchField.clear();
            searchField.sendKeys(orderNumber);
            searchField.sendKeys(Keys.ENTER);
        }
    }

    public String getFirstOrderCellText() {
        return driver.findElement(brittleOrderCell).getText();
    }

    public String getOrderStatusForOrder(String orderNumber) {
        System.out.println("Step: Getting status for order: " + orderNumber);
        try {
            WebElement row = driver.findElement(By.xpath("//tr[td[contains(normalize-space(.), '" + orderNumber + "')]]"));
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            for (WebElement cell : cells) {
                String text = cell.getText().trim();
                if (text.equalsIgnoreCase("dispatched") || text.equalsIgnoreCase("confirmed") || text.equalsIgnoreCase("pending") || text.equalsIgnoreCase("processing") || text.equalsIgnoreCase("cancelled")) {
                    return text;
                }
            }
            if (!cells.isEmpty()) {
                return cells.get(cells.size() - 1).getText().trim();
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}
