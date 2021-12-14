fun main() {
    fun part1(input: List<String>): Int {
        return Heightmap(input).getRiskLevelSum()
    }

    fun part2(input: List<String>): Int {
        val basins = Heightmap(input).buildBasins().sortedBy { -it.coordinates.size }
        return basins[0].coordinates.size * basins[1].coordinates.size * basins[2].coordinates.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput ) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

class Heightmap(input: List<String>) {
    private val matrix: List<List<Int>> = input.map { toIntArray(it, "") }
    private val lowPoints: List<Coordinate> = buildLowPoints()

    private fun buildLowPoints(): List<Coordinate> {
        val lowPoints: MutableList<Coordinate> = mutableListOf()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val coordinate = Coordinate(i, j, matrix[i][j])
                if (coordinate.neighbors(matrix).all { it.entry > coordinate.entry }) {
                    lowPoints.add(coordinate)
                }
            }
        }
        return lowPoints
    }

    fun getRiskLevelSum(): Int {
        return lowPoints.sumOf { 1 + it.entry }
    }

    fun buildBasins(): List<Basin> {
        val basins: MutableList<Basin> = mutableListOf()
        for (point in lowPoints) {
            val basin = Basin(point)
            basin.recurse(point, matrix)
            basins.add(basin)
        }
        return basins
    }
}



class Coordinate(val row: Int, val column: Int, val entry: Int) {
    companion object {
        val default = Coordinate(-1, -1, 10)
    }

    override fun equals(other: Any?): Boolean {
        return other is Coordinate && row == other.row && column == other.column && entry == other.entry
    }

    fun neighbors(matrix: List<List<Int>>): List<Coordinate> {
        return listOf(
            upstairsNeighbor(matrix),
            downstairsNeighbor(matrix),
            leftNeighbor(matrix),
            rightNeighbor(matrix)
        )
    }

    private fun upstairsNeighbor(matrix: List<List<Int>>): Coordinate {
        return if (row == 0 || this == default)
            default
        else
            Coordinate(row - 1, column, matrix[row -1][column])
    }

    private fun downstairsNeighbor(matrix: List<List<Int>>): Coordinate {
        return if (row == matrix.size - 1 || this == default)
            default
        else
            Coordinate(row + 1, column, matrix[row + 1][column])
    }

    private fun leftNeighbor(matrix: List<List<Int>>): Coordinate {
        return if (column == 0 || this == default)
            default
        else
            Coordinate(row, column - 1, matrix[row][column - 1])
    }

    private fun rightNeighbor(matrix: List<List<Int>>): Coordinate {
        return if (this == default || column == matrix[row].size - 1)
            default
        else
            Coordinate(row, column + 1, matrix[row][column + 1])
    }
}

class Basin(lowPoint: Coordinate) {
    val coordinates: MutableList<Coordinate> = mutableListOf(lowPoint)

    fun recurse(coordinate: Coordinate, matrix: List<List<Int>>) {
        for (neighbor in coordinate.neighbors(matrix)) {
            if (coordinates.contains(neighbor)) continue

            if (0.rangeTo(8).contains(neighbor.entry)) {
                coordinates.add(neighbor)
                recurse(neighbor, matrix)
            }
        }
    }
}
