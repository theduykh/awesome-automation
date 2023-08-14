package io.theduykh.ata.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.theduykh.ata.test.AtaStepContext;
import io.theduykh.ata.test.AtaStepDefinition;

public class ApplePenSteps extends AtaStepDefinition {
    public ApplePenSteps(AtaStepContext context) {
        super(context);
    }

    @Given("I have a pen")
    public void iHaveAPen() {
        System.out.println("Pen...");
    }

    @And("I have an apple")
    public void iHaveAnApple() {
        System.out.println("Uhh");
    }

    @When("I ahh")
    public void iAhh() {
        System.out.println("Ahh!!!");
    }

    @Then("I see an Apple pen")
    public void iSeeAnApplePen() {
        System.out.println("Apple Pen");
    }

}
