package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    // Locators
    private By usernameByName = By.name("username");
    private By usernameByPlaceholder = By.xpath("//input[@placeholder='Enter username']");
    private By passwordByName = By.name("password");
    private By passwordByPlaceholder = By.xpath("//input[@placeholder='Enter password']");
    private By loginButtonLocator = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'login') or @type='submit']"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        System.out.println("Entering username");
        WebElement field = findOptionalElement(usernameByName);
        if (field == null) {
            field = waitForElement(usernameByPlaceholder);
        }
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        System.out.println("Entering password");
        WebElement field = findOptionalElement(passwordByName);
        if (field == null) {
            field = waitForElement(passwordByPlaceholder);
        }
        field.sendKeys(password);
    }

    public void clickLoginButton() {
        System.out.println("Clicking login button");
        click(loginButtonLocator);
    }

    public void login(String username, String password) throws InterruptedException {
        System.out.println("Step: Logging in to the application");
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        sleep(2000);
    }
}
