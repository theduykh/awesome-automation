package io.theduykh.ata.utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final String TEST_PROPERTIES = "test.properties";

    private static final Properties properties = new Properties();

    public static void init() throws IOException {
        properties.load(Resources.getResource(TEST_PROPERTIES).openStream());
    }

    public static String getString(String key) {
        String systemProp = System.getProperty(key);
        if (systemProp != null) {
            return systemProp;
        } else {
            return properties.getProperty(key);
        }
    }

    public static boolean getBoolean(String key) {
        return "true".equals(getString(key));
    }
}
