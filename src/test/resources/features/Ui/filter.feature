@ui
Feature: Apply order by filter

  As a user, I want to be able to apply an order by filter to the list of products so that
  I can sort the products by price, or name.

  Scenario Outline: Order by price
    Given that Im at the home page
    When I apply the order "<order>"
    Then the products should be sorted by "<order>"
    Examples:
      | order               |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |