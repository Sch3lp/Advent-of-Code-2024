package be.swsb.aoc2024.day3

import be.swsb.aoc2024.day3.Day3.Instructions
import be.swsb.aoc2024.day3.Day3.solve
import be.swsb.aoc2024.day3.Day3.solve2
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.longs.shouldBeLessThan
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
        input.solve2() shouldBeEqual 161
    }

    test("actual input part 2") {
        val input = readFile("day3/input.txt")
        input.solve2() shouldBeLessThan 101681733
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
        val instructions = lines().map { line -> Instructions(line, true) }
        return instructions.sumOf { it.execute() }
    }

    class Instructions(private val line: String, private val useState: Boolean = false) {
        fun execute(): Long {
            var state = State.Do
            val regex = if (useState) Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""") else Regex("""mul\(\d+,\d+\)""")
            return regex
                .findAll(line)
                .map { it.value }
                .map {
                    when (it) {
                        "do()" -> {
                            state = State.Do; 0
                        }

                        "don't()" -> {
                            state = State.Dont; 0
                        }

                        else -> {
                            if (state == State.Do) {
                                val (left, right) = it.drop("mul(".length).dropLast(1).split(",")
                                left.toLong() * right.toLong()
                            } else 0
                        }
                    }
                }.sum()
        }
    }

    enum class State {
        Do, Dont
    }

}

