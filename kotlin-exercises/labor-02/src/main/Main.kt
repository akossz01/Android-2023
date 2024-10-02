fun main() {
    // Problem 1
    val dict: IDictionary = ListDictionary()
    createMockDictData(dict)
    println("Number of words: ${dict.size()}")

    val searchWords = listOf("apple", "pear", "banana", "watermelon", "kiwi", "mango")
    for (word in searchWords) {
        println("Searching for '$word': Result: ${dict.find(word)}")
    }
}

// Problem 1
interface IDictionary {
    fun add(word: String)
    fun find(word: String): Boolean
    fun size(): Int
}

class ListDictionary : IDictionary {
    private val words: MutableList<String> = mutableListOf()

    override fun add(word: String) {
        words.add(word)
    }

    override fun find(word: String): Boolean {
        return words.contains(word)
    }

    override fun size(): Int {
        return words.size
    }
}

fun createMockDictData(dict: IDictionary) {
    val mockWords = listOf("apple", "banana", "grape", "orange", "melon", "kiwi", "mango", "cherry", "pear", "peach")
    for (word in mockWords) {
        dict.add(word)
    }
}
