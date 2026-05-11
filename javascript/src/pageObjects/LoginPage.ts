import { By, WebDriver } from "selenium-webdriver";
import { BasePage } from "./BasePage";

export class LoginPage extends BasePage {
  // Locators
  private usernameByName = By.name("username");
  private usernameByPlaceholder = By.xpath("//input[@placeholder='Enter username']");
  private passwordByName = By.name("password");
  private passwordByPlaceholder = By.xpath("//input[@placeholder='Enter password']");
  private loginButtonLocator = By.xpath(
    "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'login') or @type='submit']"
  );

  constructor(driver: WebDriver) {
    super(driver);
  }

  async enterUsername(username: string): Promise<void> {
    console.log("Entering username");
    const field =
      (await this.findOptionalElement(this.usernameByName)) ||
      (await this.waitForElement(this.usernameByPlaceholder));
    await field.sendKeys(username);
  }

  async enterPassword(password: string): Promise<void> {
    console.log("Entering password");
    const field =
      (await this.findOptionalElement(this.passwordByName)) ||
      (await this.waitForElement(this.passwordByPlaceholder));
    await field.sendKeys(password);
  }

  async clickLoginButton(): Promise<void> {
    console.log("Clicking login button");
    await this.click(this.loginButtonLocator);
  }

  async login(username: string, password: string): Promise<void> {
    console.log("Step: Logging in to the application");
    await this.enterUsername(username);
    await this.enterPassword(password);
    await this.clickLoginButton();
    await this.sleep(2000);
  }
}
