package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class GetListTodoApiTest: BaseApiTest() {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupTestData() {
            TestData.generateEntities(6)
        }
    }

    @Test
    fun `test get todo list`() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("limit", "5")
            .queryParam("offset", "0")
            .get("/todos")
            .then()
            .statusCode(200)
            .body("size()", equalTo(5))
            .body(matchesJsonSchemaInClasspath("schemas/todo-list.json"))
    }

    @Test
    fun `incorrect limit param`() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("limit", "")
            .queryParam("offset", "0")
            .get("/todos")
            .then()
            .statusCode(400)
            .body(containsString("Invalid query string"))
    }

    @Test
    fun `incorrect offset param`() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("limit", "1")
            .queryParam("offset", "")
            .get("/todos")
            .then()
            .statusCode(400)
            .body(containsString("Invalid query string"))
    }
}