package be.swsb.aoc2024

import be.swsb.aoc2024.util.Point
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class PointTest: FunSpec({

    test("given two points - we can add them together") {
        val origin = Point(0, 0)
        val vector = Point(-1, 1)

        val actual = origin + vector

        actual shouldBe Point(-1, 1)
    }

    test("return neighbouring points of point") {
        val point = Point(0, 0)

        val actual = point.neighbours

        actual.shouldContainExactlyInAnyOrder(
            Point(-1, -1), Point(0, -1), Point(1, -1),
            Point(-1, 0), Point(1, 0),
            Point(-1, 1), Point(0, 1), Point(1, 1)
        )
    }

    test("return orthogonal neighbouring points of point") {
        val point = Point(0, 0)

        val actual = point.orthogonalNeighbours

        actual.shouldContainExactlyInAnyOrder(
            Point(0, -1),
            Point(-1, 0), Point(1, 0),
            Point(0, 1)
        )
    }

    test("return diagonal neighbouring points of point") {
        val point = Point(4, 3)

        val actual = point.diagonalNeighbours

        actual.shouldContainExactlyInAnyOrder(
            Point(3, 2), Point(5, 2),
            Point(3, 4), Point(5, 4)
        )
    }

    test("determine vector") {
        Point(0, 0).determineVectorTo(Point(0, 0)) shouldBeEqual Point(0, 0)
        Point(0, 0).determineVectorTo(Point(0, 3)) shouldBeEqual Point(0, 1)
        Point(0, 0).determineVectorTo(Point(3, 0)) shouldBeEqual Point(1, 0)
        Point(0, 0).determineVectorTo(Point(3, 3)) shouldBeEqual Point(1, 1)
        Point(0, 3).determineVectorTo(Point(0, 0)) shouldBeEqual Point(0, -1)
        Point(3, 0).determineVectorTo(Point(0, 0)) shouldBeEqual Point(-1, 0)
        Point(3, 3).determineVectorTo(Point(0, 0)) shouldBeEqual Point(-1, -1)
        Point(0, 0).determineVectorTo(Point(-3, -3)) shouldBeEqual Point(-1, -1)
        Point(-3, -3).determineVectorTo(Point(0, 0)) shouldBeEqual Point(1, 1)
        Point(0, 0).determineVectorTo(Point(3, 3)) shouldBeEqual Point(1, 1)
        shouldThrow<IllegalArgumentException> { Point(0, 0).determineVectorTo(Point(1, 3)) }
            .message?.shouldBeEqual("Can only determine vector orthogonally or diagonally: Point(x=0, y=0) to Point(x=1, y=3)")
    }

    test("rangeTo retains expected order") {
        (Point(0, 0)..Point(0, 3)).shouldContainExactly(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3))
        (Point(0, 3)..Point(0, 0)).shouldContainExactly(Point(0, 3), Point(0, 2), Point(0, 1), Point(0, 0))
        (Point(0, 0)..Point(3, 0)).shouldContainExactly(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0))
        (Point(3, 0)..Point(0, 0)).shouldContainExactly(Point(3, 0), Point(2, 0), Point(1, 0), Point(0, 0))
        (Point(0, 0)..Point(3, 3)).shouldContainExactly(Point(0, 0), Point(1, 1), Point(2, 2), Point(3, 3))
    }

    test("rangeTo diagonally just goes diagonally (no manhattan)") {
        (Point(0, 0)..Point(2, 2)).shouldContainExactly(Point(0, 0), Point(1, 1), Point(2, 2))
        (Point(2, 2)..Point(0, 0)).shouldContainExactly(Point(2, 2), Point(1, 1), Point(0, 0))
        (Point(0, 0)..Point(-2, -2)).shouldContainExactly(Point(0, 0), Point(-1, -1), Point(-2, -2))
        (Point(-2, -2)..Point(0, 0)).shouldContainExactly(Point(-2, -2), Point(-1, -1), Point(0, 0))
        (Point(0, 0)..Point(-2, 2)).shouldContainExactly(Point(0, 0), Point(-1, 1), Point(-2, 2))
        (Point(-2, 2)..Point(0,0)).shouldContainExactly(Point(-2, 2), Point(-1, 1), Point(0, 0))
        (Point(0, 0)..Point(2, -2)).shouldContainExactly(Point(0, 0), Point(1, -1), Point(2, -2))
        (Point(2, -2)..Point(0,0)).shouldContainExactly(Point(2, -2), Point(1, -1), Point(0, 0))
    }

    test("line returns all points in the same vector for the requested line length") {
        Point(0,0).line(vector = Point(1,0), length = 4).shouldContainExactly(Point(0,0), Point(1,0), Point(2,0), Point(3,0))
        Point(0,0).line(vector = Point(1,1), length = 4).shouldContainExactly(Point(0,0), Point(1,1), Point(2,2), Point(3,3))
        Point(0,4).line(vector = Point(-1,-1), length = 4).shouldContainExactly(Point(0,4), Point(-1,3), Point(-2,2), Point(-3,1))
    }

    test("neighbourlines returns the lines in the vectors of its neighbours for the requested line length") {
        Point(0,0).neighbourLines(length = 4).shouldContainAll(
            setOf(Point(0,0), Point(-1,-1), Point(-2,-2), Point(-3,-3)),
            setOf(Point(0,0), Point(0,1), Point(0,2), Point(0,3)),
            setOf(Point(0,0), Point(1,-1), Point(2,-2), Point(3,-3)),
            setOf(Point(0,0), Point(-1,0), Point(-2,0), Point(-3,0)),
            setOf(Point(0,0), Point(1,0), Point(2,0), Point(3,0)),
            setOf(Point(0,0), Point(-1,1), Point(-2,2), Point(-3,3)),
            setOf(Point(0,0), Point(0,1), Point(0,2), Point(0,3)),
            setOf(Point(0,0), Point(1,1), Point(2,2), Point(3,3)),
            )
    }
})