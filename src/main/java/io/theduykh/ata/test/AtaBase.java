package io.theduykh.ata.test;

import io.theduykh.ata.driver.AtaDriver;
import io.theduykh.ata.driver.AtaDriverManager;

public abstract class AtaBase {
    protected AtaDriver I;

    public AtaBase() {
        I = AtaDriverManager.getDriver();
    }

}
