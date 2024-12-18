import java.awt.Point
import java.util.PriorityQueue

fun main() {
    val points = mutableListOf<Point>()
    Any::class::class.java.getResource("/day18.txt")?.readText()?.split("\r\n")?.forEach { p ->
        val a = p.split(",").map { it.toInt() }
        points.add(Point(a[0], a[1]))
    }
    println(getShortestPath(points, 1024, 70))
}

fun getShortestPath(points: List<Point>, limit: Int, width: Int): Int {
    val truncatedPoints = points.subList(0, limit)
    val locations = mutableListOf<Point>()
    val goal = Point(width, width)
    val pq = PriorityQueue<State>(compareBy<State> { it.cost })
    pq.add(State(Point(0, 0), 0, 0, mutableListOf(Point(0, 0))))
    var route = 0
    while (pq.isNotEmpty()) {
        val current = pq.poll()
        if (current.point == goal) {
            route = current.cost
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
    return route
}