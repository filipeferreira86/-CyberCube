@Api @Store @PetCreated
Feature: Delete Order
  As an user I want to delete an order so that I can remove it from the system

    Background:
      Given I have a pet
        And I send a POST request to add the pet
        And the data to create a new order is prepared
        And I send a POST request to add the order

  Scenario: Delete an order
    When I delete the order
    Then the response should be 200

    Scenario: Delete an order that does not exist
      Given I have an order that does not exist
      When I delete the order
      Then the response should be 404