package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.containsString


class CreateTodoApiTest: BaseApiTest() {

    @Test
    fun `correct create todo`() { // Positive scenario for creation basic entity
        val requestBody = """{
            "id": ${TestData.uniqId},
            "text": "${TestData.randomString}",
            "completed": false
        }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
            .statusCode(201)
    }

    @Test
    fun `duplicate id create todo`() { // Negative scenario, create entity with existing id
        val requestBody = """{
            "id": ${TestData.uniqId},
            "text": "${TestData.randomString}",
            "completed": false
        }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
            .statusCode(400)
    }

    @Test
    fun `incorrect text create todo`() { // Negative scenario, create entity with incorrect type for text field
        val requestBody = """{
            "id": ${TestData.uniqId},
            "text": ${TestData.numberData},
            "completed": false
        }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
            .statusCode(400)
            .body(containsString("Request body deserialize error: invalid type: integer"))
    }

    @Test
    fun `empty body create todo`() { // Negative scenario, create entity with empty body
        val requestBody = """"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
            .statusCode(400)
            .body(containsString("Request body deserialize error: EOF while parsing"))
    }
}
