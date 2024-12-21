import java.awt.Point
import java.util.PriorityQueue

fun main() {
    val points = mutableListOf<Point>()
    Any::class::class.java.getResource("/day18.txt")?.readText()?.split("\r\n")?.forEach { p ->
        val a = p.split(",").map { it.toInt() }
        points.add(Point(a[0], a[1]))
    }
    val limit = 1024; val width = 70
    println(getShortestPath(points, limit, width).cost)
    for (i in limit..points.size) {
        if (getShortestPath(points, i, width).cost == 0) {
            println(points[i-1])
            break
        }
    }
}

fun getShortestPath(points: List<Point>, limit: Int = points.size, width: Int,
                    start: Point = Point(0,0), goal: Point = Point(width, width)): State {
    val truncatedPoints = points.subList(0, limit)
    val locations = mutableListOf<Point>()
    val pq = PriorityQueue(compareBy<State> { it.cost })
    pq.add(State(start, 0, 0, mutableListOf(start)))
    var end = State(Point(0, 0), 0, 0, mutableListOf())
    while (pq.isNotEmpty()) {
        val current = pq.poll()
        if (current.point == goal) {
            end = current
            break
        }
        for (d in directions) {
            val next = Point(current.point.x + d.first, current.point.y + d.second)
            if (next.x in 0..width && next.y in 0..width && next !in locations && next !in truncatedPoints) {
                pq.add(State(next, 0, current.cost + 1, current.path + next))
                locations.add(next)
            }
        }
        locations.add(current.point)
    }
    return end
}