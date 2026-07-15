package tests;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.OrdersPage;
import utils.DriverManager;

public class MarkOrdersDispatchedTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private OrdersPage ordersPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Mark Confirmed Orders Dispatched ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        ordersPage = new OrdersPage(driver);
    }

    @Test
    public void testMarkConfirmedOrdersDispatched() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening login page");
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Performing login as admin");
            loginPage.login(USERNAME, PASSWORD);

            System.out.println("Step 3: Navigating to Orders page");
            ordersPage.navigateToOrders();

            System.out.println("Step 4: Filtering orders by status Confirmed");
            ordersPage.applyStatusFilter("Confirmed");

            System.out.println("Step 5: Selecting more than two confirmed orders");
            List<String> selectedOrders = ordersPage.selectTopNOrders(3);
            if (selectedOrders == null || selectedOrders.size() < 3) {
                throw new AssertionError("Failed to select at least 3 confirmed orders");
            }
            System.out.println("Selected order numbers: " + selectedOrders);

            System.out.println("Step 6: Marking selected orders as dispatched");
            ordersPage.clickMarkAsDispatched();
            ordersPage.confirmYesOnPopup();

            System.out.println("Step 7: Verifying success popup is displayed");
            if (!ordersPage.isSuccessPopupDisplayed()) {
                throw new AssertionError("Success popup was not displayed after marking orders as dispatched");
            }

            System.out.println("Step 8: Verifying status changed to dispatched for selected orders");
            for (String orderNumber : selectedOrders) {
                ordersPage.searchOrder(orderNumber);
                String status = ordersPage.getOrderStatusForOrder(orderNumber);
                System.out.println("Order " + orderNumber + " status after dispatch: " + status);
                if (!"dispatched".equalsIgnoreCase(status)) {
                    throw new AssertionError("Order " + orderNumber + " did not change status to dispatched. Found: " + status);
                }
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
