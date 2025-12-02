package y2024

val directions = listOf(Pair(0, -1), Pair(-1, 0), Pair(0, 1), Pair(1, 0))
fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/y2024/day10.txt")?.bufferedReader()?.readLines()!!
    val grid = mutableListOf<List<Int>>()
    lines.forEach { line -> grid.add(line.toCharArray().map { it.toString().toInt() }) }
    println(day10part1(grid))
    println(day10part2(grid))
}

fun day10part1(grid: List<List<Int>>): Int {
    fun dfs(x: Int, y: Int, currentHeight: Int, visited: Array<BooleanArray>, reachableNines: MutableSet<Pair<Int, Int>>) {
        if (grid[x][y] == 9) {
            reachableNines.add(Pair(x, y))
            return
        }
        visited[x][y] = true
        for ((dx, dy) in directions) {
            val newX = x + dx
            val newY = y + dy
            if (newX in grid.indices && newY in grid.indices && !visited[newX][newY] && grid[newX][newY] == currentHeight + 1) {
                dfs(newX, newY, currentHeight + 1, visited, reachableNines)
            }
        }
        visited[x][y] = false // Unmark the cell before backtracking
    }

    var result = 0
    for (x in grid.indices) {
        for (y in grid[x].indices) {
            if (grid[x][y] == 0) {
                // For each `0`, count the reachable `9`s using DFS
                val visited = Array(grid.size) { BooleanArray(grid.size) }
                val reachableNines = mutableSetOf<Pair<Int, Int>>()
                dfs(x, y, 0, visited, reachableNines)
                result += reachableNines.size
            }
        }
    }
    return result
}

fun day10part2(grid: List<List<Int>>): Int {
    fun dfs(x: Int, y: Int, currentHeight: Int, visited: Array<BooleanArray>): Int {
        if (grid[x][y] == 9) return 1
        visited[x][y] = true
        var pathCount = 0
        for ((dx, dy) in directions) {
            val nx = x + dx
            val ny = y + dy
            if (nx in grid.indices && ny in grid.indices && !visited[nx][ny] && grid[nx][ny] == currentHeight + 1) {
                pathCount += dfs(nx, ny, currentHeight + 1, visited)
            }
        }
        visited[x][y] = false
        return pathCount
    }
    val results = mutableListOf<Int>()
    for (x in grid.indices) {
        for (y in grid[x].indices) {
            if (grid[x][y] == 0) {
                val visited = Array(grid.size) { BooleanArray(grid[x].size) }
                results.add(dfs(x, y, 0, visited))
            }
        }
    }
    return results.reduce(Int::plus)
}