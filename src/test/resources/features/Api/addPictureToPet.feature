@Api
Feature: Add picture for a pet
  As a user I want to be able to add a picture for a pet through an api call

  Scenario: Add a picture for a pet
    Given I have a pet with id 1
    When I add a picture for the pet with id 1
    Then the pet with id 1 should have a picture
      And the response should be 200

  Scenario: Add a picture for a pet that does not exist
    Given I have a pet with id 1
    When I add a picture for the pet with id 4896
    Then the response should be 404

  Scenario: Add a picture for a pet with an invalid picture
    Given I have a pet with id 1
    When I add a picture for the pet with id 1 with an invalid picture
    Then the response should be 400