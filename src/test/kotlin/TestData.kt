package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType

object TestData {

    val uniqId: Int = (100..999).random()
    const val NONEXISTENTID = 0

    val randomString: String = (1..20)
        .map { ('a'..'z').random() }
        .joinToString("")

    private fun generateRandomId(): Int {
        var randomUniqId: Int
        do {
            randomUniqId = (100..999).random()
        } while (randomUniqId == uniqId)

        return randomUniqId
    }

    fun generateUniqEntity() {
        val requestBody = """{
            "id": $uniqId,
            "text": "$randomString",
            "completed": false
            }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
    }

    fun generateEntities(todoQuantity: Int) {

        repeat(todoQuantity) {
            val requestBody = """{
            "id": ${generateRandomId()},
            "text": "$randomString",
            "completed": false
            }"""

            given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/todos")
        }
    }
}
