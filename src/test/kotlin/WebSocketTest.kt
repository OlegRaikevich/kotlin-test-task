package todo.api

import okhttp3.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import com.google.gson.JsonParser
import io.restassured.RestAssured.given
import io.restassured.http.ContentType

class WebSocketTest: BaseApiTest() {

    private val client = OkHttpClient()

    private val uniqueId = TestData.generateRandomId()
    private val randomString = TestData.generateRandomString()

    private val requestBody = """{
            "id": $uniqueId,
            "text": "$randomString",
            "completed": false
        }"""

    private var expectedMassage = """
        {
            "data": {
                "completed": false,
                "id": $uniqueId,
                "text": "$randomString"
            },
            "type": "new_todo"
        }
        """

    @Test
    fun `websocket connection and getting massage` () {
        val latch = CountDownLatch(1)
        var messageReceived: String? = null

        val listener = object : WebSocketListener() { // override event handlers for

            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("Connection opened")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("Message received: $text")
                messageReceived = text
                latch.countDown()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("WebSocket error: ${t.message}")
                latch.countDown()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("WebSocket closed: $code, $reason")
            }
        }

        val request = Request.Builder().url("ws://localhost:8080/ws").build()
        val webSocket = client.newWebSocket(request, listener)

        latch.await(5, TimeUnit.SECONDS)

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post("/todos")
            .then()
            .statusCode(201)

        webSocket.close(1000, "Test completed")
        client.dispatcher.executorService.shutdown()

        val expectedMassageAsJson = JsonParser.parseString(expectedMassage).asJsonObject
        val messageReceivedAsJson = JsonParser.parseString(messageReceived).asJsonObject
        assertEquals(expectedMassageAsJson, messageReceivedAsJson)
    }

}