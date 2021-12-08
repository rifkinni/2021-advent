import kotlin.math.abs

fun main() {
    fun parseInput(filename: String): List<Int> {
        return toIntArray(readInput(filename)[0], ",")
    }

    fun calculateFuel(position: Int, crabs: List<Int>): Int {
        return crabs.sumOf { crab -> abs(crab - position) }
    }

    fun calculateFuel2(position: Int, crabs: List<Int>): Int {
        return crabs.sumOf {  crab -> (0..abs(crab - position)).sum() }
    }

    fun part1(input: List<Int>): Int {
        val fuelCosts = mutableMapOf<Int, Int>()

        for (crab in input) {
            if (fuelCosts[crab] == null)
                fuelCosts[crab] = calculateFuel(crab, input)
        }

        return fuelCosts.values.minOf { it }
    }

    fun part2(input: List<Int>): Int {
        val fuelCosts = mutableMapOf<Int, Int>()
        val max = input.maxOf { it }

        for (position in 0..max) {
            fuelCosts[position] = calculateFuel2(position, input)
        }

        return fuelCosts.values.minOf { it }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = parseInput("Day07_test")
    check(part1(testInput ) == 37)
    check(part2(testInput) == 168)

    val input = parseInput("Day07")
    println(part1(input))
    println(part2(input))
}
