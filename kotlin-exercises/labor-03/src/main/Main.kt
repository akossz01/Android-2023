data class Item(
    val question: String,
    val answers: List<String>,
    val correct: Int
)

class ItemRepository {
    private val items = mutableListOf<Item>()

    init {
        items.addAll(
            listOf(
                Item("What is the default return type of a Kotlin function that returns no value?", listOf("void", "Unit", "Nothing", "None"), 1),
                Item("How do you declare a variable in Kotlin that cannot be reassigned?", listOf("var", "let", "const", "val"), 3),
                Item("What keyword is used to create a class in Kotlin?", listOf("class", "struct", "object", "interface"), 0),
                Item("Which function is used to print output to the console in Kotlin?", listOf("print", "echo", "println", "write"), 2),
                Item("What is Kotlin primarily used for?", listOf("Web Development", "Mobile Development", "Desktop Development", "Data Science"), 1),
                Item("How do you check for null safety in Kotlin?", listOf("?", "null", "??", "!.null"), 0),
                Item("Which Kotlin keyword allows you to extend a class?", listOf("extends", "override", "open", "inherit"), 2),
                Item("What is the equivalent of Java's 'static' keyword in Kotlin?", listOf("companion object", "object", "singleton", "const"), 0),
                Item("Which library provides Kotlin's coroutines?", listOf("kotlin.coroutines", "kotlinx.coroutines", "kotlin.coroutines.flow", "kotlin.concurrent"), 1),
                Item("What is the default visibility modifier in Kotlin?", listOf("public", "internal", "protected", "private"), 1)
            )
        )
    }

    fun randomItem(): Item {
        return items.random()
    }

    fun save(item: Item) {
        items.add(item)
    }

    fun size(): Int = items.size

    fun selectRandomItems(n: Int): List<Item> {
        return items.shuffled().take(n)
    }
}

class ItemService(private val itemRepository: ItemRepository) {

    fun selectRandomItems(n: Int): List<Item> {
        return itemRepository.selectRandomItems(n)
    }
}

class ItemController(private val itemService: ItemService) {

    fun quiz(numQuestions: Int) {
        val items = itemService.selectRandomItems(numQuestions)
        var correctAnswers = 0

        for ((index, item) in items.withIndex()) {
            println("Question ${index + 1}: ${item.question}")
            item.answers.forEachIndexed { i, answer -> println("${i + 1}. $answer") }
            
            print("Your answer: ")
            val userAnswer = readLine()?.toIntOrNull()

            if (userAnswer == item.correct + 1) {
                println("Correct!\n")
                correctAnswers++
            } else {
                println("Wrong! Correct answer was: ${item.answers[item.correct]}\n")
            }
        }

        println("You got $correctAnswers out of ${items.size} correct!")
    }
}


fun main() {
    val repository = ItemRepository()
    val service = ItemService(repository)
    val controller = ItemController(service)

    print("Welcome to the Kotlin Quiz!\nHow many questions would you like to attempt? ")
    val numQuestions = readLine()?.toIntOrNull() ?: 0

    if (numQuestions > 0) {
        controller.quiz(numQuestions)
    } else {
        println("Invalid number of questions. Exiting quiz.")
    }
}
