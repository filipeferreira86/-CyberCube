@ui
Feature: Logout
  As a user I want to be able to logout of the application so that
  I can keep my account secure.

  Background:
    Given that Im at the home page

  Scenario: User logs out
    When I logout
    Then I should be redirected to the login page

  Scenario: User tries to access a page after logging out
    When I logout
      And I try to access the home page
    Then I should be redirected to the login page
      And the error message "Epic sadface: You can only access '/inventory.html' when you are logged in." is presented
