fun main() {
    fun getBoards(input: List<String>): List<BingoBoard> {
        return (2 until input.size step 6).map { i ->
            BingoBoard(input.slice(i..i+4))
        }

    }

    fun part1(calledNumbers: List<Int>, boards: List<BingoBoard>): Int {
        for (i in calledNumbers.indices) {
            val subsetOfNumbers = calledNumbers.slice(0..i)
            for (board in boards) {
                if (board.hasBingo(subsetOfNumbers)) {
                    return board.calculateFinalAnswer(subsetOfNumbers)
                }
            }
        }

        throw Exception ("no boards have bingo with the called numbers")
    }

    fun part2(calledNumbers: List<Int>, boards: List<BingoBoard>): Int {
        var boards = boards

        for (i in calledNumbers.indices) {
            val subsetOfNumbers = calledNumbers.slice(0..i)

            if (boards.size > 1) {
                boards = boards.filterNot { it.hasBingo(subsetOfNumbers) }
            } else if (boards[0].hasBingo(subsetOfNumbers)) {
                return boards[0].calculateFinalAnswer(subsetOfNumbers)
            }
        }

        throw Exception ("Something went wrong")
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val testNumbers = toIntArray(testInput[0], ",")
    val testBoards = getBoards(testInput)

    check(part1(testNumbers, testBoards) == 4512)
    check(part2(testNumbers, testBoards) == 1924)

    val input = readInput("Day04")
    val numbers = toIntArray(input[0], ",")
    val boards = getBoards(input)
    println(part1(numbers, boards))
    println(part2(numbers, boards))
}

class BingoBoard(input: List<String>) {
    val matrix = input.map { toIntArray(it) }

    fun hasBingo(calledNumbers: List<Int>): Boolean {
        // check rows
        for (row in matrix) {
            if (row.all { calledNumbers.contains(it) })
                return true
        }

        // check columns
        for (column in matrix[0].indices) {
            if (matrix.map { it[column] }.all { calledNumbers.contains(it) })
                return true
        }
        return false
    }

    fun calculateFinalAnswer(calledNumbers: List<Int>): Int {
        return sumUnmarkedNumbers(calledNumbers) * calledNumbers.last()
    }

    private fun sumUnmarkedNumbers(calledNumbers: List<Int>): Int {
        var sum = 0

        for (row in matrix) {
            for (element in row) {
                if (!calledNumbers.contains(element)) {
                    sum += element
                }
            }
        }
        return sum
    }
}
