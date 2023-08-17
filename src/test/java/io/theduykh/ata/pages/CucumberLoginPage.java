package io.theduykh.ata.pages;

import io.theduykh.ata.entities.CredentialEntity;
import io.theduykh.ata.test.AtaPage;

public class CucumberLoginPage extends AtaPage {

    public CucumberLoginPage login(CredentialEntity credential) {
        I.fill("#user_email", credential.getUsername());
        I.fill("#user_password", credential.getPassword());
        I.click("form input[type='submit']");
        return this;
    }

    public String getErrorMessage() {
        return I.getText(".ht-alert__content p");
    }
}
