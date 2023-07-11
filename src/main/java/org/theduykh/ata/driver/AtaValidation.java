package org.theduykh.ata.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AtaValidation {

    private final WebDriver driver;
    private final Object target;

    public AtaValidation(WebDriver driver, Object target) {
        this.driver = driver;
        this.target = target;
    }

    public void display() {
        if (target instanceof String) {
            driver.findElement(By.xpath(target.toString())).isDisplayed();
        } else if (target instanceof By) {
            driver.findElement((By) target).isDisplayed();
        } else if (target instanceof WebElement) {
            ((WebElement) target).isDisplayed();
        }
    }
}
