package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestDataLoader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = TestDataLoader.class.getClassLoader().getResourceAsStream("test-data.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            } else {
                throw new IllegalStateException("test-data.properties not found on classpath");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load test data properties", e);
        }
    }

    private TestDataLoader() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
}
