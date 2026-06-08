package utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CustomerPortalSnapshotData {
    private final List<String> categories;
    private final List<String> collections;
    private final Map<String, String> contactDetails;
    private final Map<String, String> accountDetails;

    private CustomerPortalSnapshotData(List<String> categories,
                                       List<String> collections,
                                       Map<String, String> contactDetails,
                                       Map<String, String> accountDetails) {
        this.categories = categories;
        this.collections = collections;
        this.contactDetails = contactDetails;
        this.accountDetails = accountDetails;
    }

    public static CustomerPortalSnapshotData fromHomePage(String pageText) {
        List<String> categories = extractMatches(pageText,
                "Salwar Sets", "Sarees", "Kurti", "Lehengas", "Salwar", "Saree", "Kurthi", "Lehenga");
        List<String> collections = extractMatches(pageText,
                "Cotton", "Georgette", "Linen", "Modal silk", "Wool");

        Map<String, String> contactDetails = new LinkedHashMap<>();
        contactDetails.put("address", extractValue(pageText, "Ground Floor, 1549/1, Nr Ravi Mandiram, Nellikunnu, Thrissur - 680005"));
        contactDetails.put("phone", extractValue(pageText, "+91 97457 47999"));
        contactDetails.put("email", extractValue(pageText, "enquiry@chrisrichardcreations.com"));

        return new CustomerPortalSnapshotData(categories, collections, contactDetails, new LinkedHashMap<>());
    }

    public static CustomerPortalSnapshotData fromAccountPage(String pageText) {
        Map<String, String> accountDetails = new LinkedHashMap<>();
        accountDetails.put("fullName", extractValue(pageText, "test ads"));
        accountDetails.put("phone", extractValue(pageText, "8989898989"));
        accountDetails.put("email", extractValue(pageText, "test@a.com"));
        accountDetails.put("addressLine1", extractValue(pageText, "test"));
        accountDetails.put("addressLine2", extractValue(pageText, "jdvsc"));
        accountDetails.put("city", extractValue(pageText, "pune"));
        accountDetails.put("state", extractValue(pageText, "maharashtra"));
        accountDetails.put("postalCode", extractValue(pageText, "410057"));
        accountDetails.put("country", extractValue(pageText, "India"));

        return new CustomerPortalSnapshotData(new ArrayList<>(), new ArrayList<>(), new LinkedHashMap<>(), accountDetails);
    }

    private static List<String> extractMatches(String pageText, String... candidates) {
        List<String> matches = new ArrayList<>();
        String lowerText = pageText.toLowerCase();
        for (String candidate : candidates) {
            if (lowerText.contains(candidate.toLowerCase())) {
                matches.add(candidate);
            }
        }
        return matches;
    }

    private static String extractValue(String pageText, String fallback) {
        return pageText.contains(fallback) ? fallback : "Not available";
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getCollections() {
        return collections;
    }

    public Map<String, String> getContactDetails() {
        return contactDetails;
    }

    public Map<String, String> getAccountDetails() {
        return accountDetails;
    }

    @Override
    public String toString() {
        return "CustomerPortalSnapshotData{" +
                "categories=" + categories +
                ", collections=" + collections +
                ", contactDetails=" + contactDetails +
                ", accountDetails=" + accountDetails +
                '}';
    }
}
