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
        input.solve2() shouldBeLessThan -1
    }

    test("Instructions only executes correctly parsed multiplication") {
        Instructions(listOf("mul(4,6)")).execute() shouldBe 24
        Instructions(listOf("mul(11,8)")).execute() shouldBe 88
    }

    test("Instructions with conditionals only executes multiplication after do()") {
        Instructions(listOf("mul(4,6)"), true).execute() shouldBe 24
        Instructions(listOf("mul(11,8)"), true).execute() shouldBe 88
        Instructions(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"), true).execute() shouldBe (2*4 + 8*5)
    }

})

object Day3 {
    fun String.solve(): Long {
        val instructions = Instructions(lines())
        return instructions.execute()
    }

    fun String.solve2(): Long {
        val instructions = Instructions(lines(), true)
        return instructions.execute()
    }

    class Instructions(private val lines: List<String>, private val useState: Boolean = false) {
        fun execute(): Long {
            var state = State.Do
            val regex = if (useState) Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""") else Regex("""mul\(\d+,\d+\)""")
            return lines.asSequence().flatMap { line ->
                regex
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
                    }
            }.sum()
        }
    }

    enum class State {
        Do, Dont
    }

}

