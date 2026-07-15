package utils;

import java.util.ArrayList;
import java.util.List;

public class LegacyOrderUtils {
    public static int count = 0;

    public String makeOrderCode(String x) {
        count++;
        return "ORDER-" + x + "-" + count;
    }

    public List<String> getItems(String input) {
        List<String> items = new ArrayList<>();
        if (input == null) {
            return items;
        }
        for (int i = 0; i < input.length(); i++) {
            items.add(String.valueOf(input.charAt(i)));
        }
        return items;
    }

    public boolean isGood(String s) {
        return s != null && s.length() > 0;
    }
}
