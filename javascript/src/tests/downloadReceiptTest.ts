import { DriverManager } from "../utils/DriverManager";
import { LoginPage } from "../pageObjects/LoginPage";
import { OrdersPage } from "../pageObjects/OrdersPage";

// Test Configuration
const BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
const USERNAME = "admin";
const PASSWORD = "password123";

async function testDownloadReceiptFlow() {
  let loginPage: LoginPage;
  let ordersPage: OrdersPage;

  try {
    console.log("==================== TEST START: Download Receipt ====================");

    console.log("About to initialize driver...");
    const driver = await DriverManager.initializeDriver();
    console.log("Driver initialized.");

    // Initialize Page Objects
    loginPage = new LoginPage(driver);
    ordersPage = new OrdersPage(driver);

    // Navigate to login page
    console.log("Step 1: Opening login page");
    await driver.get(BASE_URL);
    await driver.manage().deleteAllCookies();
    await driver.sleep(2000);

    // Login as admin
    console.log("Step 2: Performing login as admin");
    await loginPage.login(USERNAME, PASSWORD);

    // Navigate to orders
    console.log("Step 3: Navigating to Orders page");
    await ordersPage.navigateToOrders();
    await ordersPage.sleep(2000);

    // Select an order checkbox
    console.log("Step 4: Selecting an order checkbox");
    await ordersPage.selectFirstOrderCheckbox();
    await ordersPage.sleep(1000);

    // Click Print Selected
    console.log("Step 5: Clicking Print Selected");
    await ordersPage.clickPrintSelected();
    await ordersPage.sleep(2000);

    // Verify print popup/preview displayed
    console.log("Step 6: Verifying print popup is displayed");
    const printShown = await ordersPage.isPrintPopupDisplayed();
    if (!printShown) {
      throw new Error("Print popup/preview was not detected after clicking Print Selected");
    }

    console.log("==================== TEST PASSED ====================");
  } catch (error) {
    console.error("==================== TEST FAILED ====================");
    console.error("Error:", error);
    throw error;
  } finally {
    await DriverManager.quitDriver();
  }
}

// Run the test
testDownloadReceiptFlow().catch((error) => {
  console.error(error);
  process.exit(1);
});
