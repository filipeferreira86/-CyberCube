@Api @User
Feature: Delete users

  As an Admin I want to delete users so that they can no longer access the system

  Background:
    Given the data to create a new user is prepared
      And I created a new user

  Scenario: Delete a user
    When I delete the user
    Then the response should be 200
      And the user is deleted

  Scenario: Delete a user that does not exist
    When I delete a user that does not exist
    Then the response should be 404