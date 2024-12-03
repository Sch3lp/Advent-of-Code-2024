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
        input.solve2() shouldBeEqual 100189366
    }

    test("Instructions only executes correctly parsed multiplication") {
        Instructions("mul(4,6)").execute() shouldBe 24
        Instructions("mul(11,8)").execute() shouldBe 88
    }

    test("Instructions with conditionals only executes multiplication after do()") {
        Instructions("mul(4,6)", true).execute() shouldBe 24
        Instructions("mul(11,8)", true).execute() shouldBe 88
        Instructions(
            "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))",
            true
        ).execute() shouldBe (2 * 4 + 8 * 5)
    }

})

object Day3 {
    fun String.solve(): Long {
        val instructions = Instructions(this)
        return instructions.execute()
    }

    fun String.solve2(): Long {
        val instructions = Instructions(this, true)
        return instructions.execute()
    }

    class Instructions(private val input: String, private val useState: Boolean = false) {
        fun execute(): Long {
            val regex = if (useState) Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""") else Regex("""mul\(\d+,\d+\)""")
            return regex
                .findAll(input)
                .map { it.value }
                .fold(State.Do to 0L) { (state, sum), it ->
                    when {
                        it == "do()" -> State.Do to sum
                        it == "don't()" -> State.Dont to sum
                        state == State.Do -> {
                            val (left, right) = it.drop("mul(".length).dropLast(1).split(",")
                            state to sum + (left.toLong() * right.toLong())
                        }
                        else -> state to sum
                    }
                }.second
        }
    }


    enum class State {
        Do, Dont
    }

}

