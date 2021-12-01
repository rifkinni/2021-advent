fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        for (i in 1..input.size - 1) {
            if (input[i] > input[i - 1]) {
                count += 1
            }
        }
        return count
    }

    fun getCohort(input: List<String>, index: Int): Int {
        return input[index].toInt() + input[index + 1].toInt() + input[index + 2].toInt()
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (i in 1..input.size - 3) {
            if (getCohort(input, i) > getCohort(input, i - 1)) {
                count += 1
            }
        }
        return count
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
