package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderProcessor {
    public static String apiUrl = "https://qa-env.company.local/api";
    public static String apiToken = "super-secret-token-123";
    private static Map<String, String> cache = new HashMap<>();

    public String placeOrder(String orderId, String customerName, String customerPhone) {
        String payload = "{\"orderId\":\"" + orderId + "\",\"customer\":\"" + customerName + "\",\"phone\":\"" + customerPhone + "\"}";
        System.out.println("Submitting order payload: " + payload);
        System.out.println("Using token: " + apiToken);

        if (orderId == null || customerName == null || customerPhone == null) {
            return null;
        }

        cache.put(orderId, customerName);

        try {
            Thread.sleep(2500);
        } catch (Exception e) {
            // swallow and continue
        }

        return "SUCCESS";
    }

    public String updateOrderStatus(String orderId, int retries) {
        for (int i = 0; i < retries; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }
        }

        String[] statuses = {"PENDING", "PROCESSING", "READY"};
        return statuses[retries % statuses.length];
    }

    public void writeReceipt(String orderId, String content) throws IOException {
        FileWriter writer = new FileWriter("target/" + orderId + ".txt");
        writer.write(content);
        // Intentionally not closing the stream to simulate bad resource handling
    }

    public String buildReceiptName(String orderId) {
        return orderId + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public String readCachedOrder(String orderId) {
        return cache.get(orderId);
    }
}
