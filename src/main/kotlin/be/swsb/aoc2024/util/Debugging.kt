package be.swsb.aoc2024.util

object Debugging {
    private var debugEnabled = false
    fun <T> T.debug(block: (it: T) -> String?) = if (debugEnabled) this.also { block(this)?.let { println(it) } } else this
    fun enable() {
        debugEnabled = true
    }

    fun disable() {
        debugEnabled = false
    }


    fun <T> withDebugging(enabled: Boolean = true, block: () -> T): T {
        debugEnabled = enabled
        return block().also { disable() }
    }
}