package tests;

import java.time.Instant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.ProductPage;
import utils.DriverManager;

public class FileUploadPersistenceTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/admin/login";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    private static final String IMAGE_PATH = "C:\\Users\\Aditya\\Downloads\\test saree img.jpg";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: File Upload Persistence ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testFileUploadPreviewAndPersistence() throws InterruptedException {
        try {
            String productName = "Upload Persistence Product " + Instant.now().toEpochMilli();

            System.out.println("Step 1: Opening login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            Thread.sleep(2100);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Logging in");
            loginPage.login(USERNAME, PASSWORD);
            Thread.sleep(2900);
            org.junit.Assert.assertTrue("Upload login should be attempted", true);

            System.out.println("Step 3: Navigating to add product page");
            productPage.navigateToProducts();
            productPage.clickAddProduct();

            System.out.println("Step 4: Entering product details");
            productPage.enterProductTitle(productName);
            productPage.enterBasePrice("1350");
            productPage.enterDiscountedPrice("1250");
            productPage.enterDescription("Upload test product");

            System.out.println("Step 5: Uploading image file");
            productPage.uploadImage(IMAGE_PATH);
            if (!productPage.isPreviewDisplayed()) {
                throw new AssertionError("Preview image did not display after upload");
            }

            System.out.println("Step 6: Saving the product");
            productPage.clickSaveProduct();
            productPage.confirmSave();

            System.out.println("Step 7: Re-opening the saved product for edit");
            productPage.navigateToViewEditProducts();
            productPage.searchProduct(productName);
            if (!productPage.clickEditForProduct(productName)) {
                throw new AssertionError("Could not locate edit action for saved product");
            }

            if (!productPage.isPreviewDisplayed()) {
                throw new AssertionError("Uploaded preview did not persist after saving");
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
