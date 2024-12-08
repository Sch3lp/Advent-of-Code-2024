package be.swsb.aoc2024.day5

import be.swsb.aoc2024.day5.Day5.PageOrderingRule
import be.swsb.aoc2024.day5.Day5.PageUpdate
import be.swsb.aoc2024.day5.Day5.parse
import be.swsb.aoc2024.day5.Day5.solve
import be.swsb.aoc2024.day5.Day5.solve2
import be.swsb.aoc2024.util.readFile
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.equals.shouldBeEqual

class Day5Test : ShouldSpec({
    val exampleInput = readFile("day5/exampleInput.txt")
    val actualInput = readFile("day5/input.txt")

    should("example input part 1") {
        exampleInput.solve() shouldBeEqual 143
    }

    should("actual input part 1") {
        actualInput.solve() shouldBeEqual 1
    }

    should("example input part 2") {
        exampleInput.solve2() shouldBeEqual 1
    }

    should("actual input part 2") {
        actualInput.solve2() shouldBeEqual 1
    }

    context("parsing") {
        should("rules are parseable") {
            val (rules, pageUpdates) = parse(exampleInput)
            rules shouldContainExactly listOf(
                PageOrderingRule(47, 53),
                PageOrderingRule(97, 13),
                PageOrderingRule(97, 61),
                PageOrderingRule(97, 47),
                PageOrderingRule(75, 29),
                PageOrderingRule(61, 13),
                PageOrderingRule(75, 53),
                PageOrderingRule(29, 13),
                PageOrderingRule(97, 29),
                PageOrderingRule(53, 29),
                PageOrderingRule(61, 53),
                PageOrderingRule(97, 53),
                PageOrderingRule(61, 29),
                PageOrderingRule(47, 13),
                PageOrderingRule(75, 47),
                PageOrderingRule(97, 75),
                PageOrderingRule(47, 61),
                PageOrderingRule(75, 61),
                PageOrderingRule(47, 29),
                PageOrderingRule(75, 13),
                PageOrderingRule(53, 13),
            )
            pageUpdates shouldContainExactly listOf(
                PageUpdate(75, 47, 61, 53, 29),
                PageUpdate(97, 61, 53, 29, 13),
                PageUpdate(75, 29, 13),
                PageUpdate(75, 97, 47, 61, 53),
                PageUpdate(61, 13, 29),
                PageUpdate(97, 13, 75, 29, 47),
            )
        }
    }

})

object Day5 {
    fun String.solve(): Long {
        return 0
    }

    fun String.solve2(): Long {
        return 0
    }

    fun parse(input: String): Pair<PageOrderingRules, List<PageUpdate>> =
        input
            .split("\n\n")
            .let { (rules, updates) ->
        PageOrderingRules(rules.lines().map(PageOrderingRule::parse)) to updates.lines().map(PageUpdate::parse)
    }

    class PageOrderingRules(
        private val rules: List<PageOrderingRule>
    ) : List<PageOrderingRule> by rules

    data class PageOrderingRule(val first: Int, val second: Int) {
        companion object {
            fun parse(input: String) = input
                .split("|")
                .let { (first, second) -> PageOrderingRule(first.toInt(), second.toInt()) }
        }
    }

    data class PageUpdate(val pages: List<Int>) {
        constructor(vararg pages: Int) : this(pages.toList())

        companion object {
            fun parse(input: String) = input
                .split(",")
                .map { it.toInt() }
                .let { PageUpdate(it) }
        }
    }
}

