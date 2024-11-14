package todo.api

object TestData {
    val staticId: Int = (100..999).random()
    val randomString: String = (1..200)
        .map { ('a'..'z').random() }
        .joinToString("")

    fun generateRandomId(): Int {
        var randomUniqId: Int
        do {
            randomUniqId = (100..999).random()
        } while (randomUniqId == staticId)

        return randomUniqId
    }

}