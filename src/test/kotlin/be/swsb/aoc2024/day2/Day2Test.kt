package be.swsb.aoc2024.day2

import be.swsb.aoc2024.day2.Day2.Levels
import be.swsb.aoc2024.day2.Day2.solve
import be.swsb.aoc2024.day2.Day2.solve2
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import kotlin.math.absoluteValue

class Day2Test : FunSpec({

    test("example input part 1") {
        val input = readFile("day2/exampleInput.txt")
        input.solve() shouldBeEqual 2
    }

    test("actual input part 1") {
        val input = readFile("day2/input.txt")
        input.solve() shouldBeEqual 287
    }

    test("example input part 2") {
        val input = readFile("day2/exampleInput.txt")
        input.solve2() shouldBeEqual 31
    }

    test("actual input part 2") {
        val input = readFile("day2/input.txt")
        input.solve2() shouldBeEqual 23117829
    }

    test("Levels are safe when they are all increasing or all decreasing.") {
        Levels(listOf(1, 2, 3, 4, 5)).areSafe() shouldBe true
        Levels(listOf(5, 4, 3, 2, 1)).areSafe() shouldBe true
        Levels(listOf(5, 4, 5)).areSafe() shouldBe false
        Levels(listOf(4, 5, 4)).areSafe() shouldBe false
    }

    test("Levels are safe when any two adjacent levels differ by at least one and at most three.") {
        Levels(listOf(1, 4, 7, 10, 13)).areSafe() shouldBe true
        Levels(listOf(13, 10, 7, 4, 1)).areSafe() shouldBe true
        Levels(listOf(1, 5)).areSafe() shouldBe false
        Levels(listOf(5, 1)).areSafe() shouldBe false
    }
})

object Day2 {
    fun String.solve(): Long {
        val levels = lines().map { line -> Levels(line.split(" ").map { it.toInt() }) }
        return levels.count { it.areSafe() }.toLong()
    }

    fun String.solve2(): Long {
        return 0
    }

    data class Levels(val levels: List<Int>) {
        fun areSafe(): Boolean {
            return haveConsistentOrder() && notMoreOfADifferenceThan(3)
        }

        private fun notMoreOfADifferenceThan(allowedDifference: Int): Boolean {
            return levels.zipWithNext().all { (left,right) -> (left - right).absoluteValue <= allowedDifference.absoluteValue }
        }

        private fun haveConsistentOrder() = allAreIncreasing() || allAreDecreasing()

        private fun allAreIncreasing() = levels.zipWithNext().all { (left, right) -> left < right }
        private fun allAreDecreasing() = levels.zipWithNext().all { (left, right) -> left > right }
    }

}