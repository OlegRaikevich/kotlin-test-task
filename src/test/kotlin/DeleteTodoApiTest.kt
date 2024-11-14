package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

class DeleteTodoApiTest {
    @Test
    fun `test delete todo`() {
        val requestBody = """{
            "id": ${TestData.staticId},
        }"""

        given()
            .auth().basic("admin", "admin")
            .contentType(ContentType.JSON)
            .pathParam("id", "${TestData.staticId}")
            .body(requestBody)
            .delete("/todos/{id}")
            .then()
            .statusCode(204)
    }
}