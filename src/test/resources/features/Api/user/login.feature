@Api @User
Feature: Check login User

  As an user I want to be able to check I'm logged in

  Background:
    Given the data to create a new user is prepared
      And I created a new user

  Scenario: Check if I'm is logged in
    When I check if the user is logged in
    Then the response should be 200
      And I should see that the user is logged in

  @run
  Scenario: Check if user with wrong password
      When I check if the user is logged in using a wrong password
      Then the response should be 401
        And the error message should be "Unauthorized"

  @run
  Scenario: Check if user is logged in using the wrong username
      When I check if the user is logged in using a wrong username
      Then the response should be 404
        And the error message should be "Not Found"