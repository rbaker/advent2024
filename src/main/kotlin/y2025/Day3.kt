package y2025

fun main() {
    var part1 = 0L; var part2 = 0L
    Any::class::class.java.getResourceAsStream("/y2025/day3.txt")?.bufferedReader()?.forEachLine { line ->
        val batteries = line.toList().map { it.toString().toInt() }
        part1 += getHighest(batteries, 2)
        part2 += getHighest(batteries, 12)
    }
    println(part1)
    println(part2)
}

fun getHighest(batteries: List<Int>, length: Int): Long {
    val maxes = mutableListOf<Int>()
    var tempBatteries = batteries.toMutableList()
    for (i in length downTo 1) {
        val max = tempBatteries.subList(0, tempBatteries.size + 1 - i).max()
        maxes.add(max)
        tempBatteries = tempBatteries.subList(tempBatteries.indexOf(max) + 1, tempBatteries.size)
    }
    return maxes.joinToString("").toLong()
}