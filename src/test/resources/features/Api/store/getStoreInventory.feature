@Api @Store @PetCreated
Feature: Get store inventory

  As an user I want to get the inventory of a store so that I can see
  what products are available in the store.

  Background:
    Given that I have the number of pets by status in the store

  Scenario: Get store inventory
    When I request the inventory of the store
    Then the response should be 200
      And the response should contain the number of pets by status