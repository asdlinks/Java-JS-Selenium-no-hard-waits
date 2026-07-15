package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.CustomerPage;
import pageObjects.LoginPage;
import utils.DriverManager;

public class CustomerTermsAndConditionsTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private CustomerPage customerPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/login";
    private static final String CUSTOMER_ID = "8989898989";
    private static final String CUSTOMER_PASSWORD = "Test@a123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Customer Login and Terms & Conditions ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        customerPage = new CustomerPage(driver);
    }

    @Test
    public void testCustomerLoginAndTermsConditions() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening customer login page");
            driver.get(BASE_URL);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Performing customer login with ID: " + CUSTOMER_ID);
            loginPage.login(CUSTOMER_ID, CUSTOMER_PASSWORD);

            System.out.println("Step 3: Clicking on Terms & Conditions button at the bottom of the page");
            customerPage.clickTermsAndConditions();

            System.out.println("Step 4: Verifying Terms & Conditions page is opened");
            if (!customerPage.isTermsAndConditionsPageOpened()) {
                throw new AssertionError("Terms & Conditions page did not open");
            }

            System.out.println("Step 5: Checking the page title");
            String pageTitle = customerPage.getPageTitle();
            System.out.println("Page Title: " + pageTitle);
            
            if (pageTitle == null || pageTitle.isEmpty()) {
                throw new AssertionError("Page title is empty or null");
            }
            
            boolean isTitleValid = pageTitle.toLowerCase().contains("terms") || pageTitle.toLowerCase().contains("condition");
            if (!isTitleValid) {
                throw new AssertionError("Page title does not contain expected Terms & Conditions text. Title: " + pageTitle);
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
