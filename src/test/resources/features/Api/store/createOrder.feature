@Api @Store @PetCreated
Feature: Create new order
  As an user I want to create a new order so that I can buy pets

  Background:
    Given I have a pet
      And I send a POST request to add the pet
      And the data to create a new order is prepared

  Scenario: Create new order
    When I create a new order
    Then the response should be 200
      And the order should be created

  Scenario: Create new order with invalid pet
    Given the pet does not exist
    When I create a new order
    Then the response should be 404
      And the order should not be created

  Scenario: Create new order with invalid quantity
    Given the quantity is invalid
    When I create a new order
    Then the response should be 400
      And the order should not be created