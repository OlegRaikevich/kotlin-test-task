package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UpdateTodoApiTest: BaseApiTest() {
    companion object {
        lateinit var testTodoData: Map<String, Any>

        @JvmStatic
        @BeforeAll
        fun setupTestData() {
            testTodoData = TestData.generateUniqueEntity()
        }
    }

    @Test
    fun `update todo`() { // Positive scenario for updating basic entity
        val uniqueId = testTodoData["id"]
        val updatedText = "Test Completed"

        val requestBody = """{
            "id": $uniqueId,
            "text": "$updatedText",
            "completed": true
        }"""

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", "$uniqueId")
            .body(requestBody)
            .put("/todos/{id}")
            .then()
            .statusCode(200)

        val todos = given()
            .contentType(ContentType.JSON)
            .get("/todos")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList("", Map::class.java)

        val updatedTodo = todos.find { it["id"] == uniqueId }
        assertTrue(updatedTodo != null)
        assertTrue(updatedTodo!!["completed"] == true)
        assertEquals(updatedText, updatedTodo["text"])
    }

    @Test
    fun `incorrect update todo`() { // Negative scenario, update nonexistent entity
        val uniqueId = testTodoData["id"]
        val randomString = testTodoData["text"]
        val requestBody = """{
            "id": $uniqueId,
            "text": "$randomString",
            "completed": true
        }"""

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", "${TestData.NONEXISTENTID}")
            .body(requestBody)
            .put("/todos/{id}")
            .then()
            .statusCode(404)
    }
}