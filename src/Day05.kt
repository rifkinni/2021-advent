import kotlin.math.min
import kotlin.math.max

fun main() {
    fun parseInput(filename: String): List<Coordinates> {
        return readInput(filename).map { Coordinates(it) }
    }

    fun part1(input: List<Coordinates>): Int {
        val lineMap = mutableMapOf<String, Int>().withDefault { 0 }
        input.filter { it.isHorizontalOrVerticalLine() }.flatMap { it.getMapKeysForLine() }.forEach {
            lineMap[it] = lineMap.getValue(it).plus(1)
        }
        return lineMap.filterValues { it > 1 }.size
    }

    fun part2(input: List<Coordinates>): Int {
        val lineMap = mutableMapOf<String, Int>().withDefault { 0 }
        input.flatMap { it.getMapKeysForLine() }.forEach {
            lineMap[it] = lineMap.getValue(it).plus(1)
        }
        return lineMap.filterValues { it > 1 }.size
    }


    // test if implementation meets criteria from the description, like:
    val testInput = parseInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = parseInput("Day05")
    println(part1(input))
    println(part2(input))
}

class Coordinates(input: String) {
    private val x1: Int
    private val y1: Int
    private val x2: Int
    private val y2: Int
    private val xStep: Int
    private val yStep: Int

    init {
        var input = input.split(" -> ")
        x1 = input[0].split(",")[0].toInt()
        x2 = input[1].split(",")[0].toInt()
        y1 = input[0].split(",")[1].toInt()
        y2 = input[1].split(",")[1].toInt()

        xStep = if ((x2 - x1) > 0) {
            1
        } else if ((x2 - x1) == 0) {
            0
        } else {
            -1
        }

        yStep = if ((y2 - y1) > 0) {
            1
        } else if ((y2 - y1) == 0) {
            0
        } else {
            -1
        }
    }

    private val xRange = min(x1, x2)..max(x1, x2)
    private val yRange = min(y1, y2)..max(y1, y2)

    fun isHorizontalOrVerticalLine(): Boolean {
        return (x1 == x2) || (y1 == y2)
    }

    fun getMapKeysForLine(): List<String> {
        var keys = emptyList<String>()
        var x = x1
        var y = y1

        while (xRange.contains(x) && yRange.contains(y)) {
            keys = keys.plus(getMapKey(x, y))
            x += xStep
            y += yStep
        }
        return keys
    }

    private fun getMapKey(x: Int, y: Int): String {
        return "$x,$y"
    }
}
