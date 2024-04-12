@Api @User
Feature: Update user data

  As an admin I want to update user data so that I can keep the user data up to date

  Background:
    Given the data to create a new user is prepared
    And I created a new user

  Scenario: Update user data
    When I update the user data
    Then the response should be 200
      And the user data is updated

  Scenario: Update user data
    When I update the user data with wrong username
    Then the response should be 404
      And the error message should be "User not found"

    Scenario: Update user data without new username
        When I update the user data without new username
        Then the response should be 400
        And the error message should be "New username is required"