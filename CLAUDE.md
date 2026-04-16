# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run all tests
mvn test

# Run tests for a specific tag only
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"

# Run a single feature file
mvn test -Dcucumber.features="classpath:features/posts.feature"

# Run tests + generate HTML report (target/cucumber-rapport/)
mvn verify
```

## Architecture

### Tech stack
- **Java 21**, Maven
- **Cucumber 7** + JUnit Platform Suite for BDD test execution
- **RestAssured 5** for HTTP calls
- **PicoContainer** for dependency injection between step classes
- **AssertJ** for assertions

### Project layout

```
src/test/
├── java/com/exemple/
│   ├── runner/CucumberRunnerTest.java   # JUnit Suite entry point
│   └── steps/
│       ├── ScenarioContext.java         # Shared state injected per scenario
│       ├── StepDefinitions.java         # Generic HTTP steps (GET, POST, assertions)
│       ├── PostStepDefinitions.java     # Steps specific to the posts feature
│       └── Hooks.java                  # @Before/@After + RestAssured logging filters
└── resources/
    ├── cucumber.properties             # Cucumber config (glue, plugins, features path)
    └── features/
        ├── exemple.feature             # Users API scenarios
        └── posts.feature               # Posts API scenarios
```

### Shared state between step classes

`ScenarioContext` is injected by PicoContainer into every step class constructor. It holds the last `Response` object so that assertion steps in any class can access the response set by action steps in another class. PicoContainer creates one `ScenarioContext` instance per scenario automatically.

### Adding a new feature

1. Create `src/test/resources/features/<name>.feature`
2. If the feature needs steps not in `StepDefinitions.java`, create `src/test/java/com/exemple/steps/<Name>StepDefinitions.java` with a `ScenarioContext` constructor parameter
3. Generic steps (HTTP verbs, status code, field assertions) are already available in `StepDefinitions`

### Cucumber configuration

Configuration lives in two places — keep them consistent:
- `src/test/resources/cucumber.properties` — used at runtime
- `@ConfigurationParameter` annotations in `CucumberRunnerTest.java` — used by the JUnit suite launcher

Reports are written to `target/cucumber-reports/` (JSON + HTML).
