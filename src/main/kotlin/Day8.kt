import java.awt.Point

fun main() {
    val antennaMap = hashMapOf<Char, MutableList<Point>>()
    val lines = Any::class::class.java.getResourceAsStream("/day8.txt")?.bufferedReader()?.readLines()!!
    lines.forEachIndexed { l, line ->
        line.toCharArray().forEachIndexed { i, c ->
            if (c != '.')antennaMap.computeIfAbsent(c) { mutableListOf() }.add(Point(i, l))
        }
    }
    val antinodesPart1 = mutableSetOf<Point>(); val antinodesPart2 = mutableSetOf<Point>()
    antennaMap.forEach { list ->
        getAllCombinations(list.value).forEach {
            var node = Point(it.first.x + (it.first.x - it.second.x), it.first.y + (it.first.y - it.second.y))
            if (node.x in lines.indices && node.y in lines.indices) antinodesPart1.add(node)
            antinodesPart2.add(it.first)
            while (node.x in lines.indices && node.y in lines.indices) {
                antinodesPart2.add(node)
                node = Point(node.x + (it.first.x - it.second.x), node.y + (it.first.y - it.second.y))
            }
        }
    }
    println(antinodesPart1.size)
    println(antinodesPart2.size)
}

fun getAllCombinations(list: List<Point>): List<Pair<Point, Point>> {
    val pairs = mutableListOf<Pair<Point, Point>>()
    list.forEach { p1 ->
        list.forEach {p2 ->
            if (p1 != p2) pairs.add(Pair(p1, p2))
        }
    }
    return pairs
}