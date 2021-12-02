fun main() {
    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0

        for (row in input) {
            val (direction, magnitude) = row.split(" ")
            if (direction == "forward") {
                position += magnitude.toInt()
            } else if (direction == "up") {
                depth -= magnitude.toInt()
            } else if (direction == "down") {
                depth += magnitude.toInt()
            }
        }
        return position * depth
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var depth = 0
        var aim = 0

        for (row in input) {
            val (direction, magnitude) = row.split(" ")
            if (direction == "forward") {
                position += magnitude.toInt()
                depth += (aim * magnitude.toInt())
            } else if (direction == "up") {
                aim -= magnitude.toInt()
            } else if (direction == "down") {
                aim += magnitude.toInt()
            }
        }
        return position * depth
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
