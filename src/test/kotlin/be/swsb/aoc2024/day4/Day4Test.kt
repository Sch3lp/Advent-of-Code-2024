package be.swsb.aoc2024.day4

import be.swsb.aoc2024.day4.Day4.solve
import be.swsb.aoc2024.day4.Day4.solve2
import be.swsb.aoc2024.util.Point
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.maps.shouldContainAll

class Day4Test : FunSpec({

    test("example input part 1") {
        val input = readFile("day4/exampleInput.txt")
        input.solve() shouldBeEqual 18
    }

    test("actual input part 1") {
        val input = readFile("day4/input.txt")
        input.solve() shouldBeEqual 2514
    }

    test("example input part 2") {
        val input = readFile("day4/exampleInput.txt")
        input.solve2() shouldBeEqual 1
    }

    test("actual input part 2") {
        val input = readFile("day4/input.txt")
        input.solve2() shouldBeEqual 1
    }

    test("can parse to grid with positions and characters") {
        val smallInput = """
            123
            456
            789
        """.trimIndent()

        val actual: Map<Point, Char> = parseToGrid(smallInput)

        actual shouldContainAll mapOf(
            Point(0, 0) to '1', Point(1, 0) to '2', Point(2, 0) to '3',
            Point(0, 1) to '4', Point(1, 1) to '5', Point(2, 1) to '6',
            Point(0, 2) to '7', Point(1, 2) to '8', Point(2, 2) to '9',
        )
    }
})

fun parseToGrid(input: String): Map<Point, Char> = input.lines()
    .flatMapIndexed { y, line ->
        line.mapIndexed { x, character ->
            Point(x, y) to character
        }
    }.toMap()

val exampleInput = """
    ....XXMAS.
    .SAMXMS...
    ...S..A...
    ..A.A.MS.X
    XMASAMX.MM
    X.....XA.A
    S.S.S.S.SS
    .A.A.A.A.A
    ..M.M.M.MM
    .X.X.XMASX
""".trimIndent()


object Day4 {
    fun String.solve(): Long {
        val grid = parseToGrid(this)
        val xPositions = grid.filter { (_, value) -> value == 'X' }.keys
        return xPositions.sumOf { x ->
            //compute 4 next positions in all neighbour vectors
            x.neighbourLines(4).sumOf { line ->
                //fetch all of these from the grid
                val wordOnLine = line.map { point -> grid[point] ?: "" }.joinToString(separator = "")
                //check if they form XMAS, if yes: add 1, if no: add 0
                if (wordOnLine == "XMAS") 1L
                else 0
            }
        }
    }

    fun String.solve2(): Long {
        return 0
    }
}

