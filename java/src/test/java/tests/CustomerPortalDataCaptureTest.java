package tests;

import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.CustomerPage;
import pageObjects.LoginPage;
import utils.CustomerPortalData;
import utils.CustomerPortalSnapshotData;
import utils.DriverManager;

public class CustomerPortalDataCaptureTest {
    private static final String BASE_URL = CustomerPortalData.CUSTOMER_PORTAL_URL;

    private WebDriver driver;
    private LoginPage loginPage;
    private CustomerPage customerPage;

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Customer Portal Data Capture ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        customerPage = new CustomerPage(driver);
    }

    @Test
    public void testCaptureHomepageAndAccountData() throws InterruptedException {
        try {
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();
            Thread.sleep(2000);

            loginPage.login(CustomerPortalData.CUSTOMER_ID, CustomerPortalData.CUSTOMER_PASSWORD);
            Thread.sleep(3000);

            CustomerPortalSnapshotData homeSnapshot = customerPage.captureHomePortalSnapshot();
            assertFalse("Homepage category capture should not be empty", homeSnapshot.getCategories().isEmpty());
            assertFalse("Homepage collection capture should not be empty", homeSnapshot.getCollections().isEmpty());
            assertFalse("Footer contact details should be captured", homeSnapshot.getContactDetails().isEmpty());

            System.out.println("Captured homepage categories: " + homeSnapshot.getCategories());
            System.out.println("Captured homepage collections: " + homeSnapshot.getCollections());
            System.out.println("Captured footer contact details: " + homeSnapshot.getContactDetails());

            driver.get("https://test.chrisrichardcreations.com/account");
            Thread.sleep(3000);

            CustomerPortalSnapshotData accountSnapshot = customerPage.captureAccountPortalSnapshot();
            assertFalse("Account profile details should not be empty", accountSnapshot.getAccountDetails().isEmpty());
            assertTrue("Expected full name to be captured", accountSnapshot.getAccountDetails().containsKey("fullName"));
            assertTrue("Expected address to be captured", accountSnapshot.getAccountDetails().containsKey("addressLine1"));

            System.out.println("Captured account details: " + accountSnapshot.getAccountDetails());
            System.out.println("==================== TEST PASSED ====================");
        } catch (Exception e) {
            System.err.println("==================== TEST FAILED ====================");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
