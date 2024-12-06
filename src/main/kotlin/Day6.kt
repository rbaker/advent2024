import java.awt.Point

fun main() {
    val points = mutableListOf<Point>()
    var guard: Guard? = null
    val lines = Any::class::class.java.getResourceAsStream("/day6.txt")?.bufferedReader()?.readLines()
    lines!!.forEachIndexed { l, line ->
        line.toCharArray().forEachIndexed { i, c ->
            if (c == '#') points.add(Point(i, l))
            if (c == '^') guard = Guard(Point(i, l), 'N')
        }
    }
    val locations = part1(points, Guard(guard!!.location, guard!!.direction), lines.size)
    println(locations.size)
    println(part2(points, Guard(guard!!.location, guard!!.direction), lines.size, locations))
}

fun part2(points: List<Point>, guard: Guard, size: Int, locations: Set<Point>): Int {
    var count = 0
    for (location in locations) {
        val guardLocations = mutableListOf<Guard>()
        var nextPosition = guard.nextPosition()
        val list = points.toMutableList()
        list.add(location)
        while (nextPosition.x in 0..<size && nextPosition.y in 0..<size) {
            if (list.contains(nextPosition)) guard.turn()
            else {
                guard.move()
                guardLocations.add(Guard(Point(guard.location), guard.direction))
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

class Guard (val location: Point, var direction: Char) {
    fun move() {
        val p = nextPosition()
        location.x = p.x
        location.y = p.y
    }
    fun nextPosition(): Point {
        val next = Point(location)
        when (direction) {
            'N' -> next.y--
            'E' -> next.x++
            'S' -> next.y++
            'W' -> next.x--
        }
        return next
    }
    fun turn() {
        when (direction) {
            'N' -> direction = 'E'
            'E' -> direction = 'S'
            'S' -> direction = 'W'
            'W' -> direction = 'N'
        }
    }
    fun equals(other: Guard): Boolean = location == other.location && direction == other.direction
}