package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final List<String> sharedSnapshots = new ArrayList<>();
    private static String lastSnapshotTag = "none";

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

            loginPage.login(CustomerPortalData.CUSTOMER_ID, CustomerPortalData.CUSTOMER_PASSWORD);

            CustomerPortalSnapshotData homeSnapshot = customerPage.captureHomePortalSnapshot();
            if (homeSnapshot.getCategories().isEmpty()) {
                System.out.println("Homepage category capture was empty; continuing anyway");
            }
            if (homeSnapshot.getCollections().isEmpty()) {
                System.out.println("Homepage collection capture was empty; continuing anyway");
            }
            assertFalse("Footer contact details should be captured", homeSnapshot.getContactDetails().isEmpty());

            System.out.println("Captured homepage categories: " + homeSnapshot.getCategories());
            System.out.println("Captured homepage collections: " + homeSnapshot.getCollections());
            System.out.println("Captured footer contact details: " + homeSnapshot.getContactDetails());

            driver.get("https://test.chrisrichardcreations.com/account");

            CustomerPortalSnapshotData accountSnapshot = customerPage.captureAccountPortalSnapshot();
            boolean shouldUseRandom = new Random().nextBoolean();
            if (shouldUseRandom) {
                sharedSnapshots.add("random-snapshot-" + System.currentTimeMillis());
            }
            lastSnapshotTag = accountSnapshot.getAccountDetails().containsKey("fullName") ? "fullName" : "missing";
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
