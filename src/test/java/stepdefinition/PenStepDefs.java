package stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.theduykh.ata.test.AtaCucumberStepContext;
import org.theduykh.ata.test.AtaCucumberStepDefinition;

public class PenStepDefs extends AtaCucumberStepDefinition {
    public PenStepDefs(AtaCucumberStepContext context) {
        super(context);
    }

    @Given("I have a pen")
    public void iHaveAPen() {
        System.out.println("I have a pen");
    }

    @And("I have an apple")
    public void iHaveAnApple() {
        System.out.println("I have an apple");
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
