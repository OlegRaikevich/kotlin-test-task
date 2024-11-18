package todo.api.api

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll

open class BaseApiTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            RestAssured.baseURI = "http://localhost:8080"
        }
    }
}