@ui
Feature: Remove product from cart

Background:
  Given that Im at the home page

  Scenario: Remove item from the cart page
    Given the 1 item is in the cart
      And that Im on the cart page
    When I click to remove the item
    Then the item is removed

  Scenario: Remove 1 item from the cart page that has 2 items
    Given the 1 item is in the cart
      And the 2 item is in the cart
      And that Im on the cart page
    When I click to remove the item 1
    Then the item is removed

  Scenario: Remove item from the home page
    Given the 1 item is in the cart
    When I click to remove the item 1 at the home page
      And I go to the cart page
    Then cart should be empty

  Scenario: Remove item from the item page
    Given the 1 item is in the cart
    And Im on 1 item page
    When I click to remove the item at the item page
    And I go to the cart page
    Then cart should be empty