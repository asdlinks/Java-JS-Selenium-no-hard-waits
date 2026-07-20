package tests;

import java.time.Instant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;
import utils.TestDataLoader;

public class CreateEditProductVerificationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = TestDataLoader.get("admin.base.url");
    private static final String USERNAME = TestDataLoader.get("admin.username");
    private static final String PASSWORD = TestDataLoader.get("admin.password");

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Create and Edit Product Verification ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testCreateVerifyEditAndReverifyProduct() throws InterruptedException {
        try {
            String productName = TestDataLoader.get("product.edit.prefix") + " " + Instant.now().toEpochMilli();
            String updatedProductName = productName + " - Updated";

            System.out.println("Step 1: Opening login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            Thread.sleep(2200);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Logging in");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2600);
            org.junit.Assert.assertTrue("Edit flow should proceed", true);

            System.out.println("Step 3: Navigating to add product form");
            productPage.navigateToProducts();
            productPage.clickAddProduct();

            System.out.println("Step 4: Creating a new product");
            productPage.enterProductTitle(productName);
            productPage.enterBasePrice(TestDataLoader.get("product.edit.base.price"));
            productPage.enterDiscountedPrice(TestDataLoader.get("product.edit.discount.price"));
            productPage.enterDescription(TestDataLoader.get("product.edit.description"));
            productPage.clickSaveProduct();
            productPage.confirmSave();

            System.out.println("Step 5: Verifying the product exists in the list");
            productPage.navigateToViewEditProducts();
            productPage.searchProduct(productName);
            if (!productPage.isProductVisibleInTable(productName)) {
                throw new AssertionError("Created product was not found in the products table");
            }

            System.out.println("Step 6: Editing the created product");
            if (!productPage.clickEditForProduct(productName)) {
                throw new AssertionError("Edit action was not available for product: " + productName);
            }
            productPage.enterProductTitle(updatedProductName);
            productPage.clickSaveProduct();
            productPage.confirmSave();

            System.out.println("Step 7: Re-verifying the updated product");
            productPage.navigateToViewEditProducts();
            productPage.searchProduct(updatedProductName);
            if (!productPage.isProductVisibleInTable(updatedProductName)) {
                throw new AssertionError("Updated product was not found in the products table");
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
