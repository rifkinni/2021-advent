fun calculateScore(char: Char?): Int {
    val scoreLookup: MutableMap<Char, Int> = mutableMapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    return scoreLookup[char]!!
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.mapNotNull { Line(it).violation }.sumOf { calculateScore(it) }
    }

    fun part2(input: List<String>): Long {
        val scores = input.mapNotNull { Line(it).scorePart2() }.sorted()
        val median = scores.size / 2
        return scores[median]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput ) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

class Line(line: String) {
    private val brackets = mapOf(
        '{' to '}',
        '[' to ']',
        '<' to '>',
        '(' to ')')

    var violation: Char? = null
    var incompleteString: String? = null

    fun scorePart2(): Long? {
        if (completedString() == null) return null

        val scoreLookup: MutableMap<Char, Int> = mutableMapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4
        )
        var total = 0L

        for (char in completedString()!!) {
            total = 5 * total + scoreLookup[char]!!
        }

        return total
    }

    fun completedString(): String? {
        return incompleteString?.reversed()?.map { brackets[it]!! }?.joinToString("")
    }

    init {
        parseRecursively(line)
    }

    private fun parseRecursively(segment: String) {
        for (i in segment.indices) {
            val char = segment[i]
            if (brackets.keys.contains(char)) { //opening bracket
                if (i == segment.length - 1)
                {
                    this.incompleteString = segment
                    return
                }

                if (segment[i + 1] == brackets[char]) {
                    return parseRecursively(buildSubstring(segment, i))
                }
            } else {
                this.violation = segment[i]
                return
            }
        }
        this.incompleteString = segment
        return
    }

    private fun buildSubstring(segment: String, i: Int): String {
        return if (segment.length <= i + 1)  segment.substring(0, i) else segment.substring(0, i).plus(segment.substring(i + 2))
    }
}
