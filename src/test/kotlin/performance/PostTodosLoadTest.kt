package performance

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import org.junit.jupiter.api.Test
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class PostTodosLoadTest {

    private val url = "http://localhost:8080/todos"
    private val totalRequests = 100
    private val reportFile = File("load_test_report.txt")

    @Test
    fun `load test for POST todos`() {
        runBlocking {
            val times = mutableListOf<Long>()
            val successfulRequests = AtomicInteger(0)
            val failedRequests = AtomicInteger(0)

            coroutineScope {
                repeat(totalRequests) { i ->
                    launch(Dispatchers.IO) {
                        val uniqueId = Random.nextInt(1000, 9999)
                        val randomString = (1..20)
                            .map { ('a'..'z').random() }
                            .joinToString("")

                        val requestBody = """{
                            "id": $uniqueId,
                            "text": "$randomString",
                            "completed": false
                        }"""

                        val time = measureTimeMillis {
                            val response = given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .post(url)

                            if (response.statusCode == 201) {
                                successfulRequests.incrementAndGet()
                            } else {
                                failedRequests.incrementAndGet()
                            }
                        }
                        times.add(time)
                    }
                }
            }


            val averageTime = times.average()
            val maxTime = times.maxOrNull()
            val minTime = times.minOrNull()
            val successRate = successfulRequests.get().toDouble() / totalRequests * 100
            val failureRate = failedRequests.get().toDouble() / totalRequests * 100

            val report = """
                Performance summary for POST /todos:
                Total requests: $totalRequests
                Successful requests: ${successfulRequests.get()} ($successRate%)
                Failed requests: ${failedRequests.get()} ($failureRate%)
                Average response time: $averageTime ms
                Min response time: $minTime ms
                Max response time: $maxTime ms
            """.trimIndent()

            reportFile.writeText(report)

            println(report)
        }
    }
}