package todo.api.api

import io.restassured.RestAssured.given
import io.restassured.http.ContentType

object TestData {
    const val NUMBERDATA = 0 // numerical data for negative testcases
    const val NONEXISTENTID = 0 // id for nonexistent entity in app, for negative testcases

    private val usedIds = mutableSetOf<Int>()

    fun generateRandomId(): Int {
        var randomUniqId: Int
        do {
            randomUniqId = (100..999).random()
        } while (usedIds.contains(randomUniqId))
        usedIds.add(randomUniqId)
        return randomUniqId
    }

    fun generateRandomString(): String {
        return (1..20)
            .map { ('a'..'z').random() }
            .joinToString("")
    }

    fun generateUniqueEntity(): Map<String, Any> {
        val uniqueId = generateRandomId()
        val randomString = generateRandomString()
        val requestBody = """{
            "id": $uniqueId,
            "text": "$randomString",
            "completed": false
            }"""

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")

        return mapOf("id" to uniqueId, "text" to randomString)
    }

    fun generateEntities(todoQuantity: Int) { // create specified number of basic entities
        repeat(todoQuantity) {
            generateUniqueEntity()
        }
    }
}
