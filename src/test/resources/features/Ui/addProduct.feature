@ui
Feature: Add product to the cart
  As an user I want to be able to add items to my cart and buy them

  Background:
    Given that Im at the home page

  Scenario: Add 1 item to cart through home page
    When I click on add to cart on the 1th item
      And I go to the cart page
    Then the item is presented with correct data

  Scenario: Add 2 random items to cart through home page
    When Add 2 random items
      And I go to the cart page
    Then the items are presented with correct data

  Scenario: Add a item to cart through item page
    When I access the 1st item page
      And I add the item to the cart
      And I go to the cart page
    Then the item is presented with correct data