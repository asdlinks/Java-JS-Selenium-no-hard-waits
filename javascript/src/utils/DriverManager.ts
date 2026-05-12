import { Builder, WebDriver } from "selenium-webdriver";
import chrome from "selenium-webdriver/chrome";
import chromedriver from "chromedriver";

export class DriverManager {
  private static driver: WebDriver;

  static async initializeDriver(): Promise<WebDriver> {
    console.log("Initializing Chrome driver...");
    const service = new chrome.ServiceBuilder(chromedriver.path);
    const options = new chrome.Options();
    options.addArguments("--start-maximized");
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.setChromeBinaryPath("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

    DriverManager.driver = await new Builder()
      .forBrowser("chrome")
      .setChromeService(service)
      .setChromeOptions(options)
      .build();

    console.log("Chrome driver initialized successfully.");
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
