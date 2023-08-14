package io.theduykh.ata.steps;

import io.theduykh.ata.entities.CredentialEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.theduykh.ata.pages.CucumberLandingPage;
import io.theduykh.ata.pages.CucumberLoginPage;
import org.testng.Assert;
import io.theduykh.ata.test.AtaStepContext;
import io.theduykh.ata.test.AtaStepDefinition;

public class LoginSteps extends AtaStepDefinition {
    public LoginSteps(AtaStepContext context) {
        super(context);
    }

    @Given("I am on Cucumber landing page")
    public void iAmOnCucumberLandingPage() {
        new CucumberLandingPage().open();
    }

    @When("I go to Cucumber login page")
    public void iGoToCucumberLoginPage() {
        new CucumberLandingPage().clickLoginBtn();
    }

    @And("I login with an invalid credential")
    public void iLoginWithAnInvalidCredential() {
        CredentialEntity credential = CredentialEntity.builder()
                .username("testne@gmail.com")
                .password("12345678@")
                .build();
        new CucumberLoginPage().login(credential);
    }

    @Then("I see the error message {string}")
    public void iSeeTheErrorMessage(String expect) {
        String message = new CucumberLoginPage().getErrorMessage();
        Assert.assertEquals(message, expect);
    }
}
