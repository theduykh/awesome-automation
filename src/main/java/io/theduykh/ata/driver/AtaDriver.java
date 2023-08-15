package io.theduykh.ata.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface AtaDriver {

    AtaDriver session(String session);

    AtaDriver session(String session, MutableCapabilities capabilities);

    AtaDriver navigate(String url);

    AtaDriver fillText(String locator, String text);

    AtaDriver click(String locator);

    AtaDriver selectByVisibleText(String locator, String text);

    AtaDriver selectByValue(String locator, String value);

    AtaDriver see(String message);

    AtaValidation see(By locator);

    AtaDriver waitFor(long time);

    void quit();

    String getText(String locator);

    WebElement findElement(String locator);

    List<WebElement> findElements(String locator);
}
