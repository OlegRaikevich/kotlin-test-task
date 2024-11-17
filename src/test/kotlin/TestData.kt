package todo.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType

object TestData {
    const val numberData = 0 // numerical data for negative testcases
    const val NONEXISTENTID = 0 // id for nonexistent entity in app, for negative testcases

    val uniqId: Int = (100..999).random()

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

    fun generateEntities(todoQuantity: Int) { // create specified number of basic entities

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

    fun createTodo() { // create basic entity
        val requestBody = """{
            "id": ${TestData.uniqId},
            "text": "${TestData.randomString}",
            "completed": false
        }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
    }
}
