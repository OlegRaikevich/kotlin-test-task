
# Kotlin API Test

This project contains automated tests written in Kotlin to verify the functionality of a TODO API application.

## Contents
- [Description](#description)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Running Load Tests](#running-load-tests)
- [Example Report Output](#example-report-output)
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

### Running Load Tests

A separate Gradle task `loadTest` is configured specifically for load testing, which runs the load test located in the `performance` package. This test measures the performance of the `POST /todos` endpoint under simulated load conditions.

To run the load test only:
```bash
./gradlew loadTest
```

After executing the load test, a performance summary report will be generated and saved in a file named `load_test_report.txt` in the project's root directory. This report includes metrics such as the total number of requests, success rate, failure rate, and response times (average, minimum, and maximum).

### Example Report Output

The `load_test_report.txt` will contain a summary similar to the following:

```
Performance summary for POST /todos:
Total requests: 100
Successful requests: 95 (95.0%)
Failed requests: 5 (5.0%)
Average response time: 120 ms
Min response time: 80 ms
Max response time: 300 ms
```

This file can be reviewed after each load test run to analyze performance metrics.


## Project Structure

```plaintext
kotlin-test-task/
├── src/
│   └── test/                            # Project tests
│       └── kotlin/                      # Tests written in Kotlin
│           ├── api/                     # Package for API tests
│           │   ├── BaseApiTest          # Parent class for all test classes, contains common setup and helper methods
│           │   ├── CreateTodoApiTest    # Tests the creation of TODO items via POST /todos endpoint
│           │   ├── GetListTodoApiTest   # Tests fetching TODO items, verifies response structure and data accuracy
│           │   ├── UpdateTodoApiTest    # Tests updating existing TODO items via PUT /todos/{id} endpoint
│           │   ├── DeleteTodoApiTest    # Tests deleting TODO items via DELETE /todos/{id} endpoint and ensures removal
│           │   ├── WebSocketTest        # Tests real-time notifications via WebSocket connection on new TODO creation
│           │   └── TestData             # Contains test data generation utilities and constants
│           └── performance/             # Package for performance and load testing
│               └── PostTodosLoadTest    # Load test for POST /todos endpoint, measures response times under load
├── build.gradle.kts                     # Gradle configuration file for Kotlin, includes dependencies and project settings
├── settings.gradle.kts                  # Gradle project settings, links modules and defines root project name
├── gradlew                              # Gradle Wrapper script (Unix), runs Gradle tasks without installing Gradle globally
├── gradlew.bat                          # Gradle Wrapper script (Windows)
└── README.md                            # Project documentation file, includes project overview, setup, and usage instructions
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
