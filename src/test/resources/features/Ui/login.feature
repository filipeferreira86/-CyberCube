@ui
Feature: Login
  As an user I want to be able to login the mai page using a valid login an password

  Scenario: Successful login
    Given that I have a valid username
      And a valid password
    When I try to login on the page
    Then the login is accepted
      And the home page is presented

  Scenario Outline: Failed login due to invalid username
    Given that I have a "<username>" username
      And a "<password>" password
    When I try to login on the page
    Then the login is rejected
     And the error message "<errorMessage>" is presented
    Examples:
      | username | password | errorMessage                                                              |
      | invalid  | valid    | Epic sadface: Username and password do not match any user in this service |
      | empty    | valid    | Epic sadface: Username is required                                        |
      | valid    | invalid  | Epic sadface: Username and password do not match any user in this service |
      | valid    | empty    | Epic sadface: Password is required                                        |

  Scenario: Close login error message
    Given that I have a "valid" username
      And a "empty" password
      And I try to login
      And the error message is presented
    When I click to close the error message
    Then the error message is not presented