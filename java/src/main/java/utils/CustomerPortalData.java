package utils;

public final class CustomerPortalData {
    private CustomerPortalData() {
    }

    public static final String CUSTOMER_PORTAL_URL = TestDataLoader.get("customer.portal.url", "https://test.chrisrichardcreations.com/login");
    public static final String CUSTOMER_ID = TestDataLoader.get("customer.username", "8989898989");
    public static final String CUSTOMER_PASSWORD = TestDataLoader.get("customer.password", "Test@a123");
}
