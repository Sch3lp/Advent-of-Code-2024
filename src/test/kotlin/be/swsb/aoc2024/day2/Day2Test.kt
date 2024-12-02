package be.swsb.aoc2024.day2

import be.swsb.aoc2024.day2.Day2.Levels
import be.swsb.aoc2024.day2.Day2.ProblemDampener
import be.swsb.aoc2024.day2.Day2.solve
import be.swsb.aoc2024.day2.Day2.solve2
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
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
        input.solve2() shouldBeEqual 4
    }

    test("actual input part 2") {
        val input = readFile("day2/input.txt")
        input.solve2() shouldBeGreaterThan 320
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

    test("Levels are considered safe by the ProblemDampener if removing one level makes it safe.") {
        Levels(listOf(7, 6, 4, 2, 1)).areSafe(ProblemDampener) shouldBe true // without removing any level.
        Levels(listOf(1, 2, 7, 8, 9)).areSafe(ProblemDampener) shouldBe false // regardless of which level is removed.
        Levels(listOf(9, 7, 6, 2, 1)).areSafe(ProblemDampener) shouldBe false // regardless of which level is removed.
        Levels(listOf(1, 3, 2, 4, 5)).areSafe(ProblemDampener) shouldBe true // by removing the second level, 3.
        Levels(listOf(8, 6, 4, 4, 1)).areSafe(ProblemDampener) shouldBe true // by removing the third level, 4.
        Levels(listOf(1, 3, 6, 7, 9)).areSafe(ProblemDampener) shouldBe true // without removing any level.
    }
})

object Day2 {
    fun String.solve(): Long {
        val levels = lines().map { line -> Levels(line.split(" ").map { it.toInt() }) }
        return levels.count { it.areSafe() }.toLong()
    }

    fun String.solve2(): Long {
        val levels = lines().map { line -> Levels(line.split(" ").map { it.toInt() }) }
        return levels.count { it.areSafe(problemDampener = ProblemDampener) }.toLong()
    }

    data class Levels(val levels: List<Int>) {
        fun areSafe(problemDampener: ProblemDampener? = null): Boolean =
            problemDampener?.areSafe(this)
                    ?: haveConsistentOrder() && notMoreOfADifferenceThan(3)

        private fun notMoreOfADifferenceThan(allowedDifference: Int) =
            levels.zipWithNext()
                .all { (left, right) -> (left - right).absoluteValue <= allowedDifference }

        private fun haveConsistentOrder() = allAreIncreasing() || allAreDecreasing()

        private fun allAreIncreasing() = levels.zipWithNext().all { (left, right) -> left < right }
        private fun allAreDecreasing() = levels.zipWithNext().all { (left, right) -> left > right }
    }

    object ProblemDampener {
        fun areSafe(levels: Levels): Boolean = levels.areSafe() || levels.areSafeWithoutAny1Level()

        private fun Levels.areSafeWithoutAny1Level() =
            List(this.levels.size) { index -> this.levels.toMutableList().also { it.removeAt(index) } }
                .map { Levels(it.toList()) }
                .any { it.areSafe() }
    }
}