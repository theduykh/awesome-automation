package io.theduykh.ata.driver;

import io.qameta.allure.Step;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

class AtaDriverDry extends AtaDriver {

    @Override
    public WebDriver getWebDriver() {
        return null;
    }

    @Override
    @Step("session \"{0}\"")
    public AtaDriver session(String session) {
        return null;
    }

    @Override
    @Step("session \"{0}\"")
    public AtaDriver session(String session, MutableCapabilities capabilities) {
        return null;
    }

    @Override
    @Step("navigate to \"{0}\"")
    public AtaDriver navigate(String url) {
        return null;
    }

    @Override
    @Step("fill \"{1}\" into \"{0}\"")
    public AtaDriver fill(String locator, String text) {
        return null;
    }

    @Override
    @Step("click \"{0}\"")
    public AtaDriver click(String locator) {
        return null;
    }

    @Override
    @Step("select by visible text \"{0}\" \"{1}\"")
    public AtaDriver selectByVisibleText(String locator, String text) {
        return null;
    }

    @Override
    @Step("select by value \"{0}\" \"{1}\"")
    public AtaDriver selectByValue(String locator, String value) {
        return null;
    }

    @Override
    @Step("see text \"{0}\"")
    public AtaDriver see(String message) {
        return null;
    }

    @Override
    public AtaValidation expect(String locator) {
        return new AtaValidationDry(this, locator);
    }

    @Override
    public AtaDriver waitFor(long time) {
        return null;
    }

    @Override
    @Step("Get text \"{0}\"")
    public String getText(String locator) {
        return null;
    }

    @Override
    public WebElement findElement(String locator) {
        return null;
    }

    @Override
    public List<WebElement> findElements(String locator) {
        return null;
    }


    @Override
    public void quit() {

    }

    @Override
    protected boolean isDisplayed(String locator) {
        return false;
    }

    @Override
    protected boolean isEnabled(String locator) {
        return false;
    }
}
