package be.swsb.aoc2024.util

import kotlin.math.absoluteValue

fun readFile(fileName: String): String =
    {}::class.java.classLoader.getResourceAsStream(fileName)?.reader()?.readText() ?: error("Could not load $fileName")


/** Least common multiple **/
fun List<Int>.lcm(): Int =
    map { it.toLong() }.reduce(::lcm).toInt()

/** Least common multiple **/
fun lcm(a: Long, b: Long) : Long
        = (a safeTimes b) / gcd(a,b)

/** Greatest Common Denominator **/
fun gcd(a: Long, b: Long) : Long =
    if (b == 0L) a.absoluteValue else gcd(b, a % b)

// Thanks https://github.com/Zordid/adventofcode-kotlin-2022/blob/main/src/main/kotlin/utils
infix fun Long.safeTimes(other: Long) = (this * other).also {
    check(other == 0L || it / other == this) { "Long Overflow at $this * $other" }
}

