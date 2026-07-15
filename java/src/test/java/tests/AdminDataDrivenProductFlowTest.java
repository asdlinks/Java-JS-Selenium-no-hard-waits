package tests;

import java.util.List;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.CustomerPage;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.CustomerPortalData;
import utils.DriverManager;
import utils.ProductBatchData;

public class AdminDataDrivenProductFlowTest {
    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    private static final int PRODUCT_COUNT = 5;

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;
    private DashboardPage dashboardPage;
    private CustomerPage customerPage;

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Admin Data-Driven Product Flow ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        dashboardPage = new DashboardPage(driver);
        customerPage = new CustomerPage(driver);
    }

    @Test
    public void testCreateAndValidateMultipleProductsFromUtilityData() throws InterruptedException {
        List<ProductBatchData.ProductSpec> products = ProductBatchData.getBatchProducts(PRODUCT_COUNT);

        try {
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();

            loginPage.login(USERNAME, PASSWORD);

            assertTrue("Admin login did not open the dashboard", driver.getCurrentUrl().contains("admin"));

            for (ProductBatchData.ProductSpec productSpec : products) {
                createAndValidateProduct(productSpec);
            }

            dashboardPage.logout();

            driver.get(CustomerPortalData.CUSTOMER_PORTAL_URL);
            driver.manage().deleteAllCookies();

            loginPage.login(CustomerPortalData.CUSTOMER_ID, CustomerPortalData.CUSTOMER_PASSWORD);

            assertTrue("Customer login did not open the customer portal", driver.getCurrentUrl().contains("test.chrisrichardcreations.com"));

            for (ProductBatchData.ProductSpec productSpec : products) {
                assertTrue("Product was not visible to customer: " + productSpec.getProductName(),
                        customerPage.isProductVisibleOnPortal(productSpec.getProductName()));
                System.out.println("Verified customer can see product: " + productSpec.getProductName());
            }

            System.out.println("Validated " + products.size() + " products from utility test data in admin and customer portal.");
            System.out.println("==================== TEST PASSED ====================");
        } catch (Exception e) {
            System.err.println("==================== TEST FAILED ====================");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createAndValidateProduct(ProductBatchData.ProductSpec productSpec) throws InterruptedException {
        productPage.navigateToProducts();

        productPage.clickAddProduct();

        productPage.selectCategory(productSpec.getCategory());
        productPage.selectSubcategory(productSpec.getSubCategory());

        productPage.enterProductTitle(productSpec.getProductName());
        productPage.enterBasePrice(productSpec.getBasePrice());
        productPage.enterDiscountedPrice(productSpec.getDiscountPrice());
        productPage.enterDescription(productSpec.getDescription());

        productPage.enterColor(productSpec.getColor());
        productPage.enterQuantity(productSpec.getQuantity());
        productPage.enterSize(productSpec.getSize());

        productPage.uploadImage(productSpec.getImagePath());

        productPage.clickSaveProduct();
        productPage.confirmSave();

        productPage.navigateToViewEditProducts();

        productPage.searchProduct(productSpec.getProductName());

        assertTrue("Product was not found after creation: " + productSpec.getProductName(),
                productPage.validateProductExists(productSpec.getProductName()));

        System.out.println("Verified product created successfully: " + productSpec.getProductName());
        productPage.clearSearch();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
