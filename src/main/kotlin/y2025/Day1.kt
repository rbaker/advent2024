package y2025

const val wheelsize = 100

fun main() {
    var position = 50; var part1 = 0; var part2 = 0
    Any::class::class.java.getResourceAsStream("/y2025/day1.txt")?.bufferedReader()?.forEachLine {line ->
        for (i in 0 until line.substring(1, line.length).toInt()) {
            if (line.get(0) == 'L') {
                position = (position - 1).mod(wheelsize)
            } else {
                position = (position + 1).mod(wheelsize)
            }
            if (position == 0) part2++
        }
        if (position == 0) part1++
    }
    println(part1)
    println(part2)
}