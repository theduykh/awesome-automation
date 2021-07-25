package org.theduykh.ata.test;

import org.theduykh.ata.driver.AtaDriver;
import org.theduykh.ata.driver.AtaDriverManager;

public abstract class AtaBase {
    protected AtaDriver I;

    public AtaBase() {
        I = AtaDriverManager.getDriver();
    }

}
