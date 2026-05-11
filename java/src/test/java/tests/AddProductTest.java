package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;

public class AddProductTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    // Test Configuration
    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    private static final String PRODUCT_NAME = "Test Asd Saree";
    private static final String BASE_PRICE = "1000";
    private static final String DISCOUNT_PRICE = "900";
    private static final String DESCRIPTION = "test product";
    private static final String CATEGORY = "Saree";
    private static final String SUBCATEGORY = "cotton";
    private static final String IMAGE_PATH = "C:\\Users\\Aditya\\Downloads\\test saree img.jpg";

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
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);

            // Login
            System.out.println("Step 2: Performing login");
            loginPage.login(USERNAME, PASSWORD);

            // Navigate to products and add product
            System.out.println("Step 3: Navigating to products");
            productPage.navigateToProducts();
            productPage.sleep(2000);

            System.out.println("Step 4: Clicking add product");
            productPage.clickAddProduct();
            productPage.sleep(2000);

            // Fill product details
            System.out.println("Step 5: Selecting category and subcategory");
            productPage.selectCategory(CATEGORY);
            productPage.selectSubcategory(SUBCATEGORY);
            productPage.sleep(2000);

            System.out.println("Step 6: Entering product information");
            productPage.enterProductTitle(PRODUCT_NAME);
            productPage.enterBasePrice(BASE_PRICE);
            productPage.enterDiscountedPrice(DISCOUNT_PRICE);

            String productCode = productPage.getProductCode();
            if (productCode != null && !productCode.isEmpty()) {
                System.out.println("Product code captured: " + productCode);
            }

            productPage.enterDescription(DESCRIPTION);
            productPage.sleep(2000);

            System.out.println("Step 7: Entering optional fields");
            productPage.enterColor("Red");
            productPage.enterQuantity("5");
            productPage.enterSize("M");
            productPage.sleep(2000);

            System.out.println("Step 8: Uploading image");
            productPage.uploadImage(IMAGE_PATH);
            productPage.sleep(2000);

            System.out.println("Step 9: Saving product");
            productPage.clickSaveProduct();
            productPage.sleep(2000);

            System.out.println("Step 10: Confirming save");
            productPage.confirmSave();
            productPage.sleep(2000);

            // Verify product was added
            System.out.println("Step 11: Navigating to view/edit products");
            productPage.navigateToViewEditProducts();
            productPage.sleep(2000);

            System.out.println("Step 12: Searching for product");
            productPage.searchProduct(PRODUCT_NAME);
            productPage.sleep(2000);

            System.out.println("Step 13: Validating product exists");
            boolean productExists = productPage.validateProductExists(PRODUCT_NAME);
            if (!productExists) {
                throw new AssertionError("Product '" + PRODUCT_NAME + "' was not found in search results");
            }
            System.out.println("✓ Validation passed: product '" + PRODUCT_NAME + "' exists");
            productPage.sleep(2000);

            // Delete the product
            System.out.println("Step 14: Deleting the product");
            productPage.deleteProduct();
            productPage.sleep(2000);

            System.out.println("Step 15: Confirming delete");
            productPage.confirmDelete();
            productPage.sleep(2000);

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
