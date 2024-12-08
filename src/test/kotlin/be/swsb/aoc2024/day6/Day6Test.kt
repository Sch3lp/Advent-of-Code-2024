package be.swsb.aoc2024.day6

import be.swsb.aoc2024.day6.Day6.Direction.Up
import be.swsb.aoc2024.day6.Day6.Path
import be.swsb.aoc2024.day6.Day6.Room
import be.swsb.aoc2024.day6.Day6.solve
import be.swsb.aoc2024.day6.Day6.solve2
import be.swsb.aoc2024.util.Debugging.debug
import be.swsb.aoc2024.util.Point
import be.swsb.aoc2024.util.Point.Companion.at
import be.swsb.aoc2024.util.parseToGrid
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class Day6Test : ShouldSpec({

    should("example input part 1") {
        val input = readFile("day6/exampleInput.txt")
        input.solve() shouldBeEqual 41
    }

    should("actual input part 1") {
        val input = readFile("day6/input.txt")
        input.solve() shouldBeEqual 5162
    }

    should("example input part 2") {
        val input = readFile("day6/exampleInput.txt")
        input.solve2() shouldBeEqual 1
    }

    should("actual input part 2") {
        val input = readFile("day6/input.txt")
        input.solve2() shouldBeEqual 1
    }

    context("Room with dimension 10x10") {
        val room = Room(readFile("day6/exampleInput.txt"))
        val roomCorners = listOf(at(0,0), at(9,0), at(0,9), at(9,9))
        roomCorners.forEach { corner ->
            should("Corner $corner should be in Room with dimension 10x10") {
                (corner in room) shouldBe true
            }
        }
        val outsideRoom = listOf(at(-1,0), at(0,-1), at(10,0), at(0,10), at(10,10))
        outsideRoom.forEach { outside ->
            should("$outside should be outside Room with dimension 10x10") {
                (outside in room) shouldBe false
            }
        }
    }

    context("Path") {
        should("calculate length properly") {
            Path(from = at(0,0), to = at(0,0)).length shouldBeEqual 1
            Path(from = at(0,0), to = at(0,1)).length shouldBeEqual 2
            Path(from = at(0,0), to = at(1,0)).length shouldBeEqual 2
            Path(from = at(0,1), to = at(0,0)).length shouldBeEqual 2
            Path(from = at(1,0), to = at(0,0)).length shouldBeEqual 2
        }
    }
})

object Day6 {
    fun String.solve(): Long {
        val room = Room(this)
        val walkedPath = room.guard.executeProtocol(room).walkedPath
        return walkedPath.flatMap { it.points }.toSet().size.toLong()
    }

    fun String.solve2(): Long {
        return 0
    }

    data class Guard(
        val at: Point,
        val direction: Direction = Up,
        val walkedPath: List<Path> = emptyList(),
        val hasExited: Boolean = false,
    ) {
        fun executeProtocol(room: Room) : Guard {
            return if (hasExited) this.debug { "Guard exited at $at" }
            else {
                var currentPosition = at.debug { "Starting at $at" }
                while(currentPosition in room && currentPosition !in room.obstacles) {
                    currentPosition = currentPosition.plus(direction.vector).debug { "Moved over $it" }
                }
                debug { if (currentPosition in room.obstacles) "stopped because hit an obstacle at $currentPosition" else "" }
                copy(
                    at = currentPosition - direction.vector,
                    direction = direction.rotateRight().debug { "Turning towards $it" },
                    walkedPath = walkedPath + Path(at, currentPosition - direction.vector),
                    hasExited = currentPosition !in room
                ).executeProtocol(room)
            }
        }
    }

    data class Path(val from: Point, val to: Point) {
        val points get() = from..to
        val length: Long get() = (from..to).size.toLong()
    }

    enum class Direction {
        Up, Down, Left, Right;
        fun rotateRight() = when (this) {
            Up -> Right
            Right -> Down
            Down -> Left
            Left -> Up
        }
        val vector: Point get() = when(this) {
            Up -> at(0,-1)
            Right -> at(1,0)
            Down -> at(0,1)
            Left -> at(-1,0)
        }
    }

    class Room(input: String) {
        private val dimension = input.lines().size
        private val grid = parseToGrid(input)

        val obstacles: Set<Point> get() = grid.filterValues { it == '#' }.keys
        val guard: Guard get() = Guard(grid.filterValues { it == '^' }.keys.first())

        operator fun contains(currentPosition: Point): Boolean =
            currentPosition.x >= 0 && currentPosition.y >= 0 &&
            currentPosition.x < dimension && currentPosition.y < dimension
    }
}

