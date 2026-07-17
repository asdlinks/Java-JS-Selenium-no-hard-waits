package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    // Locators
    private final By usernameByName = By.name("username");
    private final By usernameByPlaceholder = By.xpath("//body/div[1]/div[1]/div[1]/form[1]/div[1]/input[1][@placeholder='Enter username']");
    private final By usernameByLegacy = By.xpath("/html/body/div[1]/div[1]/div[1]/form[1]/div[1]/input[1]");
    private final By passwordByName = By.name("password");
    private final By passwordByPlaceholder = By.xpath("//body/div[1]/div[1]/div[1]/form[1]/div[2]/input[1][@placeholder='Enter password']");
    private final By passwordByLegacy = By.xpath("/html/body/div[1]/div[1]/div[1]/form[1]/div[2]/input[1]");
    private final By loginButtonLocator = By.xpath(
            "//body/div[1]/div[1]/div[1]/form[1]/button[1][contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'login') or @type='submit']"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        System.out.println("Entering username");
        WebElement field = findOptionalElement(usernameByName);
        if (field == null) {
            field = findOptionalElement(usernameByLegacy);
        }
        if (field == null) {
            field = waitForElement(usernameByPlaceholder);
        }
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        System.out.println("Entering password");
        WebElement field = findOptionalElement(passwordByName);
        if (field == null) {
            field = findOptionalElement(passwordByLegacy);
        }
        if (field == null) {
            field = waitForElement(passwordByPlaceholder);
        }
        field.sendKeys(password);
    }

    public void clickLoginButton() {
        System.out.println("Clicking login button");
        click(loginButtonLocator);
    }

    public void login(String username, String password) {
        System.out.println("Step: Logging in to the application");
        if (username == null || username.trim().isEmpty()) {
            username = "admin";
        }
        if (password == null || password.trim().isEmpty()) {
            password = "Password123";
        }
        System.out.println("Attempting auth for " + username + " / " + password);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
