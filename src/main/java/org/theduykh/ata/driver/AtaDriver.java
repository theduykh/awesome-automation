package org.theduykh.ata.driver;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.util.*;

public class AtaDriver {
    private final Logger logger = LogManager.getLogger(AtaDriver.class);
    private MutableCapabilities defaultCapabilities;
    private RemoteWebDriver driver;
    private final Map<String, RemoteWebDriver> driverMap = new HashMap<>();

    private final long TIMEOUT = 10;

    public AtaDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1366,768");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.setAcceptInsecureCerts(true);
        defaultCapabilities = chromeOptions;
        driver = new RemoteWebDriver(defaultCapabilities);
        driverMap.put("default", driver);
    }

    public AtaDriver(MutableCapabilities capabilities) {
        this.defaultCapabilities = capabilities;
        driver = new RemoteWebDriver(capabilities);
        driverMap.put("default", driver);
    }

    // region actions
    @Step("session \"{0}\"")
    public AtaDriver session(String session) {
        logger.debug("session " + session);
        if (driverMap.containsKey(session)) {
            driver = driverMap.get(session);
        } else {
            driver = new RemoteWebDriver(defaultCapabilities);
            driverMap.put(session, driver);
        }

        return this;
    }

    @Step("session \"{0}\"")
    public AtaDriver session(String session, MutableCapabilities capabilities) {
        logger.debug("session " + session);
        if (driverMap.containsKey(session)) {
            driver = driverMap.get(session);
        } else {
            driver = new RemoteWebDriver(capabilities);
            driverMap.put(session, driver);
        }
        return this;
    }

    @Step("navigate")
    public AtaDriver navigate(String url) {
        logger.debug("navigate " + url);
        step(() -> driver.navigate().to(url));
        return this;
    }

    @Step("fillText")
    public AtaDriver fillText(String locator, String text) {
        logger.debug("fillText " + text + " into " + locator);
        step(() -> findElement(locator).sendKeys(text));
        return this;
    }

    @Step("click")
    public AtaDriver click(String locator) {
        logger.debug("click " + locator);
        step(() -> {
            By parsedLocator = parseLocator(locator);
            WebDriverWait wait = new WebDriverWait(driver, TIMEOUT, 500);
            wait.until(ExpectedConditions.elementToBeClickable(parsedLocator));
            driver.findElement(parsedLocator).click();
        });
        return this;
    }

    @Step("select by visible text \"{0}\" \"{1}\"")
    public AtaDriver selectByVisibleText(String locator, String text) {
        logger.debug("select by visible text " + locator);
        step(() -> {
            Select select = new Select(findElement(locator));
            select.selectByVisibleText(text);
        });
        return this;
    }

    @Step("select by value \"{0}\" \"{1}\"")
    public AtaDriver selectByValue(String locator, String value) {
        logger.debug("select by value " + locator);
        step(() -> {
            Select select = new Select(findElement(locator));
            select.selectByValue(value);
        });
        return this;
    }

    @Step("see message \"{0}\"")
    public AtaDriver see(String message) {
        logger.debug("see " + message);
        step(() -> {
            Assert.assertTrue(isElementVisible("//*[contains(.,'" + message + "')]", TIMEOUT));
        });
        return this;
    }

    public AtaDriver waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void quit() {
        driverMap.forEach((s, remoteWebDriver) -> {
            remoteWebDriver.quit();
        });
    }

    @Step("Get text \"{0}\"")
    public String getText(String locator) {
        logger.debug("get text " + locator);
        Allure.addAttachment("screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return findElement(locator).getText();
    }

    public WebElement findElement(String locator) {
        By parsedLocator = parseLocator(locator);
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT, 500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(parsedLocator));
        return driver.findElement(parsedLocator);
    }

    public List<WebElement> findElements(String locator) {
        By parsedLocator = parseLocator(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, TIMEOUT, 500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(parsedLocator));
        } catch (WebDriverException e) {
            return new ArrayList<>();
        }
        return driver.findElements(parsedLocator);
    }

    // endregion actions

    private boolean isElementVisible(String locator, long timeout) {
        By parsedLocator = parseLocator(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout, 500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(parsedLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private By parseLocator(String locator) {
        String temp = locator;
        while (temp.startsWith("(")) {
            temp = temp.substring(1);
        }
        if (temp.startsWith("/") || temp.startsWith("./")) {
            return By.xpath(locator);
        } else {
            return By.cssSelector(locator);
        }
    }

    private void step(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            logger.error("", e);
            throw e;
        } finally {
            Allure.addAttachment("screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }

    private MutableCapabilities fireFoxCapability() {
        MutableCapabilities capabilities = new FirefoxOptions();
        capabilities.setCapability("seleniumHost", "http://localhost:4444/wd/hub/");
        return capabilities;
    }

    private MutableCapabilities chromeCapability() {
        MutableCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("seleniumHost", "http://localhost:4444/wd/hub/");
        return capabilities;
    }

}
