package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test


class CreateTodoApiTest: BaseApiTest() {

    val randomId = (1..100).random()

    @Test
    fun `test create todo`() {
        val requestBody = """{
            "id": ${TestData.staticId},
            "text": "do something",
            "completed": false
        }"""

        // check creation status
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
                .statusCode(201)

        // negative check duplicate creation
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
            .statusCode(409)
    }

}