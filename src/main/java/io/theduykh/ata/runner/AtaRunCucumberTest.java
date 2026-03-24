package io.theduykh.ata.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.theduykh.ata.config.ConfigReader;
import org.testng.annotations.DataProvider;

import java.io.IOException;


public abstract class AtaRunCucumberTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        try {
            ConfigReader.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.scenarios();
    }

}
