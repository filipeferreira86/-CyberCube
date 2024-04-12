@Api @User
Feature: Create new users

  As an admin I want to create new users so that they can use the system

  Background:
    Given the data to create a new user is prepared

  Scenario: Create a new user
    When I create a new user
    Then the response should be 200
      And the user should be created

  Scenario: Create a duplicated user
    Given I create a new user
    When I create a new user with same data
    Then the response should be 400

  Scenario: Create a new user with missing data
    When I create a new user with missing data
    Then the response should be 400

  Scenario: Create a new user with missing username
    When I create a new user with missing username
    Then the response should be 400