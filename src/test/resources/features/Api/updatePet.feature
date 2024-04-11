Feature: Update pet
  As an API user I want to update a pet

  Scenario: Update a pet
    Given I have a pet with the following data:
      | 1 | dog | available |
    When I update the pet with the following data:
      | id | name | status | photoUrls   | tagsId | tagName | categoryId | categoryNam |
      | 1  | cat  | sold   | www.123.com | 2      | tagNew  | 2          | categoryNew |
    Then the pet should be updated with the following data:
      | id | name | status |
      | 1  | cat  | sold   |

  Scenario: Update a invalid pet
    When I update the pet with the following data:
      | id | name | status | photoUrls   | tagsId | tagName | categoryId | categoryNam |
      | 2  | cat  | sold   | www.123.com | 2      | tagNew  | 2          | categoryNew |
      Then the response should be 404

  Scenario: Update a pet with invalid data
    When I update the pet with invalid data
    Then the response should be 400

  Scenario: Update a pet with Form data
    Given I have a pet with the following data:
      | 1 | dog | available |
    When I update the pet with the following data through form data:
      | id | name | status |
      | 1  | cat  | sold   |
    Then the pet should be updated with the following data:
      | id | name | status |
      | 1  | cat  | sold   |
      And the response should be 200

  Scenario: Update a invalid pet with Form data
      When I update the pet with the following data through form data:
        | id | name | status |
        | 2  | "cat"  | sold   |
      Then the response should be 404

  Scenario: Update a invalid data with Form data
    Given I have a pet with the following data:
      | 1 | dog | available |
    When I update the pet with the following data through form data:
      | id | name | status |
      | 1  | ""   | sold   |
    Then the response should be 400