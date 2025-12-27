# Testing Process

This document outlines the testing strategy, how to run tests, and how to view coverage reports for the Kulik app.

## Types of Tests

### Unit Tests
Unit tests are used to verify the logic of individual components in isolation.
- **Models**: Verified properties and data integrity.
- **Utilities**: Verified logic for date formatting, URL generation, etc.
- **ViewModels**: Verified initialization and state handling.

## Running Tests

To run all unit tests in the project, use the following Gradle command:

```bash
./gradlew test
```

## Test Coverage

We use **Kotlinx Kover** to measure test coverage. 

### Generating Reports
To generate an HTML coverage report, run:

```bash
./gradlew koverHtmlReport
```

### Viewing Reports
After running the command above, the report will be available at:
`app/build/reports/kover/html/index.html`

Open this file in your browser to see detailed coverage statistics for each class and method.

## Test Directory Structure

- **Unit Tests**: `app/src/test/java/com/agrohi/kulik/`
    - `model/`: Tests for data models.
    - `utils/`: Tests for utility classes.
    - `ui/screens/`: Tests for ViewModels.

## Best Practices
- Keep logic that needs testing in utility classes or ViewModels, away from Compose UI code.
- Always run tests before committing changes.
- Aim for high coverage in business logic and utility functions.
