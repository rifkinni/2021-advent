fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Entry(it) }.sumOf { it.count1478() }
    }

    fun part2(input: List<String>): Int {
        return input.map { Entry(it) }.sumOf { it.calculateOutput() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput ) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

class Entry(input: String) {
    private val signals: List<String>
    private val output: List<String>

    private val digitLookup: Map<String, String> = mapOf(
        "abcefg" to "0",
        "cf" to "1",
        "acdeg" to "2",
        "acdfg" to "3",
        "bcdf" to "4",
        "abdfg" to "5",
        "abdefg" to "6",
        "acf" to "7",
        "abcdefg" to "8",
        "abcdfg" to "9"
    )

    private val allPossibleValueList = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
    val possibleValues: MutableMap<Char, List<Char>> = mutableMapOf(
        'a' to allPossibleValueList,
        'b' to allPossibleValueList,
        'c' to allPossibleValueList,
        'd' to allPossibleValueList,
        'e' to allPossibleValueList,
        'f' to allPossibleValueList,
        'g' to allPossibleValueList)
    private val confirmedValues: MutableMap<Char, Char> = mutableMapOf()


    init {
        val pipe = input.split(" | ")
        signals = pipe[0].split(" ")
        output = pipe[1].split(" ")
        deduceValues()
    }

    fun count1478(): Int {
        return output.filter { arrayOf(2, 3, 4, 7).contains(it.length) }.size
    }

    private fun deduceValues() {
        for (signal in listOf(signals, output).flatten()) {
            when (signal.length) {
                2 -> specifyPossibleValues(signal, listOf('c', 'f'))
                3 -> specifyPossibleValues(signal, listOf('a', 'c', 'f'))
                4 -> specifyPossibleValues(signal, listOf('b', 'c', 'd', 'f'))
                5 -> missingValues(signal).forEach { eliminateImpossibleValues(it, listOf('a', 'g')) }
                6 -> missingValues(signal).forEach { eliminateImpossibleValues(it, listOf('a', 'b', 'f', 'g')) }
            }

            for (entry in possibleValues) {
                if (entry.value.size == 1) {
                    confirmedValues[entry.key] = entry.value[0]
                    for (char in allPossibleValueList.minus(entry.key)) {
                        eliminateImpossibleValues(char, entry.value)
                    }
                }
            }

        }
    }

    fun calculateOutput(): Int {
        var result: String = ""
        for (digit in output) {
            result +=  digitLookup[digit.mapNotNull { confirmedValues[it] }.sorted().joinToString("")]
        }
        return result.toInt()
    }



    private fun specifyPossibleValues(signal: String, newPossibleValues: List<Char>) {
        for (char in signal) {
            possibleValues[char] = newPossibleValues.intersect(possibleValues[char]!!.toList()).toList()
        }
    }

    private fun eliminateImpossibleValues(char: Char, impossibleValues: List<Char>) {
        possibleValues[char] = possibleValues[char]!!.minus(impossibleValues)
    }

    private fun missingValues(signal: String): List<Char> {
        return allPossibleValueList.minus(signal.toList())
    }
}
