package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

class GetListTodoApiTest {
    @Test
    fun `test get todo list`() {
        val requestBody = """{
                "offset": 0,
                "limit": 10
        }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .get("/todos")
            .then()
            .statusCode(200)
    }
}