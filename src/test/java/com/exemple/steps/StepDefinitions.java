package com.exemple.steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {

    private final ScenarioContext context;

    public StepDefinitions(ScenarioContext context) {
        this.context = context;
    }

    @Given("the API is available at {string}")
    public void theApiIsAvailableAt(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        Response response = given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .when()
            .get(endpoint)
            .then()
            .extract()
            .response();
        context.setResponse(response);
    }

    @When("I send a POST request to {string} with body:")
    public void iSendAPostRequestTo(String endpoint, String body) {
        Response response = given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(body)
            .when()
            .post(endpoint)
            .then()
            .extract()
            .response();
        context.setResponse(response);
    }

    @Then("the response status is {int}")
    public void theResponseStatusIs(int expectedStatus) {
        assertThat(context.getResponse().getStatusCode())
            .as("Expected HTTP status: %d", expectedStatus)
            .isEqualTo(expectedStatus);
    }

    @And("the response contains a non-empty list")
    public void theResponseContainsANonEmptyList() {
        assertThat(context.getResponse().jsonPath().getList("$"))
            .as("Response should be a non-empty list")
            .isNotEmpty();
    }

    @And("the field {string} equals {int}")
    public void theFieldEqualsInt(String field, int expectedValue) {
        assertThat(context.getResponse().jsonPath().getInt(field))
            .as("Field '%s'", field)
            .isEqualTo(expectedValue);
    }

    @And("the field {string} equals {string}")
    public void theFieldEqualsString(String field, String expectedValue) {
        assertThat(context.getResponse().jsonPath().getString(field))
            .as("Field '%s'", field)
            .isEqualTo(expectedValue);
    }

    @And("the field {string} is not empty")
    public void theFieldIsNotEmpty(String field) {
        assertThat(context.getResponse().jsonPath().getString(field))
            .as("Field '%s' should not be empty", field)
            .isNotBlank();
    }
}
