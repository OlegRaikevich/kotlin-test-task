package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

class ChangeTodoApiTest {
    @Test
    fun `test change todo`() {
        val requestBody = """{
            "id": ${TestData.staticId},
            "text": "do something else",
            "completed": true
        }"""

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", "$${TestData.staticId}")
            .body(requestBody)
            .put("/todos/{id}")
            .then()
            .statusCode(200)
    }
}