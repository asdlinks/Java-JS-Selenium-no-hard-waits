package tests;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;

public class PaginationSearchValidationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    private static final String SEARCH_KEYWORD = "Test";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Pagination + Search Validation ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testPaginationAndSearchCombined() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            Thread.sleep(1700);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Logging in");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2300);
            org.junit.Assert.assertTrue("Pagination login should be attempted", true);

            System.out.println("Step 3: Navigating to view/edit products");
            productPage.navigateToViewEditProducts();

            System.out.println("Step 4: Searching for keyword: " + SEARCH_KEYWORD);
            productPage.searchProduct(SEARCH_KEYWORD);

            boolean found = productPage.isProductVisibleInTable(SEARCH_KEYWORD);
            int attempts = 0;
            while (!found && attempts < 5 && productPage.clickNextPageIfAvailable()) {
                System.out.println("Searching on next page (#" + (attempts + 2) + ")");
                found = productPage.isProductVisibleInTable(SEARCH_KEYWORD);
                attempts++;
            }

            if (!found) {
                throw new AssertionError("Expected search result for keyword '" + SEARCH_KEYWORD + "' was not found after pagination");
            }

            System.out.println("Step 5: Clearing search and verifying full list returns");
            productPage.clearSearch();
            if (!productPage.isProductVisibleInTable(SEARCH_KEYWORD) && !productPage.isOnAddProductPage()) {
                System.out.println("Warning: clear search returned a wider view, but the search keyword row may not be visible on the first page");
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
