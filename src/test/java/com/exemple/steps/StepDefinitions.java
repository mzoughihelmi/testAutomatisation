package com.exemple.steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {

    private Response reponse;

    @Given("the API is available at {string}")
    public void theApiIsAvailableAt(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        reponse = given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .when()
            .get(endpoint)
            .then()
            .extract()
            .response();
    }
//
    @Then("the response status is {int}")
    public void theResponseStatusIs(int expectedStatus) {
        assertThat(reponse.getStatusCode())
            .as("Expected HTTP status: %d", expectedStatus)
            .isEqualTo(expectedStatus);
    }

    @And("the response contains a non-empty list")
    public void theResponseContainsANonEmptyList() {
        assertThat(reponse.jsonPath().getList("$"))
            .as("Response should be a non-empty list")
            .isNotEmpty();
    }
}
