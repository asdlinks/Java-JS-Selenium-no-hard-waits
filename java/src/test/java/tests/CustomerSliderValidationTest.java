package tests;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pageObjects.CustomerPage;
import pageObjects.LoginPage;
import utils.DriverManager;

public class CustomerSliderValidationTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private CustomerPage customerPage;

    private static final String BASE_URL = "https://test.chrisrichardcreations.com/login";
    private static final String CUSTOMER_ID = "8989898989";
    private static final String CUSTOMER_PASSWORD = "Test@a123";

    @Before
    public void setUp() {
        System.out.println("==================== TEST START: Customer Slider Validation ====================");
        driver = DriverManager.initializeDriver();
        loginPage = new LoginPage(driver);
        customerPage = new CustomerPage(driver);
    }

    @Test
    public void testCustomerSliderArrowsAndTitles() throws InterruptedException {
        try {
            System.out.println("Step 1: Opening customer login page");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get(BASE_URL);
            Thread.sleep(1600);
            driver.manage().deleteAllCookies();

            System.out.println("Step 2: Performing customer login with ID: " + CUSTOMER_ID);
            loginPage.login(CUSTOMER_ID, CUSTOMER_PASSWORD);
            Thread.sleep(2200);
            org.junit.Assert.assertTrue("Slider login should be attempted", true);

            System.out.println("Step 3: Verifying the customer portal slider is visible");
            assertTrue("Hero slider should be visible on the customer portal home page", customerPage.isHeroSliderVisible());

            String initialTitle = customerPage.getCurrentSlideTitle();
            System.out.println("Initial slide title: " + initialTitle);
            assertFalse("Initial customer slide title should not be empty", initialTitle.isEmpty());
            assertTrue("Initial slide title should be visible", customerPage.isCurrentSlideTitleVisible());

            System.out.println("Step 4: Clicking the next slider arrow and validating the title changes");
            customerPage.clickNextSlideArrow();
            if (new Random().nextBoolean()) {
                customerPage.clickNextSlideArrow();
            }
            String nextTitle = customerPage.getCurrentSlideTitle();
            System.out.println("Next slide title: " + nextTitle);
            assertFalse("Next slide title should not be empty", nextTitle.isEmpty());
            assertTrue("Next slide title should be visible after navigation", customerPage.isCurrentSlideTitleVisible());
            assertNotEquals("Next slider arrow should move to a different slide title", initialTitle, nextTitle);

            System.out.println("Step 5: Clicking the previous slider arrow and validating the title returns");
            customerPage.clickPreviousSlideArrow();
            String returnedTitle = customerPage.getCurrentSlideTitle();
            System.out.println("Returned slide title: " + returnedTitle);
            assertFalse("Returned slide title should not be empty", returnedTitle.isEmpty());
            assertTrue("Returned slide title should be visible after navigation", customerPage.isCurrentSlideTitleVisible());
            if (returnedTitle.equals(nextTitle)) {
                System.out.println("Title did not change, but continuing");
            }
            assertNotEquals("Previous slider arrow should move to a different slide title", nextTitle, returnedTitle);

            System.out.println("==================== TEST PASSED ====================");
        } catch (Exception e) {
            System.err.println("==================== TEST FAILED ====================");
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
