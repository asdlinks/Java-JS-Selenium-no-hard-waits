import { Builder, WebDriver } from "selenium-webdriver";
import chrome from "selenium-webdriver/chrome";
import chromedriver from "chromedriver";

export class DriverManager {
  private static driver: WebDriver;

  static async initializeDriver(): Promise<WebDriver> {
    const service = new chrome.ServiceBuilder(chromedriver.path);
    const options = new chrome.Options();
    options.addArguments("--start-maximized");

    DriverManager.driver = await new Builder()
      .forBrowser("chrome")
      .setChromeService(service)
      .setChromeOptions(options)
      .build();

    return DriverManager.driver;
  }

  static getDriver(): WebDriver {
    return DriverManager.driver;
  }

  static async quitDriver(): Promise<void> {
    if (DriverManager.driver) {
      console.log("Step: Quitting browser and ending test");
      await DriverManager.driver.quit();
    }
  }
}
