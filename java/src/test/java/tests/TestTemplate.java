package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;

/**
 * Template for creating new test cases in Java
 * 
 * Steps to create a new test:
 * 1. Copy this file and rename it (e.g., EditProductTest.java)
 * 2. Replace the class name and method name
 * 3. Update the test logic
 * 4. Reuse locators from existing page objects (LoginPage, ProductPage)
 * 5. Run: mvn test -Dtest=YourNewTestClass
 */

public class TestTemplate {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: New Test Scenario ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testNewScenario() throws InterruptedException {
        try {
            // Step 1: Navigate and login
            System.out.println("Step 1: Opening login page");
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Performing login");
            loginPage.login(USERNAME, PASSWORD);

            // Step 2: Navigate to products
            System.out.println("Step 3: Navigating to products");
            productPage.navigateToProducts();

            // Step 3: Perform your test action
            // Example: Search for a product
            System.out.println("Step 4: Searching for a product");
            productPage.navigateToViewEditProducts();
            productPage.searchProduct("Test Asd Saree");

            // Step 4: Validate
            System.out.println("Step 5: Validating results");
            boolean exists = productPage.validateProductExists("Test Asd Saree");
            if (!exists) {
                throw new AssertionError("Product not found");
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
