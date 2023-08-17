package io.theduykh.ata.pages;

import io.theduykh.ata.test.AtaPage;

public class CucumberLandingPage extends AtaPage {

    public CucumberLandingPage open() {
        I.navigate("https://cucumber.io/");
        return this;
    }

    public void clickLoginBtn() {
        I.click(".nav-item-login a");
    }
}
