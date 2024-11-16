package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class UpdateTodoApiTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupTestData() {
            TestData.generateUniqEntity()
        }
    }

    @Test
    fun `update todo`() {
        val updatedText = "Test Completed"

        val requestBody = """{
            "id": ${TestData.uniqId},
            "text": "$updatedText",
            "completed": true
        }"""

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", "${TestData.uniqId}")
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

        val updatedTodo = todos.find { it["id"] == TestData.uniqId }
        assertTrue(updatedTodo != null)
        assertTrue(updatedTodo!!["completed"] == true)
        assertEquals(updatedText, updatedTodo["text"])
    }

    @Test
    fun `incorrect update todo`() {
        val incorrectId = 0

        val requestBody = """{
            "id": ${TestData.uniqId},
            "text": "${TestData.randomString}",
            "completed": true
        }"""

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", "$incorrectId")
            .body(requestBody)
            .put("/todos/{id}")
            .then()
            .statusCode(404)
    }
}