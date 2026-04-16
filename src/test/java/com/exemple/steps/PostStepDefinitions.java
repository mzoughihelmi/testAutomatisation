package com.exemple.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class PostStepDefinitions {

    private final ScenarioContext context;

    public PostStepDefinitions(ScenarioContext context) {
        this.context = context;
    }

    @And("the post belongs to user {int}")
    public void thePostBelongsToUser(int expectedUserId) {
        Response reponse = context.getResponse();
        assertThat(reponse.jsonPath().getInt("userId"))
            .as("Post should belong to userId %d", expectedUserId)
            .isEqualTo(expectedUserId);
    }
}
