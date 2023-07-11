package stepdefinition;

import entities.CartEntity;
import entities.ItemEntity;
import entities.ProductEntity;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.theduykh.ata.test.AtaCucumberStepDefinition;
import org.theduykh.ata.test.AtaCucumberStepContext;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AmazonStepDefinition extends AtaCucumberStepDefinition {
    public AmazonStepDefinition(AtaCucumberStepContext context) {
        super(context);
    }

    @Given("the user is shopping at {string}")
    public void theUserIsShoppingAt(String arg0) {
        I.navigate(arg0);
        CartEntity cartEntity = new CartEntity();
        context.setContext("cart", cartEntity);
    }

    @When("the user goes to Today’s Deal")
    public void theUserGoesToTodaySDeal() {
        new HomePage().viewTodayDeals();
    }

    @And("the user sorts the items by {string}")
    public void theUserSortsTheItemsBy(String arg0) {
        new HomePage().sortBy(arg0);
    }

    @And("the user views Deal for the second item")
    public void theUserViewsDealForTheSecondItem() {
        new HomePage().openTheSecondDeal();
    }


    @And("the user adds {int} items into the cart")
    public void theUserAddsItemsIntoTheCart(int quantity) {
        ProductPage productPage = new ProductPage();
        productPage.setQuantity(quantity);

        ProductEntity product = new ProductEntity();
        product.setName(productPage.getProductName());
        product.setPrice(productPage.getCurrentPrice());

        productPage.addToCart();

        CartEntity cart = (CartEntity) context.getContext("cart");
        cart.addItem(product, quantity);
    }

    @And("the user goes to the main page")
    public void theUserGoesToTheMainPage() {
        new HomePage().backToMainPage();
    }

    @And("the user searches for {string}")
    public void theUserSearchesFor(String arg0) {
        new HomePage().search(arg0);
    }

    @And("the user views an item in the search result")
    public void theUserViewsAnItemInTheSearchResult() {
        new HomePage().openAnItem();
    }

    @And("the user goes to your cart")
    public void theUserGoesToYourCart() {
        new HomePage().goToCart();
    }

    @And("the user edits the first item quantity - set to {string}")
    public void theUserEditsTheFirstItemQuantitySetTo(String arg0) {
        new CartPage().editQuantity(2, Integer.parseInt(arg0));
        CartEntity cartEntity = (CartEntity) context.getContext("cart");
        cartEntity.getItemsList().get(0).setQuantity(Integer.parseInt(arg0));
    }

    @And("the user edits the second item quantity – set to {string}")
    public void theUserEditsTheSecondItemQuantitySetTo(String arg0) {
        new CartPage().editQuantity(1, Integer.parseInt(arg0));
        CartEntity cartEntity = (CartEntity) context.getContext("cart");
        cartEntity.getItemsList().get(1).setQuantity(Integer.parseInt(arg0));
    }

    @Then("the cart displays quantities of items, prices of individual items and prices of combined items")
    public void theCartDisplaysQuantitiesOfItemsPricesOfIndividualItemsAndPricesOfCombinedItems() {
        CartEntity cart = (CartEntity) context.getContext("cart");
        CartPage cartPage = new CartPage();
        List<ItemEntity> expectedItemList = cart.getItemsList();
        List<ItemEntity> actualItemList = cartPage.getItems();

        Assert.assertEquals(actualItemList.size(), expectedItemList.size(), "Number of item in the cart");
        double expectTotalPrice = 0;
        int expectTotalQuantity = 0;
        for (int i = 0; i < expectedItemList.size(); i++) {
            Assert.assertEquals(actualItemList.get(i).getProduct().getPrice(), expectedItemList.get(i).getProduct().getPrice(), "Product price");
            Assert.assertEquals(actualItemList.get(i).getQuantity(), expectedItemList.get(i).getQuantity(), "Item quantity");

            expectTotalPrice += expectedItemList.get(i).getProduct().getPrice() * expectedItemList.get(i).getQuantity();
            expectTotalQuantity += expectedItemList.get(i).getQuantity();
        }
        BigDecimal bd = BigDecimal.valueOf(expectTotalPrice);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        Assert.assertEquals(cartPage.getTotalPrice(),  bd.doubleValue(), "Subtotal price");
        Assert.assertEquals(cartPage.getTotalQuantity(), expectTotalQuantity, "Subtotal quantity");
    }

    @And("the user deletes the first item in the cart")
    public void theUserDeletesTheFirstItemInTheCart() {
        new CartPage().deleteItem(2);
        CartEntity cartEntity = (CartEntity) context.getContext("cart");
        cartEntity.getItemsList().remove(0);
    }

    @When("the user clicks Proceed to Checkout button")
    public void theUserClicksProceedToCheckoutButton() {
        new CartPage().proceedCheckout();
    }

    @Then("the system requires a login")
    public void theSystemRequiresALogin() {
        I.see("Sign-In");
        I.see(By.cssSelector("#login-form")).display();
    }


}
