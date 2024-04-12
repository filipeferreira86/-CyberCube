@Api @Pet @PetCreated
Feature: Get pet by id
  As an user I want to be able to query a pet by its id

  Background:
    Given I have a pet
    And I send a POST request to add the pet

  Scenario: Get pet by id
    When I send a GET request to get the pet by id
    Then the response should be 200
      And the response should contain the pet data

  Scenario: Get pet by invalid id
    When I send a GET request to get the pet by invalid id
    Then the response should be 404
      And the response should contain the error message "Pet not found"