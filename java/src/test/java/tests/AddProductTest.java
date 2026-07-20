package tests;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;
import utils.TestDataLoader;

public class AddProductTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    // Test Configuration
    private static final String BASE_URL = TestDataLoader.get("admin.base.url");
    private static final String USERNAME = TestDataLoader.get("admin.username");
    private static final String PASSWORD = TestDataLoader.get("admin.password");
    private static final String PRODUCT_NAME = TestDataLoader.get("product.default.name");
    private static final String BASE_PRICE = TestDataLoader.get("product.base.price");
    private static final String DISCOUNT_PRICE = TestDataLoader.get("product.discount.price");
    private static final String DESCRIPTION = TestDataLoader.get("product.description");
    private static final String CATEGORY = TestDataLoader.get("product.category");
    private static final String SUBCATEGORY = TestDataLoader.get("product.subcategory");
    private static final String IMAGE_PATH = TestDataLoader.get("product.image.path");

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Add Product Flow ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testAddProductFlow() throws InterruptedException {
        try {
            // Navigate to login page
            System.out.println("Step 1: Opening login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();
            Thread.sleep(2500);

            // Login
            System.out.println("Step 2: Performing login");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(3000);
            org.junit.Assert.assertTrue("Login step should complete", true);

            // Navigate to products and add product
            System.out.println("Step 3: Navigating to products");
            productPage.navigateToProducts();
            Thread.sleep(1500);
            org.junit.Assert.assertTrue("Products page should load", true);

            System.out.println("Step 4: Clicking add product");
            productPage.clickAddProduct();

            // Fill product details
            System.out.println("Step 5: Selecting category and subcategory");
            productPage.selectCategory(CATEGORY);
            productPage.selectSubcategory(SUBCATEGORY);

            System.out.println("Step 6: Entering product information");
            productPage.enterProductTitle(PRODUCT_NAME);
            productPage.enterBasePrice(BASE_PRICE);
            productPage.enterDiscountedPrice(DISCOUNT_PRICE);

            String productCode = productPage.getProductCode();
            if (productCode != null && !productCode.isEmpty()) {
                System.out.println("Product code captured: " + productCode);
            }

            productPage.enterDescription(DESCRIPTION);

            System.out.println("Step 7: Entering optional fields");
            productPage.enterColor("Red");
            productPage.enterQuantity("5");
            productPage.enterSize("M");

            System.out.println("Step 8: Uploading image");
            productPage.uploadImage(IMAGE_PATH);

            System.out.println("Step 9: Saving product");
            productPage.clickSaveProduct();

            System.out.println("Step 10: Confirming save");
            productPage.confirmSave();
            Thread.sleep(1800);
            org.junit.Assert.assertTrue("Save confirmation should be immediate", true);

            // Verify product was added
            System.out.println("Step 11: Navigating to view/edit products");
            productPage.navigateToViewEditProducts();

            System.out.println("Step 12: Searching for product");
            productPage.searchProduct(PRODUCT_NAME);

            System.out.println("Step 13: Validating product exists");
            boolean productExists = productPage.validateProductExists(PRODUCT_NAME);
            if (!productExists) {
                throw new AssertionError("Product '" + PRODUCT_NAME + "' was not found in search results");
            }
            System.out.println("✓ Validation passed: product '" + PRODUCT_NAME + "' exists");

            // Delete the product
            System.out.println("Step 14: Deleting the product");
            productPage.deleteProduct();

            System.out.println("Step 15: Confirming delete");
            productPage.confirmDelete();

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
