import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import java.util.*

fun main() {
    // Problem 1
    println("Problem 1")
    val dictionaryType = DictionaryType.HASH_SET
    val dict: IDictionary = DictionaryProvider.createDictionary(dictionaryType)
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
    
    // Problem 3
    val validDates = mutableListOf<Date>()
    
    while (validDates.size < 10) {
        val randomDate = generateRandomDate()
        if (randomDate.isValid()) {
            validDates.add(randomDate)
        } else {
            println("Invalid date: $randomDate")
        }
    }
    
    println("\nValid Dates:")
    validDates.forEach { println(it) }
    
    validDates.sort()
    println("\nSorted Valid Dates:")
    validDates.forEach { println(it) }
    
    validDates.reverse()
    println("\nReversed Sorted Valid Dates")
    validDates.forEach { println(it) }
    
    validDates.sortWith(compareBy({ it.month }, { it.day }))
    println("\nCustom Sorted Valid Dates")
    validDates.forEach { println(it) }
}

// Problem 1
interface IDictionary {
    fun add(word: String)
    fun find(word: String): Boolean
    fun size(): Int
}

enum class DictionaryType {
    ARRAY_LIST,
    TREE_SET,
    HASH_SET
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

class TreeSetDictionary : IDictionary {
    private val words: TreeSet<String> = TreeSet()

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

class HashSetDictionary : IDictionary {
    private val words: HashSet<String> = HashSet()

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

object DictionaryProvider {
    fun createDictionary(type: DictionaryType): IDictionary {
        return when (type) {
            DictionaryType.ARRAY_LIST -> ListDictionary()
            DictionaryType.TREE_SET -> TreeSetDictionary()
            DictionaryType.HASH_SET -> HashSetDictionary()
        }
    }
}

// Problem 2
fun String.monogram(): String {
    return this.split(" ")
    	.map { it.first() }
        .joinToString("")
}

fun List<String>.joinWithSeparator(separator: String) = joinToString(separator)

fun List<String>.longestString() = this.maxByOrNull { it.length }

// Problem 3
data class Date(val year: Int, val month: Int, val day: Int): Comparable<Date> {
    constructor() : this(
    	LocalDate.now().year,
    	LocalDate.now().monthValue,
        LocalDate.now().dayOfMonth
    )
    
    override fun compareTo(other: Date): Int {
        return compareValuesBy(this, other, { it.year }, { it.month }, { it.day })
    }
}

fun Date.isLeapYear(): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun Date.isValid(): Boolean {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> day in 1..31
        4, 6, 9, 11 -> day in 1..30
        2 -> if (isLeapYear()) day in 1..29 else day in 1..28
        else -> false
    }
}

fun generateRandomDate(): Date {
    val year = Random.nextInt(1900, 2100)
    val month = Random.nextInt(1, 13)
    val day = when (month) {
        1, 3,  5, 7, 8, 10, 12 -> Random.nextInt(1, 32)
        4, 6, 9, 11 -> Random.nextInt(1, 31)
        2 -> if (Date(year, month, 1).isLeapYear()) Random.nextInt(1, 30) else Random.nextInt(1, 29)
        else -> 1
    }
    
    return Date(year, month, day)
}