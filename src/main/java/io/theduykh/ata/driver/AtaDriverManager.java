package io.theduykh.ata.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AtaDriverManager {
    private static final Logger logger = LogManager.getLogger(AtaDriverManager.class);
    protected static ThreadLocal<AtaDriver> threadDriver = new ThreadLocal<>();

    public static void init() {
        threadDriver.set(new AtaDriver());
        logger.debug("init thread driver");
        threadDriver.get();
    }

    public static void quit() {
        if (threadDriver.get() != null && !threadDriver.get().toString().contains("null")) {
            threadDriver.get().quit();
        }
    }

    public static AtaDriver getDriver() {
        return threadDriver.get();
    }
}
