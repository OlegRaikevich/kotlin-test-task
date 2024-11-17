package todo.api

import okhttp3.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import com.google.gson.JsonParser

class WebSocketTest: BaseApiTest() {

    private val client = OkHttpClient()
    private var expectedMassage = """
        {
            "data": {
                "completed": false,
                "id": ${TestData.uniqId},
                "text": "${TestData.randomString}"
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

        TestData.createTodo()

        webSocket.close(1000, "Test completed")
        client.dispatcher.executorService.shutdown()

        val expectedMassageAsJson = JsonParser.parseString(expectedMassage).asJsonObject
        val messageReceivedAsJson = JsonParser.parseString(messageReceived).asJsonObject
        assertEquals(expectedMassageAsJson, messageReceivedAsJson)
    }

}