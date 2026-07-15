package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
    public static WebDriver driver;

    public static WebDriver initializeDriver() {
        if (driver != null) {
            return driver;
        }

        WebDriverManager.chromedriver().driverVersion("148.0.7778.167").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-dev-shm-usage");

        String headlessProp = System.getProperty("headless");
        if (headlessProp == null) {
            headlessProp = System.getenv("HEADLESS");
        }
        boolean headless = false;
        if (headlessProp != null && headlessProp.equalsIgnoreCase("true")) {
            headless = true;
        }
        if (headless) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver getSharedDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            System.out.println("Step: Quitting browser and ending test");
            driver.quit();
        }
    }
}
