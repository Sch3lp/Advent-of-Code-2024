package be.swsb.aoc2024.day1

import be.swsb.aoc2024.util.readFile
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("day1/exampleInput.txt")
        input shouldBeEqual "snarf"
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day1/input.txt")
        input shouldBeEqual "snarf snarf"
    }
}