@ui
Feature: Reset site
  As a user I want to be able to reset the site to its original state

  Background:
    Given that Im at the home page
      And the 1 item is in the cart

  Scenario: Reset site
    When I click the reset button
    Then the item 1 should be ready to be added to the cart
      And the cart should be empty