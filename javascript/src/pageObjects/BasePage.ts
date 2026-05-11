import { WebDriver, By, WebElement, until } from "selenium-webdriver";

export class BasePage {
  protected driver: WebDriver;

  constructor(driver: WebDriver) {
    this.driver = driver;
  }

  async waitForElement(locator: By, timeout = 20000): Promise<WebElement> {
    return this.driver.wait(until.elementLocated(locator), timeout);
  }

  async waitForClickable(locator: By, timeout = 20000): Promise<WebElement> {
    const element = await this.waitForElement(locator, timeout);
    await this.driver.wait(until.elementIsVisible(element), timeout);
    await this.driver.wait(until.elementIsEnabled(element), timeout);
    return element;
  }

  async findOptionalElement(locator: By): Promise<WebElement | null> {
    try {
      return await this.driver.findElement(locator);
    } catch {
      return null;
    }
  }

  async click(locator: By): Promise<void> {
    const element = await this.waitForClickable(locator);
    await element.click();
  }

  async sendKeys(locator: By, keys: string): Promise<void> {
    const element = await this.waitForElement(locator);
    await element.sendKeys(keys);
  }

  async clearAndSendKeys(locator: By, keys: string): Promise<void> {
    const element = await this.waitForElement(locator);
    await element.clear();
    await element.sendKeys(keys);
  }

  async getAttribute(locator: By, attribute: string): Promise<string | null> {
    const element = await this.findOptionalElement(locator);
    if (element) {
      return await element.getAttribute(attribute);
    }
    return null;
  }

  async sleep(ms: number): Promise<void> {
    return this.driver.sleep(ms);
  }

  async findElements(locator: By): Promise<WebElement[]> {
    return await this.driver.findElements(locator);
  }

  async getCurrentUrl(): Promise<string> {
    return await this.driver.getCurrentUrl();
  }

  async switchToAlert() {
    return await this.driver.switchTo().alert();
  }
}
