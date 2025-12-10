package y2025

import kotlin.math.abs
import kotlin.math.max

fun main() {
    var part1 = 0L
    val lines = Any::class::class.java.getResourceAsStream("/y2025/day9.txt")?.bufferedReader()?.readLines()!!
    lines.forEachIndexed { i, line ->
        val xy = line.split(",").map { it.toLong() }
        (i + 1..<lines.size).forEach {
            val xy2 = lines[it].split(",").map { it.toLong() }
            val area = (abs(xy[0] - xy2[0]) + 1) * (abs(xy[1] - xy2[1]) + 1)
            part1 = max(part1, area)
        }
    }
    println(part1)
}