package y2024

import kotlin.math.abs

fun main() {
    val lists = Pair(mutableListOf<Int>(), mutableListOf<Int>())

    Any::class::class.java.getResourceAsStream("/y2024/day1.txt")?.bufferedReader()?.forEachLine { line ->
        val parts = line.split("   ")
        lists.first.add(parts[0].toInt())
        lists.second.add(parts[1].toInt())
    }
    val itemCount = lists.second.groupBy { it }.mapValues { (_, v) -> v.size }
    var part1 = 0; var part2 = 0
    lists.first.sort(); lists.second.sort()

    lists.first.forEachIndexed { i, v ->
        part1 += abs(v - lists.second[i])
        part2 += v * itemCount.getOrDefault(v, 0)
    }

    println(part1)
    println(part2)

}
