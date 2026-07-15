import { DriverManager } from "../utils/DriverManager";
import { LoginPage } from "../pageObjects/LoginPage";
import { ProductPage } from "../pageObjects/ProductPage";

// Test Configuration
const BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
const USERNAME = "admin";
const PASSWORD = "password123";
const PRODUCT_NAME = "Test Asd Saree";
const BASE_PRICE = "1000";
const DISCOUNT_PRICE = "900";
const DESCRIPTION = "test product";
const CATEGORY = "Saree";
const SUBCATEGORY = "cotton";
const IMAGE_PATH = "C:\\Users\\Aditya\\Downloads\\test saree img.jpg";

async function testAddProductFlow() {
  let loginPage: LoginPage;
  let productPage: ProductPage;

  try {
    console.log("==================== TEST START: Add Product Flow ====================");
    
    console.log("About to initialize driver...");
    let driver;
    try {
      driver = await DriverManager.initializeDriver();
    } catch (initError) {
      console.error("Failed to initialize driver:", initError);
      throw initError;
    }
    console.log("Driver initialized.");
    
    // Initialize Page Objects
    loginPage = new LoginPage(driver);
    productPage = new ProductPage(driver);

    // Navigate to login page
    console.log("Step 1: Opening login page");
    await driver.get(BASE_URL);

    // Login
    console.log("Step 2: Performing login");
    await loginPage.login(USERNAME, PASSWORD);

    // Navigate to products and add product
    console.log("Step 3: Navigating to products");
    await productPage.navigateToProducts();

    console.log("Step 4: Clicking add product");
    await productPage.clickAddProduct();

    // Fill product details
    console.log("Step 5: Selecting category and subcategory");
    await productPage.selectCategory(CATEGORY);
    await productPage.selectSubcategory(SUBCATEGORY);

    console.log("Step 6: Entering product information");
    await productPage.enterProductTitle(PRODUCT_NAME);
    await productPage.enterBasePrice(BASE_PRICE);
    await productPage.enterDiscountedPrice(DISCOUNT_PRICE);

    const productCode = await productPage.getProductCode();
    if (productCode) {
      console.log(`Product code captured: ${productCode}`);
    }

    await productPage.enterDescription(DESCRIPTION);

    console.log("Step 7: Entering optional fields");
    await productPage.enterColor("Red");
    await productPage.enterQuantity("5");
    await productPage.enterSize("M");

    console.log("Step 8: Uploading image");
    await productPage.uploadImage(IMAGE_PATH);

    console.log("Step 9: Saving product");
    await productPage.clickSaveProduct();

    console.log("Step 10: Confirming save");
    await productPage.confirmSave();

    // Verify product was added
    console.log("Step 11: Navigating to view/edit products");
    await productPage.navigateToViewEditProducts();

    console.log("Step 12: Searching for product");
    await productPage.searchProduct(PRODUCT_NAME);

    console.log("Step 13: Validating product exists");
    const productExists = await productPage.validateProductExists(PRODUCT_NAME);
    if (!productExists) {
      throw new Error(`Product '${PRODUCT_NAME}' was not found in search results`);
    }
    console.log(`✓ Validation passed: product '${PRODUCT_NAME}' exists`);

    // Delete the product
    console.log("Step 14: Deleting the product");
    await productPage.deleteProduct();

    console.log("Step 15: Confirming delete");
    await productPage.confirmDelete();

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
testAddProductFlow().catch((error) => {
  console.error(error);
  process.exit(1);
});
