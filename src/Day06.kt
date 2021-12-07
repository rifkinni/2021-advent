fun main() {
    fun parseInput(filename: String): List<Int> {
        return toIntArray(readInput(filename)[0], ",")
    }

    fun part1(input: List<Int>, days: Int = 80): Int {
        var lanternfish = mutableListOf<Int>()
        lanternfish.addAll(input)

        for (day in 1..days) {
            for (i in lanternfish.indices) {
                if (lanternfish[i] == 0) {
                    lanternfish[i] += 6
                    lanternfish.add(8)
                } else {
                    lanternfish[i] -= 1
                }
            }
        }

        return lanternfish.size
    }

    fun buildInitialMap(lanternfish: List<Int>): Map<Int, Long> {
        val map = mutableMapOf(0 to 0L, 1 to 0L, 2 to 0L, 3 to 0L, 4 to 0L, 5 to 0L, 6 to 0L, 7 to 0L, 8 to 0L)
        for (fish in lanternfish) {
            map[fish] = map[fish]!!.plus(1L)
        }
        return map
    }

    fun part2(input: List<Int>, days: Int = 256): Long {
        var oldMap = buildInitialMap(input)
        for (day in 1..days) {
            val newMap = mutableMapOf(
                0 to (oldMap[1] ?: 0),
                1 to (oldMap[2] ?: 0),
                2 to (oldMap[3] ?: 0),
                3 to (oldMap[4] ?: 0),
                4 to (oldMap[5] ?: 0),
                5 to (oldMap[6] ?: 0),
                6 to (oldMap[7] ?: 0) + (oldMap[0] ?: 0),
                7 to (oldMap[8] ?: 0),
                8 to (oldMap[0] ?: 0))
            oldMap = newMap
        }

        return oldMap.values.sumOf { it }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = parseInput("Day06_test")
    check(part2(testInput, 18) == 26L)
    check(part2(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539)

    val input = parseInput("Day06")
    println(part1(input))
    println(part2(input))
}
