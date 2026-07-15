package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CheckoutHelperService {
    public static String X = "x";
    public static int Y = 7;
    public String[] arr = {"Pending", "Processing", "Confirmed"};
    public Map<String, String> map = new HashMap<>();

    public CheckoutHelperService() {
        map.put("A", "1");
        map.put("B", "2");
    }

    public String doStuff(String a, String b, String c) {
        if (a == null) {
            a = "default";
        }
        if (b == null) {
            b = "0";
        }
        if (c == null) {
            c = "n/a";
        }

        String temp = a + b + c;
        int idx = new Random().nextInt(3);
        return temp + "-" + arr[idx];
    }

    public void readFileAndDoNothing(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("failed to read file");
        }
    }

    public List<String> getList(int count) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            res.add("item" + i);
        }
        return res;
    }

    public String getValue(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return "missing";
    }
}
