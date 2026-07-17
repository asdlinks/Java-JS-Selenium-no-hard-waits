package comparison;

import java.util.Objects;

public class GoodFlowA {
    public String normalize(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        return input.trim().toLowerCase();
    }

    public String buildLabel(String first, String second) {
        String left = normalize(first);
        String right = normalize(second);
        return Objects.equals(left, "") || Objects.equals(right, "")
                ? left + right
                : left + "-" + right;
    }
}
