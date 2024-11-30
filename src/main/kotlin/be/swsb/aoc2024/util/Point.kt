package be.swsb.aoc2024.util

import kotlin.math.sign

data class Point(val x: Int, val y: Int) {

    //@formatter:off
    val neighbours: Set<Point>
        get() = listOf(
            Point(-1, -1), Point(0, -1), Point(1, -1),
            Point(-1, 0), /*  this   */  Point(1, 0),
            Point(-1, 1), Point(0, 1), Point(1, 1),
        ).map { vector -> this + vector }
            .toSet()

    val orthogonalNeighbours: Set<Point>
        get() = listOf(
            Point(0, -1),
            Point(-1, 0), Point(1, 0),
            Point(0, 1),
        ).map { vector -> this + vector }
            .toSet()
    //@formatter:on

    val diagonalNeighbours: Set<Point>
        get() = listOf(
            Point(-1, -1), Point(1, -1),
            Point(-1, 1), Point(1, 1),
        ).map { vector -> this + vector }
            .toSet()

    fun isDiagonalTo(other: Point): Boolean = this in other.diagonalNeighbours

    operator fun plus(vector: Point) = Point(x + vector.x, this.y + vector.y)
    operator fun times(multiplier: Int) = Point(x = x * multiplier, y = y * multiplier)
    operator fun rangeTo(other: Point): Set<Point> {
        var cur = this
        val points = mutableSetOf(cur)
        while (cur != other) {
            cur += cur.determineVectorTo(other)
            points.add(cur)
        }
        return points
    }
    infix fun until(other: Point) = (this..other) - other

    private fun determineVectorTo(other: Point): Point {
        return if (this.x == other.x) Point(0, (other.y - this.y).sign)
        else if (this.y == other.y) Point((other.x - this.x).sign, 0)
        else Point((other.x - this.x).sign, (other.y - this.y).sign)
    }

    fun searchRight(pointPredicate: (Point) -> Boolean) : Point? =
        if (pointPredicate(this + Point(1, 0))) { this + Point(1, 0)
        }
        else null

    companion object {
        fun at(x:Int, y: Int) = Point(x, y)
    }
}