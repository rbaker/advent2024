package y2024

import java.awt.Point
import java.util.*

fun main() {
    val maze = Any::class::class.java.getResource("/y2024/day16.txt")?.readText()?.lines()!!
    val optimalPaths = findAllOptimalPaths(maze)
    println(optimalPaths[0].first)
    println(optimalPaths.flatMap { it.second }.toSet().size)
}

fun findAllOptimalPaths(maze: List<String>): List<Pair<Int, List<Point>>> {
    val rows = maze.size
    val cols = maze[0].length
    val directionCost = 1000
    val moveCost = 1
    val start = Point(0, 0)
    val goal = Point(0, 0)

    maze.forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            when (c) {
                'S' -> start.setLocation(i, j)
                'E' -> goal.setLocation(i, j)
            }
        }
    }

    val pq = PriorityQueue(compareBy<State> { it.cost })
    val visitedPaths = mutableMapOf<Triple<Int, Int, Int>, MutableList<List<Point>>>()
    val costs = Array(rows) { Array(cols) { IntArray(4) { Int.MAX_VALUE } } }
    val allPaths = mutableListOf<Pair<Int, List<Point>>>()

    costs[start.x][start.y][2] = 0
    pq.add(State(start, 2, 0, listOf(start)))

    var minCost = Int.MAX_VALUE

    while (pq.isNotEmpty()) {
        val current = pq.poll()
        if (current.cost > minCost) continue
        val currentKey = Triple(current.point.x, current.point.y, current.dir)

        val pathsAtCurrent = visitedPaths.getOrPut(currentKey) { mutableListOf() }
        pathsAtCurrent.add(current.path)

        if (current.point == goal) {
            if (current.cost < minCost) {
                minCost = current.cost
                allPaths.clear()
            }
            allPaths.add(current.cost to current.path)
            continue
        }

        val forward = Point(
            current.point.x + directions[current.dir].first,
            current.point.y + directions[current.dir].second
        )
        if (
            forward.x in 0 .. rows &&
            forward.y in 0 .. cols &&
            maze[forward.x][forward.y] != '#' &&
            current.cost + moveCost <= costs[forward.x][forward.y][current.dir]
        ) {
            costs[forward.x][forward.y][current.dir] = current.cost + moveCost
            pq.add(State(forward, current.dir, current.cost + moveCost, current.path + forward))
        }

        val leftDir = (current.dir + 3) % 4
        if (current.cost + directionCost <= costs[current.point.x][current.point.y][leftDir]) {
            costs[current.point.x][current.point.y][leftDir] = current.cost + directionCost
            pq.add(State(current.point, leftDir, current.cost + directionCost, current.path))
        }

        val rightDir = (current.dir + 1) % 4
        if (current.cost + directionCost <= costs[current.point.x][current.point.y][rightDir]) {
            costs[current.point.x][current.point.y][rightDir] = current.cost + directionCost
            pq.add(State(current.point, rightDir, current.cost + directionCost, current.path))
        }
    }
    return allPaths
}

data class State(val point: Point, val dir: Int, val cost: Int, val path: List<Point>)
