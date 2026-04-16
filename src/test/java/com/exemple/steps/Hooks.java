package com.exemple.steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class Hooks {

    @BeforeAll
    public static void avantTousLesScenarios() {
        // Activer les logs globaux RestAssured (utile en CI)
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        System.out.println(">>> Démarrage de la suite de tests Cucumber + RestAssured");
    }

    @Before
    public void avantChaqueScenario(Scenario scenario) {
        System.out.println("--- Début : " + scenario.getName());
    }

    @After
    public void apresChaqueScenario(Scenario scenario) {
        System.out.println("--- Fin : " + scenario.getName()
            + " [" + scenario.getStatus() + "]");
    }

    @AfterAll
    public static void apresTousLesScenarios() {
        RestAssured.reset();
        System.out.println(">>> Fin de la suite de tests Cucumber + RestAssured");
    }
}
