package tests;

import java.time.Instant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;

public class CreateEditProductVerificationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

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
            String productName = "Automated Test Product " + Instant.now().toEpochMilli();
            String updatedProductName = productName + " - Updated";

            System.out.println("Step 1: Opening login page");
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);

            System.out.println("Step 2: Logging in");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2000);

            System.out.println("Step 3: Navigating to add product form");
            productPage.navigateToProducts();
            productPage.clickAddProduct();
            Thread.sleep(1500);

            System.out.println("Step 4: Creating a new product");
            productPage.enterProductTitle(productName);
            productPage.enterBasePrice("1200");
            productPage.enterDiscountedPrice("1100");
            productPage.enterDescription("Created by automation for edit verification");
            productPage.clickSaveProduct();
            Thread.sleep(2000);
            productPage.confirmSave();

            System.out.println("Step 5: Verifying the product exists in the list");
            productPage.navigateToViewEditProducts();
            Thread.sleep(1500);
            productPage.searchProduct(productName);
            Thread.sleep(2000);
            if (!productPage.isProductVisibleInTable(productName)) {
                throw new AssertionError("Created product was not found in the products table");
            }

            System.out.println("Step 6: Editing the created product");
            if (!productPage.clickEditForProduct(productName)) {
                throw new AssertionError("Edit action was not available for product: " + productName);
            }
            Thread.sleep(1500);
            productPage.enterProductTitle(updatedProductName);
            productPage.clickSaveProduct();
            Thread.sleep(2000);
            productPage.confirmSave();

            System.out.println("Step 7: Re-verifying the updated product");
            productPage.navigateToViewEditProducts();
            Thread.sleep(1500);
            productPage.searchProduct(updatedProductName);
            Thread.sleep(2000);
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
