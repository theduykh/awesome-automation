package io.theduykh.ata.driver;

import io.theduykh.ata.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;

public class AtaDriverManager {
    private static final Logger logger = LogManager.getLogger();
    private static final ThreadLocal<AtaDriver> threadDriver = new ThreadLocal<>();

    public static void init() {
        boolean dryRun = ConfigReader.getBoolean("dryrun");
        AtaDriver driver;
        if (dryRun) {
            driver = new AtaDriverDry();
            logger.debug("init thread driver for dry run");
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--window-size=1366,768");
            chromeOptions.addArguments("--disable-infobars");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            chromeOptions.setExperimentalOption("useAutomationExtension", false);
            chromeOptions.setAcceptInsecureCerts(true);

            if (ConfigReader.getBoolean("headless")) {
                chromeOptions.addArguments("--headless=new");
            }

            driver = new AtaDriverImpl(chromeOptions);
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
