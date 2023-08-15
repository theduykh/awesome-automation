package io.theduykh.ata.test;

import io.theduykh.ata.driver.AtaDriver;
import org.testng.annotations.BeforeMethod;
import io.theduykh.ata.driver.AtaDriverManager;

public abstract class AtaTestNGRunner {

    protected AtaDriver I;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        if (AtaDriverManager.getDriver() == null) {
            AtaDriverManager.init();
            I = AtaDriverManager.getDriver();
        }
    }

}
