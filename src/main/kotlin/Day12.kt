import java.awt.Point

fun main() {
    val grid = Any::class::class.java.getResourceAsStream("/day12.txt")?.bufferedReader()?.readLines()!!
    println(calculateRegions(grid, false))
    println(calculateRegions(grid, true))
}

fun calculateRegions(grid: List<String>, distinct: Boolean): Int {
    val visited = Array(grid.size) { BooleanArray(grid.size) }

    fun floodFill(r: Int, c: Int, letter: Char): Pair<Int, List<Perimeter>> {
        val stack = mutableListOf(Pair(r, c))
        var area = 0
        val perimeter = mutableListOf<Perimeter>()

        while (stack.isNotEmpty()) {
            val (x, y) = stack.removeAt(stack.lastIndex)
            if (visited[x][y]) continue
            visited[x][y] = true
            area++

            directions.forEachIndexed { index, (dx, dy) ->
                val nx = x + dx
                val ny = y + dy
                if (nx < 0 || ny < 0 || nx >= grid.size || ny >= grid.size || grid[nx][ny] != letter) {
                    perimeter.add(Perimeter(Point(x, y), when (index) {
                        0 -> 'U'
                        1 -> 'D'
                        2 -> 'L'
                        3 -> 'R'
                        else -> throw IllegalArgumentException("Invalid direction index: $index")
                    }))
                } else if (!visited[nx][ny]) {
                    stack.add(Pair(nx, ny))
                }
            }
        }

        return Pair(area, perimeter)
    }

    var res = 0
    for (r in grid.indices) {
        for (c in grid.indices) {
            if (!visited[r][c]) {
                val letter = grid[r][c]
                val (area, perimeter) = floodFill(r, c, letter)
                res += area * if (distinct) countContiguousBoundaries(perimeter) else perimeter.size
            }
        }
    }
    return res
}

fun countContiguousBoundaries(perimeters: List<Perimeter>): Int {
    val grouped = perimeters.groupBy { it.direction }
    var distinctBoundaries = 0
    for ((direction, boundaries) in grouped) {
        val sortedBoundaries = when (direction) {
            'U', 'D' -> boundaries.sortedWith(compareBy({ it.point.x }, { it.point.y }))
            'L', 'R' -> boundaries.sortedWith(compareBy({ it.point.y }, { it.point.x }))
            else -> throw IllegalArgumentException("Invalid direction: $direction")
        }

        // Traverse and count distinct segments
        for (i in sortedBoundaries.indices) {
            if (i == 0 || !isContiguous(sortedBoundaries[i - 1], sortedBoundaries[i])) {
                distinctBoundaries++
            }
        }
    }

    return distinctBoundaries
}

fun isContiguous(a: Perimeter, b: Perimeter): Boolean {
    return when (a.direction) {
        'U', 'D' -> a.point.x == b.point.x && a.point.y + 1 == b.point.y
        'L', 'R' -> a.point.y == b.point.y && a.point.x + 1 == b.point.x
        else -> false
    }
}

class Perimeter(val point: Point, val direction: Char)