package com.exemple.steps;

import io.restassured.response.Response;

/**
 * Objet partagé entre les classes de steps via l'injection de dépendances Cucumber (PicoContainer).
 * Une instance est créée par scénario.
 */
public class ScenarioContext {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
