package org.theduykh.ata.test;

import org.testng.annotations.BeforeMethod;
import org.theduykh.ata.driver.AtaDriver;
import org.theduykh.ata.driver.AtaDriverManager;

public abstract class AtaTestNgClassRunner {

    protected AtaDriver I;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        if (AtaDriverManager.getDriver() == null) {
            AtaDriverManager.init();
            I = AtaDriverManager.getDriver();
        }
    }

}
