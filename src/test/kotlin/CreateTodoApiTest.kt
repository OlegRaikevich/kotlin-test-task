package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.containsString


class CreateTodoApiTest: BaseApiTest() {

    @Test
    fun `positive test create todo`() {
        val requestBody = """{
            "id": ${TestData.staticId},
            "text": "do something",
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
    fun `duplicate id create todo`() {
        val requestBody = """{
            "id": ${TestData.staticId},
            "text": "do something",
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
    fun `incorrect id create todo`() {
        val randomNumber = (1..100).random()

        val requestBody = """{
            "id": ${TestData.staticId},
            "text": $randomNumber,
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
    fun `empty body create todo`() {
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
