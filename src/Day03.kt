import java.lang.Exception
import kotlin.math.abs

fun main() {
    fun getMostCommonBit(input: List<String>, index: Int): Int {
        var onesRatio = 0
        for (row in input) {
            if (row[index] == '1') {
                onesRatio += 1
            } else {
                onesRatio -= 1
            }
        }

        return if (onesRatio >= 0) 1 else 0
    }

    fun invertBit(bit: Int): Int {
        return abs(bit - 1)
    }

    fun part1(input: List<String>): Int {
        var gammaRate = ""
        var epsilonRate = ""

        for (i in input[0].indices) {
            val gamma = getMostCommonBit(input, i)
            val epsilon = invertBit(gamma)
            gammaRate += gamma.toString()
            epsilonRate += epsilon.toString()
        }
        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun filterInput(input: List<String>, index: Int, mostCommonBit: Char): List<String> {
        return input.filter { it[index] == mostCommonBit }
    }

    fun getRating(input: List<String>, doInvert: Boolean = false): Int {
        var filteredInput = input
        for (i in input[0].indices) {
            val bit = if (doInvert) {
                invertBit(getMostCommonBit(filteredInput, i))
            } else {
                getMostCommonBit(filteredInput, i)
            }
            filteredInput = filterInput(filteredInput, i, bit.toString()[0])
            if (filteredInput.size == 1) {
                return filteredInput[0].toInt(2)
            }
        }
        throw Exception("Multiple values for input set")
    }

    fun part2(input: List<String>): Int {
        return getRating(input) * getRating(input, true)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
