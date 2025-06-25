Feature: Cucumber Landing Page Test

  Scenario: Login with an invalid credential
    Given I am on Cucumber landing page
    When I click Get Started
    Then the page navigates to documentation page