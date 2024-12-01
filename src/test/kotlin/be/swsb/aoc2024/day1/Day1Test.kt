package be.swsb.aoc2024.day1

import be.swsb.aoc2024.day1.Distance.Companion.sum
import be.swsb.aoc2024.day1.SimilarityScore.Companion.sum
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.math.absoluteValue

class Day1Test : FunSpec({

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

fun String.solve(): Long {
    val (left, right) = extractLocationIds()
    val sortedLocationPairs = left.sorted().zip(right.sorted())
    val distances = sortedLocationPairs.map(LocationPair::distance)
    return distances.sum()
}

fun String.solve2(): Long {
    val (left, right) = extractLocationIds()
    val leftSimilarities = left.map { locationId -> locationId.similaritiesIn(right) }
    val similarityScores = leftSimilarities.map(Similarity::similarityScore)
    return similarityScores.sum()
}

fun String.extractLocationIds(): Pair<List<LocationId>, List<LocationId>> {
    val locationIdPairs = lines().map { line -> line.split("   ").let { (left, right) -> left.toLocationId() to right.toLocationId() } }
    return locationIdPairs.unzip()
}

@JvmInline
value class LocationId(private val value: Long): Comparable<LocationId> {
    override fun compareTo(other: LocationId): Int = this.value.compareTo(other.value)
    fun distanceTo(other: LocationId) = Distance((this.value - other.value))
    operator fun times(occurrences: Int): SimilarityScore = SimilarityScore(this.value * occurrences)
    fun similaritiesIn(locationIds: List<LocationId>) = Similarity(this, locationIds.count { this == it })
}
private fun String.toLocationId() = LocationId(this.toLong())
fun LocationPair.distance() = first.distanceTo(second)
typealias LocationPair = Pair<LocationId, LocationId>

data class Distance(private val value: Long): Comparable<Distance> {
    private val absoluteValue = value.absoluteValue
    override fun compareTo(other: Distance): Int = this.absoluteValue.compareTo(other.absoluteValue)
    companion object {
        fun List<Distance>.sum() = sumOf { it.absoluteValue }
    }
}

data class Similarity(private val locationId: LocationId, private val occurrences: Int) {
    val similarityScore: SimilarityScore get() = locationId * occurrences
}

@JvmInline value class SimilarityScore(private val value: Long) {
    companion object {
        fun List<SimilarityScore>.sum() = sumOf { it.value }
    }
}