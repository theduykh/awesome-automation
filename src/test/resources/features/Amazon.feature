Feature: Amazon shopping

  Scenario: Amazon shopping
    Given the user is shopping at "https://www.amazon.com.au"
    When  the user goes to Today’s Deal
    And   the user sorts the items by "Discount - high to low"
    And   the user views Deal for the second item
    And   the user adds 2 items into the cart
    And   the user goes to the main page
    And   the user searches for "AAA Batteries"
    And   the user sorts the items by "Newest Arrivals"
    And   the user views an item in the search result
    And   the user adds 5 items into the cart
    And   the user goes to your cart
    Then  the cart displays quantities of items, prices of individual items and prices of combined items
    When  the user edits the first item quantity - set to "1"
    And   the user edits the second item quantity – set to "3"
    And   the user deletes the first item in the cart
    Then  the cart displays quantities of items, prices of individual items and prices of combined items
    When  the user clicks Proceed to Checkout button
    Then  the system requires a login
