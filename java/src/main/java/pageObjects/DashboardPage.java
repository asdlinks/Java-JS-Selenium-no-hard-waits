package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage extends BasePage {
    private By dashboardHeader = By.xpath(
            "//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'dashboard') or contains(@class,'dashboard') or contains(@id,'dashboard')]"
    );
    private By logoutButton = By.xpath(
            "//button[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'logout')] | //a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'logout')]"
    );
    private By dashboardLink = By.xpath(
            "//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'dashboard') or contains(@href,'dashboard')]"
    );
    private By loginButtonLocator = By.xpath(
            "//button[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'login') or @type='submit']"
    );

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardVisible() {
        return isElementVisible(dashboardHeader);
    }

    public boolean isOnLoginPage() {
        return isElementVisible(loginButtonLocator);
    }

    public void logout() throws InterruptedException {
        WebElement logout = findOptionalElement(logoutButton);
        if (logout != null) {
            System.out.println("Step: Clicking logout");
            logout.click();
            sleep(1200);
        } else {
            System.out.println("Logout button not found, attempting direct logout URL");
            driver.get("https://test.chrisrichardcreations.com/admin/logout");
            sleep(1200);
        }
    }

    public void navigateToDashboard() throws InterruptedException {
        WebElement dashboard = findOptionalElement(dashboardLink);
        if (dashboard != null) {
            System.out.println("Step: Clicking dashboard navigation link");
            dashboard.click();
            sleep(1200);
            return;
        }
        System.out.println("Dashboard link not found, navigating directly");
        driver.get("https://test.chrisrichardcreations.com/admin/dashboard");
        sleep(1200);
    }
}
