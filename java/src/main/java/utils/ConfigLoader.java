package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private Properties props = new Properties();

    public ConfigLoader(String path) {
        try {
            FileInputStream in = new FileInputStream(path);
            props.load(in);
        } catch (IOException e) {
            System.out.println("Using defaults because config could not be loaded: " + e.getMessage());
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
