
# Kotlin API Test

This project contains automated tests written in Kotlin to verify the functionality of a TODO API application.

## Contents
- [Description](#description)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Test cases](#test-cases)

## Description

This project is written in Kotlin and uses **JUnit 5** for running tests and **OkHttp** for WebSocket and **RestAssured** HTTP requests. The tests are designed to verify the functionality of the API and WebSocket connection for the TODO application.

## Requirements

- **JDK 11+**
- **Gradle** (optional, as Gradle Wrapper is included)
- Internet connection (for downloading dependencies on the first run)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/OlegRaikevich/kotlin-test-task.git
   cd kotlin-test-task
   ```

2. Update dependencies using Gradle Wrapper:
   ```bash
   ./gradlew build
   ```

## Running Tests

To run all tests, use the command:
```bash
./gradlew test
```

To run tests with detailed output:
```bash
./gradlew test --info
```

## Project Structure

```plaintext
kotlin-test-task/
├── src/
│   └── test/                        # Project tests
│       └── kotlin/                  # Tests written in Kotlin
│           ├── BaseApiTest          # Parent class for all test classes, contains common setup and helper methods
│           ├── CreateTodoTest       # Tests the creation of TODO items via POST /todos endpoint
│           ├── GetTodoTest          # Tests fetching TODO items, verifies response structure and data accuracy
│           ├── UpdateTodoTest       # Tests updating existing TODO items via PUT /todos/{id} endpoint
│           ├── DeleteTodoTest       # Tests deleting TODO items via DELETE /todos/{id} endpoint and ensures removal
│           ├── WebSocketTest        # Tests real-time notifications via WebSocket connection on new TODO creation
├── build.gradle.kts                 # Gradle configuration file for Kotlin, includes dependencies and project settings
├── settings.gradle.kts              # Gradle project settings, links modules and defines root project name
├── gradlew                          # Gradle Wrapper script (Unix), runs Gradle tasks without installing Gradle globally
├── gradlew.bat                      # Gradle Wrapper script (Windows)
└── README.md                        # Project documentation file, includes project overview, setup, and usage instructions
```

## Test cases

1. **POST TODO Create**:
   - Ensures all fields are populated with valid data.
   - Tests with some required fields missing.
   - Tests with no fields populated.
   - Validates data in each field (both valid and invalid values).
   - Tests with an empty JSON payload.

2. **GET TODO List Retrieval**:
   - Retrieves a limited number of TODOs based on a set limit.
   - Verifies that the returned data structure matches the expected format.
   - Ensures an empty list is returned when an invalid or non-existent offset is provided.

3. **PUT Update TODO**:
   - Updates a TODO with valid data.
   - Tests updating with a non-existent ID.
   - Tests updating with an invalid ID.
   - Tests partial updates with some fields missing in the JSON payload.

4. **DELETE TODO**:
   - Deletes an existing TODO item.
   - Tests deletion with a non-existent ID.
   - Tests deletion with an invalid authorization credentials.

5. **WebSocket**:
   - Checks successful connection.
   - Checks receiving messages.

---
