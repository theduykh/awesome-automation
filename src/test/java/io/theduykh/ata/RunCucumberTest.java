package io.theduykh.ata;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "pretty"},
        glue = "io.theduykh.ata.steps"
)
public class RunCucumberTest extends AtaRunCucumberTest {

}
