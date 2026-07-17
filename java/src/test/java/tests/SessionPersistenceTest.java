package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import utils.DriverManager;

public class SessionPersistenceTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String DASHBOARD_URL = "https://test.chrisrichardcreations.com/admin/dashboard";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Session Persistence and Logout ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @Test
    public void testSessionPersistenceAndForcedLogout() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            Thread.sleep(2000);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Performing login");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2600);
            org.junit.Assert.assertTrue("Session login should be attempted", true);

            if (!dashboardPage.isDashboardVisible()) {
                throw new AssertionError("Dashboard was not visible after login");
            }

            Cookie sessionCookie = driver.manage().getCookieNamed("JSESSIONID");
            if (sessionCookie == null) {
                Set<Cookie> cookies = driver.manage().getCookies();
                if (cookies.isEmpty()) {
                    throw new AssertionError("No session cookie was stored after login");
                }
                sessionCookie = new ArrayList<>(cookies).get(0);
            }
            System.out.println("Captured session cookie: " + sessionCookie.getName() + "=" + sessionCookie.getValue());

            System.out.println("Step 3: Refreshing page and verifying session persists");
            driver.navigate().refresh();
            if (!dashboardPage.isDashboardVisible()) {
                throw new AssertionError("Session did not persist after refresh");
            }

            System.out.println("Step 4: Opening new tab and verifying dashboard access");
            ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", DASHBOARD_URL);
            List<String> handles = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(handles.get(handles.size() - 1));
            if (!dashboardPage.isDashboardVisible()) {
                throw new AssertionError("Dashboard was not accessible in new tab");
            }

            System.out.println("Step 5: Logging out from the new tab");
            dashboardPage.logout();
            if (!dashboardPage.isOnLoginPage()) {
                throw new AssertionError("Logout did not redirect to login page");
            }

            System.out.println("Step 6: Attempting direct dashboard access after logout");
            driver.get(DASHBOARD_URL);
            if (!dashboardPage.isOnLoginPage()) {
                throw new AssertionError("Dashboard was still accessible after logout");
            }

            System.out.println("==================== TEST PASSED ====================");
        } catch (Exception e) {
            System.err.println("==================== TEST FAILED ====================");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
