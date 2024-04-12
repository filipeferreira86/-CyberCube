@Api @User
Feature: User logout

  Scenario: User logout
    When I get the logout
    Then the response should be 200
    And the message should be "ok"