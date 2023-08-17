package io.theduykh.ata.driver;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class AtaValidationDry extends AtaValidation {


    public AtaValidationDry(AtaDriver driver, Object target) {
        super(driver, target);
    }

    @Override
    @Step
    public void visible() {
        Allure.getLifecycle().updateStep(stepResult -> stepResult.setName("expect \"" + target + "\" to be visible"));
    }

    @Override
    @Step
    public void invisible() {
        Allure.getLifecycle().updateStep(stepResult -> stepResult.setName("expect \"" + target + "\" to be visible"));
    }

    @Override
    @Step
    public void hasText(String text) {
        Allure.getLifecycle().updateStep(stepResult -> stepResult.setName("expect \"" + target + "\" to has text \"" + text + "\""));
    }
}
