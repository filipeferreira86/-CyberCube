@ui
Feature: Checkout
  As a user I want to be able to checkout my cart so that I can pay for my items

  Background:
    Given Im logged in
      And the 1 item is in the cart
      And that Im on the cart page

  Scenario: Check checkout overview with correct data
    When I click on the checkout button
      And I fill name with "testName"
      And I fill last name with "testLastName"
      And I fill zip-postal code with "123456"
      And I click on the continue button
    Then I should see the checkout overview page
      And I should see the item data
      And I should see the Payment Information
      And I should see the Shipping Information
      And I should see the tax price
      And I should see the total price

  Scenario Outline: Check error message on checkout form page
    When I click on the checkout button
      And I fill name with "<firstName>"
      And I fill last name with "<LastName>"
      And I fill zip-postal code with "<zipCode>"
      And I click on the continue button
    Then error message should be displayed "<errorMessage>"
    Examples:
      | firstName | LastName     | zipCode | errorMessage                   |
      |           | testLastName | 123456  | Error: First Name is required  |
      | testName  |              | 123456  | Error: Last Name is required   |
      | testName  | testLastName |         | Error: Postal Code is required |

  Scenario: Check checkout overview with correct data
    When I click on the checkout button
      And I fill name with "testName"
      And I fill last name with "testLastName"
      And I fill zip-postal code with "123456"
      And I click on the continue button
      And I click on the finish button
    Then I should see the checkout complete page

  Scenario: Go back to the cart page from the checkout form page
    When I click on the checkout button
      And I fill name with "testName"
      And I fill last name with "testLastName"
      And I fill zip-postal code with "123456"
      And I click on the cancel button
    Then I should see the cart page


  @run
  Scenario: Go back to the home page from the checkout overview page
    When I click on the checkout button
      And I fill name with "testName"
      And I fill last name with "testLastName"
      And I fill zip-postal code with "123456"
      And I click on the continue button
      And I click on the cancel button
    Then I should see the home page

