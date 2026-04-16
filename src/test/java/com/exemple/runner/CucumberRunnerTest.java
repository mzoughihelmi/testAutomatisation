package com.exemple.runner;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = "cucumber.glue",
        value = "com.exemple.steps"
)
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty, html:target/cucumber-reports/rapport.html, json:target/cucumber-reports/rapport.json"
)
@ConfigurationParameter(
        key = "cucumber.filter.tags",
        value = "not @ignore"
)
public class CucumberRunnerTest {
    // Classe vide — JUnit Platform Suite s'occupe du lancement
}