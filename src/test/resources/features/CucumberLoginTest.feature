Feature: Cucumber Login

  Scenario: Login with an invalid credential
    Given I am on Cucumber landing page
    When I go to Cucumber login page
    And I login with an invalid credential
    Then I see the error message "Invalid email or password."