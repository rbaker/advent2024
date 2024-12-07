import utils.Guard
import java.awt.Point

fun main() {
    val points = mutableListOf<Point>()
    var start: Point? = null
    val lines = Any::class::class.java.getResourceAsStream("/day6.txt")?.bufferedReader()?.readLines()
    lines!!.forEachIndexed { l, line ->
        line.toCharArray().forEachIndexed { i, c ->
            if (c == '#') points.add(Point(i, l))
            if (c == '^') start = Point(i, l)
        }
    }
    val locations = part1(points, Guard(Point(start!!.x, start!!.y), 'N'), lines.size)
    println(locations.size)
    println(part2(points, start!!.x, start!!.y, lines.size, locations))

}

fun part1(points: List<Point>, guard: Guard, size: Int): Set<Point> {
    var nextPosition = guard.nextPosition()
    val locations = mutableSetOf<Point>()
    locations.add(Point(guard.location))
    while (nextPosition.x in 0..<size && nextPosition.y in 0..<size) {
        if (points.contains(nextPosition)) guard.turn()
        else {
            guard.move()
            locations.add(Point(guard.location))
        }
        nextPosition = guard.nextPosition()
    }
    return locations
}

fun part2(points: List<Point>, x: Int, y: Int, size: Int, locations: Set<Point>): Int {
    var count = 0
    for (location in locations) {
        val guardLocations = mutableListOf<Guard>()
        val guard = Guard(Point(x, y), 'N')
        var nextPosition = guard.nextPosition()
        val list = points.toMutableList()
        list.add(location)
        while (nextPosition.x in 0..<size && nextPosition.y in 0..<size) {
            if (list.contains(nextPosition)) guard.turn()
            else {
                guardLocations.add(Guard(Point(guard.location), guard.direction))
                guard.move()
            }
            if (guardLocations.contains(guard)) {
                count++
                break
            }
            nextPosition = guard.nextPosition()
        }
    }
    return count
}
