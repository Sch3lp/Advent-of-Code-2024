package be.swsb.aoc2024.day3

import be.swsb.aoc2024.day3.Day3.Instructions
import be.swsb.aoc2024.day3.Day3.solve
import be.swsb.aoc2024.day3.Day3.solve2
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class Day3Test : FunSpec({

    test("example input part 1") {
        val input = readFile("day3/exampleInput.txt")
        input.solve() shouldBeEqual 161
    }

    test("actual input part 1") {
        val input = readFile("day3/input.txt")
        input.solve() shouldBeEqual 155955228
    }

    test("example input part 2") {
        val input = readFile("day3/exampleInput.txt")
        input.solve2() shouldBeEqual -1
    }

    test("actual input part 2") {
        val input = readFile("day3/input.txt")
        input.solve2() shouldBeEqual -1
    }

    test("Instructions only executes correctly parsed multiplication") {
        Instructions("mul(4,6)").execute() shouldBe 24
        Instructions("mul(11,8)").execute() shouldBe 88
    }

})

object Day3 {
    fun String.solve(): Long {
        val instructions = lines().map { line -> Instructions(line) }
        return instructions.sumOf { it.execute() }
    }

    fun String.solve2(): Long {
        return 0
    }

    class Instructions(val line: String) {
        fun execute(): Long {
            return Regex("""mul\(\d+,\d+\)""")
                .findAll(line)
                .map { it.value }
                .map {
                    val (left,right) = it.drop(4).dropLast(1).split(",")
                    left.toLong() * right.toLong()
                }.sum()
        }
    }

}

