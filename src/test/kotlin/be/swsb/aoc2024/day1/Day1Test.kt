package be.swsb.aoc2024.day1

import be.swsb.aoc2024.util.Debugging.debug
import be.swsb.aoc2024.util.Debugging.withDebugging
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.math.absoluteValue

class Day1Test : FunSpec({

    test("example input part 1") {
        val input = readFile("day1/exampleInput.txt")
        input.solve() shouldBeEqual 11
    }

    test("actual input part 1") {
        val input = readFile("day1/input.txt")
        input.solve() shouldBeEqual 2756096
    }

    test("example input part 2") {
        val input = readFile("day1/exampleInput.txt")
        input.solve2() shouldBeEqual 31
    }

    test("actual input part 2") {
        val input = readFile("day1/input.txt")
        input.solve2() shouldBeEqual 23117829
    }
})

fun String.solve2(): Long {
    val locationIdPairs = lines().map { line -> line.split("   ").let { it[0].toLong() to it[1].toLong() } }
    val (left, right) = locationIdPairs.unzip()
    val leftOccurrences = left.map { locationId -> locationId to right.count { locationId == it } }
    val similarityScores = leftOccurrences.map { (it, occurrences) -> it * occurrences }
    return similarityScores.sum()
}

fun String.solve(): Long {
    val locationIdPairs = lines().map { line -> line.split("   ").let { it[0].toLong() to it[1].toLong() } }
    val (left, right) = locationIdPairs.unzip()
    val sortedLocationIdPairs = left.sorted().zip(right.sorted())
    val distances = sortedLocationIdPairs.map { (left,right) -> left.minus(right).absoluteValue }
    return distances.sum()
}