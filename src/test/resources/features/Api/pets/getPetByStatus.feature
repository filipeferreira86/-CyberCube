@Api @Pet @PetCreated
Feature: Get list of pets by status
  As an API user I want to get a list of pets by status

  Background:
    Given I have the following pets
      | 1  | Oswaldo  | available |
      | 2  | Ronaldo  | pending   |
      | 3  | Athletic | sold      |

  Scenario Outline: Get list of pets by status
    When I send a GET request to get the list of pets with the status <status>
    Then the response status be a list of all pets with the status <status>
      And the list should contain the pet with the status <status>
      And the list should not contain the pet with the status <not_status>
    Examples:
      | status    | not_status |
      | available | pending    |
      | pending   | sold       |
      | sold      | available  |

  Scenario: Get list of pets by status with invalid status
    When I send a GET request to get the list of pets with the status "invalid"
    Then the response status be a list of all pets with the status "invalid"
      And the list should be empty