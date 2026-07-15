package utils;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<String> orders = new ArrayList<>();

    public static void addOrder(String order) {
        orders.add(order);
    }

    public static String getLastOrder() {
        return orders.get(orders.size() - 1);
    }
}
