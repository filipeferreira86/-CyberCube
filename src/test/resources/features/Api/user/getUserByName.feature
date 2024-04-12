@Api @User
Feature: Get user by username

  Background:
    Given the data to create a new user is prepared
    And I created a new user

  Scenario: Get user by username
    When I request the user by username
    Then the response should be 200
      And the user should have the correct data

    Scenario: Get user by username that doesn't exist
      When I request the user by username that doesnt exist
      Then the response should be 404
        And the error message should be "User not found"