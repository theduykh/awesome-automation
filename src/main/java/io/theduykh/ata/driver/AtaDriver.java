package io.theduykh.ata.driver;

import io.qameta.allure.Step;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AtaDriver {
    protected abstract boolean isDisplayed(String locator);
    protected abstract boolean isEnabled(String locator);

    public abstract WebDriver getWebDriver();

    public abstract AtaDriver session(String session);

    public abstract AtaDriver session(String session, MutableCapabilities capabilities);

    public abstract AtaDriver navigate(String url);

    public abstract AtaDriver fill(String locator, String text);

    public abstract AtaDriver click(String locator);

    public abstract AtaDriver selectByVisibleText(String locator, String text);

    public abstract AtaDriver selectByValue(String locator, String value);

    public abstract AtaDriver see(String message);

    public abstract AtaValidation expect(String locator);

    public abstract AtaDriver waitFor(long time);

    public abstract void quit();

    public abstract String getText(String locator);

    public abstract WebElement findElement(String locator);

    public abstract List<WebElement> findElements(String locator);
}
