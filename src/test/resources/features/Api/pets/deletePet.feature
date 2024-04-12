@Api @Pet @PetCreated
Feature: Delete pet

  As an user I want to delete a pet through the API

  Background:
    Given I have a pet
      And I send a POST request to add the pet

  Scenario: Delete a pet
      When I delete the pet
      Then the response should be 200
        And the pet should be deleted

  Scenario: Delete a non-existing pet
    When I delete a non-existing pet
    Then the response should be 404
