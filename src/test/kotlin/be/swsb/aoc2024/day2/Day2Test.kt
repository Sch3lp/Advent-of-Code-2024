package be.swsb.aoc2024.day2

import be.swsb.aoc2024.day2.Day2.solve
import be.swsb.aoc2024.day2.Day2.solve2
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class Day2Test : FunSpec({

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

object Day2 {
    fun String.solve(): Long {
        return 0
    }

    fun String.solve2(): Long {
        return 0
    }

}