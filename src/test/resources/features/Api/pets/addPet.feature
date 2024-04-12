@Api @Pet @PetCreated
Feature: Add new pet
  As a user I want to add a new pet to the database through an api call.

  Scenario: Add a new pet
    Given I have a pet
    When I send a POST request to add the pet
    Then the response should be 200
      And the response should contain the data for the pet
      And the pet should be added to the database

  Scenario: Add a new pet with a missing required field
    Given I have a pet with a missing required field
    When I send a POST request to add the pet
    Then the response should be 400
    And the response should contain an error message

  Scenario: Add a new pet with a invalid status
    Given I have a pet with a invalid status
    When I send a POST request to add the pet
    Then the response should be 400
    And the response should contain an error message