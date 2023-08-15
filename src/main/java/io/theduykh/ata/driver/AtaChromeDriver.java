package io.theduykh.ata.driver;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.*;

public class AtaChromeDriver implements AtaDriver {
    private final Logger logger = LogManager.getLogger();
    private final MutableCapabilities defaultCapabilities;
    private WebDriver driver;
    private final Map<String, WebDriver> driverMap = new HashMap<>();

    private final long TIMEOUT = 10;

    public AtaChromeDriver() {
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
        driver = new ChromeDriver(chromeOptions);
        driverMap.put("default", driver);
    }

    public AtaChromeDriver(MutableCapabilities capabilities) {
        this.defaultCapabilities = capabilities;
        driver = new RemoteWebDriver(capabilities);
        driverMap.put("default", driver);
    }

    // region actions
    @Override
    @Step("session \"{0}\"")
    public AtaChromeDriver session(String session) {
        logger.debug("session " + session);
        if (driverMap.containsKey(session)) {
            driver = driverMap.get(session);
        } else {
            driver = new RemoteWebDriver(defaultCapabilities);
            driverMap.put(session, driver);
        }

        return this;
    }

    @Override
    @Step("session \"{0}\"")
    public AtaChromeDriver session(String session, MutableCapabilities capabilities) {
        logger.debug("session " + session);
        if (driverMap.containsKey(session)) {
            driver = driverMap.get(session);
        } else {
            driver = new RemoteWebDriver(capabilities);
            driverMap.put(session, driver);
        }
        return this;
    }

    @Override
    @Step("navigate to \"{0}\"")
    public AtaChromeDriver navigate(String url) {
        logger.debug("navigate " + url);
        step(() -> driver.navigate().to(url));
        return this;
    }

    @Override
    @Step("fill \"{1}\" into \"{0}\"")
    public AtaChromeDriver fillText(String locator, String text) {
        logger.debug("fill " + text + " into " + locator);
        step(() -> findElement(locator).sendKeys(text));
        return this;
    }

    @Override
    @Step("click \"{0}\"")
    public AtaChromeDriver click(String locator) {
        logger.debug("click " + locator);
        step(() -> {
            By parsedLocator = parseLocator(locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(parsedLocator));
            driver.findElement(parsedLocator).click();
        });
        return this;
    }

    @Override
    @Step("select by visible text \"{0}\" \"{1}\"")
    public AtaChromeDriver selectByVisibleText(String locator, String text) {
        logger.debug("select by visible text " + locator);
        step(() -> {
            Select select = new Select(findElement(locator));
            select.selectByVisibleText(text);
        });
        return this;
    }

    @Override
    @Step("select by value \"{0}\" \"{1}\"")
    public AtaChromeDriver selectByValue(String locator, String value) {
        logger.debug("select by value " + locator);
        step(() -> {
            Select select = new Select(findElement(locator));
            select.selectByValue(value);
        });
        return this;
    }

    @Override
    @Step("see text \"{0}\"")
    public AtaChromeDriver see(String text) {
        logger.debug("see " + text);
        step(() -> {
            Assert.assertTrue(isElementVisible("//*[contains(.,'" + text + "')]"));
        });
        return this;
    }

    @Override
    @Step("see element \"{0}\"")
    public AtaValidation see(By locator) {
        logger.debug("see " + locator);
        return new AtaValidation(driver, locator);
    }

    @Override
    public AtaChromeDriver waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void quit() {
        driverMap.forEach((s, remoteWebDriver) -> {
            remoteWebDriver.quit();
        });
    }

    @Override
    @Step("Get text \"{0}\"")
    public String getText(String locator) {
        logger.debug("get text " + locator);
        Allure.addAttachment("screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        return findElement(locator).getText();
    }

    @Override
    public WebElement findElement(String locator) {
        By parsedLocator = parseLocator(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.visibilityOfElementLocated(parsedLocator));
        return driver.findElement(parsedLocator);
    }

    @Override
    public List<WebElement> findElements(String locator) {
        By parsedLocator = parseLocator(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(parsedLocator));
        } catch (WebDriverException e) {
            return new ArrayList<>();
        }
        return driver.findElements(parsedLocator);
    }

    // endregion actions

    private boolean isElementVisible(String locator) {
        By parsedLocator = parseLocator(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
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

}
