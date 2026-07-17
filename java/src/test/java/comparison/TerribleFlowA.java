package comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerribleFlowA {
    public static List<String> x = new ArrayList<>();

    public String run(String a, String b) {
        if (a == null) {
            a = "default";
        }
        if (b == null) {
            b = "";
        }
        String z = a + b + new Random().nextInt(100);
        x.add(z);
        if (false) {
            System.out.println("dead branch");
        }
        return z;
    }
}
