import { By, WebDriver, WebElement } from "selenium-webdriver";
import { BasePage } from "./BasePage";

export class OrdersPage extends BasePage {
  private ordersNavLink = By.xpath("//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'Orders') or contains(@href, 'order')]");
  private ordersTableCheckboxes = By.xpath("//table//input[@type='checkbox']");
  private printSelectedButton = By.xpath("//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print selected') or contains(text(),'Print Selected')]");
  private printIndicator = By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'print preview')]");

  constructor(driver: WebDriver) {
    super(driver);
  }

  async navigateToOrders(): Promise<void> {
    console.log("Step: Navigating to Orders page");
    await this.click(this.ordersNavLink);
    await this.waitForElement(this.ordersTableCheckboxes);
  }

  async selectFirstOrderCheckbox(): Promise<void> {
    console.log("Step: Selecting first order checkbox");
    const boxes: WebElement[] = await this.findElements(this.ordersTableCheckboxes);
    if (!boxes || boxes.length === 0) {
      console.log("No checkboxes found on orders table");
      return;
    }

    for (const box of boxes) {
      try {
        const displayed = await box.isDisplayed();
        if (!displayed) continue;

        const val = await box.getAttribute("value");
        if (val == null || val.trim() === "") {
          continue;
        }

        const selected = await box.isSelected();
        if (!selected) {
          await box.click();
          console.log("Checkbox clicked");
          return;
        } else {
          console.log("Checkbox already selected");
          return;
        }
      } catch (err) {
        // ignore and try next
      }
    }

    console.log("No suitable checkbox clicked");
  }

  async clickPrintSelected(): Promise<void> {
    console.log("Step: Clicking Print Selected");
    await this.click(this.printSelectedButton);
  }

  async isPrintPopupDisplayed(): Promise<boolean> {
    console.log("Step: Verifying print popup/display");

    try {
      await this.driver.wait(async () => {
        const handles = await this.driver.getAllWindowHandles();
        return handles.length > 1;
      }, 10000);
      console.log("Detected new window (print)");
      return true;
    } catch (e) {
      // ignore
    }

    try {
      const indicator = await this.waitForElement(this.printIndicator, 2000);
      if (indicator) {
        console.log("Detected print-related element in DOM");
        return true;
      }
    } catch (e) {
      // ignore
    }

    console.log("No print popup or indicator detected");
    return false;
  }
}
