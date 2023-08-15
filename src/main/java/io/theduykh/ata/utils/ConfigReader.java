package io.theduykh.ata.utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    public static void init() throws IOException {
        properties.load(Resources.getResource("").openStream());
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return "true".equals(properties.getProperty(key));
    }
}
