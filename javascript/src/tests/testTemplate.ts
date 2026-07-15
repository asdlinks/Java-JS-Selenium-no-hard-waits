import { DriverManager } from "../utils/DriverManager";
import { LoginPage } from "../pageObjects/LoginPage";
import { ProductPage } from "../pageObjects/ProductPage";

/**
 * Template for creating new test cases in JavaScript/TypeScript
 * 
 * Steps to create a new test:
 * 1. Copy this file and rename it (e.g., editProductTest.ts)
 * 2. Replace the function name and update the test logic
 * 3. Import any additional page objects needed
 * 4. Reuse locators from existing page objects
 * 5. Run: npm run test:your-new-test
 */

async function testNewScenario() {
  let loginPage: LoginPage;
  let productPage: ProductPage;

  try {
    console.log("==================== TEST START: New Test Scenario ====================");
    
    const driver = await DriverManager.initializeDriver();
    
    // Initialize Page Objects
    loginPage = new LoginPage(driver);
    productPage = new ProductPage(driver);

    // Step 1: Navigate and login
    console.log("Step 1: Opening login page");
    await driver.get("https://test.chrisrichardcreations.com/admin/login");

    console.log("Step 2: Performing login");
    await loginPage.login("admin", "password123");

    // Step 2: Navigate to products
    console.log("Step 3: Navigating to products");
    await productPage.navigateToProducts();

    // Step 3: Perform your test action
    // Example: Search for a product
    console.log("Step 4: Searching for a product");
    await productPage.navigateToViewEditProducts();
    await productPage.searchProduct("Test Asd Saree");

    // Step 4: Validate
    console.log("Step 5: Validating results");
    const exists = await productPage.validateProductExists("Test Asd Saree");
    if (!exists) {
      throw new Error("Product not found");
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

// Uncomment to run this test
// testNewScenario().catch((error) => {
//   console.error(error);
//   process.exit(1);
// });
