package main

fun main(args: Array<String>) {
    // addTwoValues(2, 3)
    // daysOfWeek()
    // printPrimesInRange(1..100)
    // encodeDecode()
    // printEven(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    // transformations()
    mutableLists()
}

// 1.
fun addTwoValues(a: Int, b: Int) {
    val sum = a + b
    println("$a + $b = $sum")
}

// 2.
fun daysOfWeek() {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
	printAllDays(daysOfWeek)
    printDaysStartingWithT(daysOfWeek)
    printDaysContainingE(daysOfWeek)
    printDaysLengthSix(daysOfWeek)
}

fun printAllDays(days: List<String>) {
    for (day in days) {
        println(day)
    }
}

fun printDaysStartingWithT(days: List<String>) {
    val daysStartingWithT = days.filter { it.startsWith('T') }
    println("Days starting with 'T': $daysStartingWithT")
}

fun printDaysContainingE(days: List<String>) {
    val daysContainingE = days.filter { it.contains('e') }
    println("Days containing 'e': $daysContainingE")
}

fun printDaysLengthSix(days: List<String>) {
    val daysLengthSix = days.filter { it.length == 6 }
    println("Days of length 6: $daysLengthSix")
}

// 3.
fun checkPrime(num: Int): Boolean {
    if (num < 2) {
    	return false
    }
    
    for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
        if (num % i == 0) return false
    }
    
    return true
}

fun printPrimesInRange(range: IntRange) {
    for (i in range) {
        if (checkPrime(i)) {
            print("$i, ")
        }
    }
}

// 4.
fun encodeDecode() {
    val message = "Szia, ember!"
   	val encoded = messageCoding(message, ::encode)
    println(encoded)
    val decoded = messageCoding(encoded, ::decode)
    println(decoded)
}

fun encode(input: String): String {
    return input.map {
        if (it.isLetter()) {
            val shift = 4
            val base = if (it.isUpperCase()) 'A' else 'a'
            ((it - base + shift) % 26 + base.toInt()).toChar()
        } else {
            it
        }
    }.joinToString("")
}

fun decode(input: String): String {
    return input.map {
        if (it.isLetter()) {
            val shift = 4
            val base = if (it.isUpperCase()) 'A' else 'a'
            ((it - base - shift + 26) % 26 + base.toInt()).toChar() // Added +26 to handle negative values
        } else {
            it
        }
    }.joinToString("")
}

fun messageCoding(msg: String, func: (String) -> String): String {
    return func(msg)
}

// 5.
fun printEven(numbers: IntArray) = numbers.filter { it % 2 == 0 }.forEach { println(it) }

// 6.
fun transformations() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
	
    doubleElements(numbers)
    capitalizeDays(daysOfWeek)
    capitalizeDaysFirstChar(daysOfWeek)
    daysLength(daysOfWeek)
    averageLength(daysOfWeek)
}

fun doubleElements(numbers: List<Int>) {
    val doubled = numbers.map { it * 2 }
    println("Doubled: $doubled")
}

fun capitalizeDays(days: List<String>) {
    val capitalized = days.map { it.uppercase() }
    println("Capitalized: $capitalized")
}

fun capitalizeDaysFirstChar(days: List<String>) {
    val capitalized = days.map { it[0].uppercase() }
    println("First char capitalized: $capitalized")
}

fun daysLength(days: List<String>) {
    val lengths = days.map { it.length }
    println("Days length: $lengths")
}

fun averageLength(days: List<String>) {
    val lengths = days.map { it.length }
    val averageLength = lengths.average()
    println("Average length: ${String.format("%.2f", averageLength)}")
}

// 7.
fun mutableLists() {
	val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    val mutableDays = daysOfWeek.toMutableList()
    
    mutableDays.removeIf { it.contains('n', ignoreCase = true) }
    println(mutableDays)
    
    for ((index, day) in mutableDays.withIndex()) {
        println("Item at $index is $day")
    }
    
    mutableDays.sort()
    println(mutableDays)
}

// 8.
fun arrayExercises() {
    val randomNumbers = IntArray(10) { Random.nextInt(0, 101) }
    randomNumbers.forEach { print("$it, ") }
    print("\n")
    
    val sortedNumbers = randomNumbers.sortedArray()
    println("Sorted numbers: ${sortedNumbers.joinToString(", ")}")

    val hasEven = randomNumbers.any {it % 2 == 0 }
    println("Contains even number: $hasEven")
    
    val allEven = randomNumbers.all { it % 2 == 0 }
    println("All numbers are even: $allEven")
    
    val average = randomNumbers.average()
    println("Average of numbers: $average")
}