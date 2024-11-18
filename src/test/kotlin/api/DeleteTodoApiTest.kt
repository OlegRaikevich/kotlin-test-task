package todo.api.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.Base64

class DeleteTodoApiTest: BaseApiTest() {
    companion object {
        lateinit var testTodoData: Map<String, Any>

        @JvmStatic
        @BeforeAll
        fun setupTestData() {
            testTodoData = TestData.generateUniqueEntity()
        }
    }

    private val credentials = "admin:admin"
    private val encodedCredentials = Base64.getEncoder().encodeToString(credentials.toByteArray())

    @Test
    fun `correct delete todo`() { // Positive scenario for deleting basic entity
        val uniqueId = testTodoData["id"]
        given()
            .header("Authorization", "Basic $encodedCredentials")
            .contentType(ContentType.JSON)
            .pathParam("id", "$uniqueId")
            .delete("/todos/{id}")
            .then()
            .statusCode(204)

        val todos = given()
            .contentType(ContentType.JSON)
            .get("/todos")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getList("", Map::class.java)

        val deletedTodoExists = todos.any { it["id"] == uniqueId }
        assertFalse(deletedTodoExists)
    }

    @Test
    fun `delete nonexistent todo`() { // Negative scenario, deleting nonexistent entity
        given()
            .header("Authorization", "Basic $encodedCredentials")
            .contentType(ContentType.JSON)
            .pathParam("id", "${TestData.NONEXISTENTID}")
            .delete("/todos/{id}")
            .then()
            .statusCode(404)
    }

    @Test
    fun `incorrect authorization`() { // Negative scenario, deleting without authorization
        val uniqueId = testTodoData["id"]
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", "$uniqueId")
            .delete("/todos/{id}")
            .then()
            .statusCode(401)
    }
}