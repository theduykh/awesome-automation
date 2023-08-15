package io.theduykh.ata.pages;

import io.theduykh.ata.entities.CredentialEntity;
import io.theduykh.ata.test.AtaPage;

public class CucumberLoginPage extends AtaPage {

    public CucumberLoginPage login(CredentialEntity credential) {
        I.fillText("#user_email", credential.getUsername());
        I.fillText("#user_password", credential.getPassword());
        I.click("form input[type='submit']");
        return this;
    }

    public String getErrorMessage() {
        return I.getText(".ht-alert__content p");
    }
}