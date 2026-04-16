Feature: REST API - Post Management
  As an API consumer
  I want to manage posts
  In order to validate the posts endpoints are working

  Background:
    Given the API is available at "https://jsonplaceholder.typicode.com"

  @smoke
  Scenario: Retrieve the list of posts
    When I send a GET request to "/posts"
    Then the response status is 200
    And the response contains a non-empty list

  @smoke
  Scenario: Retrieve a single post by id
    When I send a GET request to "/posts/1"
    Then the response status is 200
    And the field "id" equals 1
    And the field "title" is not empty
    And the post belongs to user 1

  @regression
  Scenario: Create a new post
    When I send a POST request to "/posts" with body:
      """
      {
        "title": "My new post",
        "body": "This is the content of the post",
        "userId": 1
      }
      """
    Then the response status is 201
    And the field "title" equals "My new post"
