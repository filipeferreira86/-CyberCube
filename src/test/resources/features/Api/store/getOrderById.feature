@Api @Store @PetCreated
Feature: Get order By Id

  As an user I want to get an order by Id so that I can see the details of the order.

  Background:
    Given I have a pet
    And I send a POST request to add the pet
    And the data to create a new order is prepared
    And I send a POST request to add the order

  Scenario: Get order by Id
    When I get the order by Id
    Then I should see the order details
      And the response should be 200

    Scenario: Get order by invalid Id
      Given I have an invalid order Id
      When I get the order by Id
      Then the response should be 404
        And the error message should be "Order not found"
