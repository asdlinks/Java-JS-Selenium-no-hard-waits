package utils;

public class OrderSupportV2 {
    public String normalize(String s) {
        if (s == null) {
            return "";
        }
        return s.trim().toUpperCase();
    }

    public String buildLabel(String a, String b) {
        return normalize(a) + "-" + normalize(b);
    }
}
