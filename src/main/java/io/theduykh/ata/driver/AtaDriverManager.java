package io.theduykh.ata.driver;

import io.theduykh.ata.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AtaDriverManager {
    private static final Logger logger = LogManager.getLogger();
    private static final ThreadLocal<AtaDriver> threadDriver = new ThreadLocal<>();

    public static void init() {
        boolean dryRun = ConfigReader.getBoolean("dryrun");
        AtaDriver driver;
        if (dryRun) {
            driver = new AtaDryRunDriver();
            logger.debug("init thread driver for dry run");
        } else {
            driver = new AtaChromeDriver();
            logger.debug("init thread driver");
        }
        threadDriver.set(driver);
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
