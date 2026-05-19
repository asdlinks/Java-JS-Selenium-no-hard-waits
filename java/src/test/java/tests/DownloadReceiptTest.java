package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import pageObjects.OrdersPage;
import utils.DriverManager;

public class DownloadReceiptTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private OrdersPage ordersPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Download Receipt ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        ordersPage = new OrdersPage(driver);
    }

    @Test
    public void testDownloadReceiptPrintPopup() throws InterruptedException {
        try {
            // Navigate to login page
            System.out.println("Step 1: Opening login page");
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);

            // Login as admin
            System.out.println("Step 2: Performing login as admin");
            loginPage.login(USERNAME, PASSWORD);

            // Navigate to orders
            System.out.println("Step 3: Navigating to Orders page");
            ordersPage.navigateToOrders();
            ordersPage.sleep(2000);

            // Select an order checkbox
            System.out.println("Step 4: Selecting an order checkbox");
            ordersPage.selectFirstOrderCheckbox();
            ordersPage.sleep(1000);

            // Click Print Selected
            System.out.println("Step 5: Clicking Print Selected");
            ordersPage.clickPrintSelected();
            ordersPage.sleep(2000);

            // Verify print popup/preview displayed
            System.out.println("Step 6: Verifying print popup is displayed");
            boolean printShown = ordersPage.isPrintPopupDisplayed();
            if (!printShown) {
                throw new AssertionError("Print popup/preview was not detected after clicking Print Selected");
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
