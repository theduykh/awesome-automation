package io.theduykh.ata.driver;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;

public class AtaValidationImpl extends AtaValidation {

    public AtaValidationImpl(AtaDriver driver, Object target) {
        super(driver, target);
    }

    @Override
    @Step
    public void visible() {
        try {
            Assert.assertFalse(driver.isDisplayed(target.toString()));
        } finally {
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setName("expect \"" + target + "\" to be visible"));
        }
    }

    @Override
    @Step
    public void invisible() {
        try {
            Assert.assertFalse(driver.isDisplayed(target.toString()));
        } finally {
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setName("expect \"" + target + "\" to be invisible"));
        }
    }

    @Override
    @Step
    public void hasText(String text) {
        try {
            Assert.assertEquals(driver.getText(target.toString()), text);
        } finally {
            Allure.getLifecycle().updateStep(stepResult -> stepResult.setName("expect \"" + target + "\" to has text \"" + text + "\""));
        }
    }
}
