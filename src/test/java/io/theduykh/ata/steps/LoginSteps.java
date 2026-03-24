package io.theduykh.ata.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.theduykh.ata.pages.CucumberLandingPage;
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

    @When("I click Get Started")
    public void iClickGetStarted() {
        new CucumberLandingPage().clickLoginBtn();
    }

    @Then("the page navigates to documentation page")
    public void thePageNavigatesToDocumentationPage() {
        I.see("What is Cucumber?");
    }
}
