package y2025

import kotlin.math.max

fun main() {
    val parts =
        Any::class::class.java.getResourceAsStream("/y2025/day5.txt")?.bufferedReader()?.readText()?.split("\n\n")
    val ranges = mutableListOf<Pair<Long, Long>>()
    parts?.get(0)?.split("\n")?.forEach {
        val nums = it.split("-").map { s -> s.toLong() }
        ranges.add(Pair(nums[0], nums[1]))
    }
    ranges.sortWith(Comparator { i1, i2 -> i1.first.compareTo(i2.first) })
    var part1 = 0; var part2 = 0L ; var highestEnd = 0L
    ranges.forEach {
        val uniqueRange = Pair(if (it.first > highestEnd) it.first else highestEnd + 1, it.second)
        highestEnd = max(highestEnd, it.second)
        part2 += max(uniqueRange.second - uniqueRange.first + 1, 0L)
    }
    parts?.get(1)?.split("\n")?.forEach { line ->
        for (range in ranges) {
            if (line.toLong() in range.first..range.second) {
                part1++
                break
            }
        }
    }
    println(part1)
    println(part2)
}