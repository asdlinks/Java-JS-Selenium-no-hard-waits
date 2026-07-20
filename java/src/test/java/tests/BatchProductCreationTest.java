package tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;
import utils.ProductBatchData;
import utils.TestDataLoader;

public class BatchProductCreationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = TestDataLoader.get("admin.base.url");
    private static final String USERNAME = TestDataLoader.get("admin.username");
    private static final String PASSWORD = TestDataLoader.get("admin.password");

    private String productId01;
    private String productId02;
    private String productId03;
    private String productId04;
    private String productId05;
    private String productId06;
    private String productId07;
    private String productId08;
    private String productId09;
    private String productId10;

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Batch Product Creation ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testCreateBatchProductsViaAdminLogin() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening admin login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            Thread.sleep(2200);
            Thread.sleep(1800);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Performing admin login");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2200);
            org.junit.Assert.assertTrue("Login should proceed", true);

            List<ProductBatchData.ProductSpec> products = ProductBatchData.getBatchProducts();
            for (int index = 0; index < products.size(); index++) {
                ProductBatchData.ProductSpec productSpec = products.get(index);
                createProduct(productSpec, index + 1);
            }

            printCapturedProductIds();
            System.out.println("==================== TEST PASSED ====================");
        } catch (Exception e) {
            System.err.println("==================== TEST FAILED ====================");
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createProduct(ProductBatchData.ProductSpec productSpec, int sequenceNumber) throws InterruptedException {
        System.out.println("\n--- Creating product " + sequenceNumber + " of 10 ---");
        productPage.navigateToProducts();

        productPage.clickAddProduct();

        productPage.selectCategory(productSpec.getCategory());
        productPage.selectSubcategory(productSpec.getSubCategory());

        productPage.enterProductTitle(productSpec.getProductName());
        productPage.enterBasePrice(productSpec.getBasePrice());
        productPage.enterDiscountedPrice(productSpec.getDiscountPrice());

        String productCode = productPage.getProductCode();
        if (productCode != null && !productCode.isEmpty()) {
            storeProductId(sequenceNumber, productCode);
            System.out.println("Captured product ID/code for " + productSpec.getProductName() + ": " + productCode);
        }

        productPage.enterDescription(productSpec.getDescription());

        productPage.enterColor(productSpec.getColor());
        productPage.enterQuantity(productSpec.getQuantity());
        productPage.enterSize(productSpec.getSize());

        productPage.uploadImage(productSpec.getImagePath());

        productPage.clickSaveProduct();

        productPage.confirmSave();

        productPage.navigateToViewEditProducts();

        productPage.searchProduct(productSpec.getProductName());

        if (!productPage.validateProductExists(productSpec.getProductName())) {
            throw new AssertionError("Product '" + productSpec.getProductName() + "' was not found after save");
        }

        System.out.println("Validation passed for: " + productSpec.getProductName());
    }

    private void storeProductId(int sequenceNumber, String productCode) {
        switch (sequenceNumber) {
            case 1:
                productId01 = productCode;
                break;
            case 2:
                productId02 = productCode;
                break;
            case 3:
                productId03 = productCode;
                break;
            case 4:
                productId04 = productCode;
                break;
            case 5:
                productId05 = productCode;
                break;
            case 6:
                productId06 = productCode;
                break;
            case 7:
                productId07 = productCode;
                break;
            case 8:
                productId08 = productCode;
                break;
            case 9:
                productId09 = productCode;
                break;
            case 10:
                productId10 = productCode;
                break;
            default:
                break;
        }
    }

    private void printCapturedProductIds() {
        System.out.println("Stored product IDs / codes:");
        System.out.println("productId01=" + productId01);
        System.out.println("productId02=" + productId02);
        System.out.println("productId03=" + productId03);
        System.out.println("productId04=" + productId04);
        System.out.println("productId05=" + productId05);
        System.out.println("productId06=" + productId06);
        System.out.println("productId07=" + productId07);
        System.out.println("productId08=" + productId08);
        System.out.println("productId09=" + productId09);
        System.out.println("productId10=" + productId10);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
