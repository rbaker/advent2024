import java.awt.Point
import kotlin.math.abs

fun main() {
    val start = Point(0,0)
    val end = Point(0,0)
    val walls = mutableListOf<Point>()
    val lines = Any::class::class.java.getResource("/day20.txt")?.readText()?.lines()?.map { it.toCharArray() }!!
    lines.forEachIndexed { i, l ->
        l.forEachIndexed { j, c ->
            when (c) {
                'S' -> start.setLocation(i, j)
                'E' -> end.setLocation(i, j)
                '#' -> walls.add(Point(i, j))
            }
        }
    }
    val withoutCheat = getShortestPath(walls, walls.size, lines.size, start, end).path
    println(getCheats(withoutCheat, 2))
    println(getCheats(withoutCheat, 20))
}

fun getCheats(path: List<Point>, searchRadius: Int): Int {
    var result = 0
    path.forEachIndexed { i, c ->
        val a = path.filter { abs(it.x - c.x) + abs(it.y - c.y) <= searchRadius }.map { path.indexOf(it) }
        a.subList(a.indexOf(i), a.size).forEach { pos ->
            val save = pos - i
            val manhattan = abs(path[pos].x - path[i].x) + abs(path[pos].y - path[i].y)
            if (save - manhattan >= 100) result++
        }
    }
    return result
}