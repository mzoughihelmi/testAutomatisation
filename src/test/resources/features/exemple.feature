Feature: REST API - User Management
  As an API consumer
  I want to retrieve the list of users
  In order to validate the endpoint is working

  Background:
    Given the API is available at "https://jsonplaceholder.typicode.com"

  @smoke
  Scenario: Retrieve the list of users
    When I send a GET request to "/users"
    Then the response status is 200
    And the response contains a non-empty list
