package tests;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;

public class ProductFormValidationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Product Form Validation ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testProductFormValidationForNegativeInputs() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening login page");
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);

            System.out.println("Step 2: Performing login");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2000);

            List<ProductValidationScenario> scenarios = Arrays.asList(
                    new ProductValidationScenario("", "", ""),
                    new ProductValidationScenario("<script>alert(1)</script>", "abc", "Invalid title and price"),
                    new ProductValidationScenario(generateLongText(300), "-100", "Title too long and negative price"),
                    new ProductValidationScenario("ValidTitle", "abc123", "Non-numeric price")
            );

            for (ProductValidationScenario scenario : scenarios) {
                System.out.println("Step 3: Navigating to add product form for scenario: " + scenario.description);
                productPage.navigateToProducts();
                productPage.clickAddProduct();
                Thread.sleep(1500);

                productPage.enterProductTitle(scenario.title);
                productPage.enterBasePrice(scenario.price);
                productPage.enterDescription(scenario.description);
                Thread.sleep(1000);

                System.out.println("Step 4: Attempting to save invalid product");
                productPage.clickSaveProduct();
                Thread.sleep(1500);

                List<String> validationMessages = productPage.getValidationMessages();
                if (validationMessages.isEmpty()) {
                    throw new AssertionError("Expected validation messages for scenario: " + scenario.description);
                }

                System.out.println("Captured validation messages for scenario [" + scenario.description + "]: " + validationMessages);
                if (!productPage.isOnAddProductPage()) {
                    throw new AssertionError("Unexpected navigation away from add form for invalid input scenario: " + scenario.description);
                }
            }

            if (!productPage.isSaveButtonEnabled()) {
                System.out.println("Save button is disabled for invalid input scenarios, which is expected behavior");
            }

            System.out.println("==================== TEST PASSED ====================");
        } catch (Exception e) {
            System.err.println("==================== TEST FAILED ====================");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String generateLongText(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append('A');
        }
        return builder.toString();
    }

    private static class ProductValidationScenario {
        String title;
        String price;
        String description;

        ProductValidationScenario(String title, String price, String description) {
            this.title = title;
            this.price = price;
            this.description = description;
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
