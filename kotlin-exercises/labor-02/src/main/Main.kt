fun main() {
    // Problem 1
    println("Problem 1")
    val dict: IDictionary = ListDictionary()
    createMockDictData(dict)
    println("Number of words: ${dict.size()}")

    val searchWords = listOf("apple", "pear", "banana", "watermelon", "kiwi", "mango")
    for (word in searchWords) {
        println("Searching for '$word': Result: ${dict.find(word)}")
    }
    println("\n")
    
    // Problem 2
    val fullName = "John Smith"
    println("Monogram of '$fullName': ${fullName.monogram()}")
	
    val fruits = listOf("apple", "pear", "melon")
    val separator = "-"
    println("Joined fruits: ${fruits.joinWithSeparator(separator)}")
    
    val words = listOf("apple", "pear", "strawberry", "melon")
    println("Longest word: ${words.longestString()}")
}

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

// Problem 2
fun String.monogram(): String {
    return this.split(" ")
    	.map { it.first() }
        .joinToString("")
}

fun List<String>.joinWithSeparator(separator: String): String {
    return this.joinToString(separator)
}

fun List<String>.longestString(): String? {
    return this.maxByOrNull { it.length }
}




