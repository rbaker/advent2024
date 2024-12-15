import kotlin.math.abs

val robotString = "p=(-?\\d*),(-?\\d*) v=(-?\\d*),(-?\\d*)".toRegex()
const val WIDTH = 101; const val HEIGHT = 103
fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/day14.txt")?.bufferedReader()?.readLines()!!
    println(getScore(getPositions(lines, 100)))
    val positions = mutableListOf<Triple<Int, Int, Long>>()
    var runningTotal = 0L
    for (i in 0..10000) {
        val score = getScore(getPositions(lines, i))
        runningTotal += score
        positions.add(Triple(i, score, abs(score - (runningTotal / (i + 1)))))
    }
    println(positions.maxBy { it.third }.first)
}

fun getPositions(robots: List<String>, seconds: Int): List<Pair<Int, Int>> {
    val points = mutableListOf<Pair<Int, Int>>()
    robots.forEach {
        val parts = robotString.find(it)!!
        val start = Pair(parts.groupValues[1].toInt(), parts.groupValues[2].toInt())
        val velocity = Pair(parts.groupValues[3].toInt(), parts.groupValues[4].toInt())
        val xLocation = ((velocity.first * seconds) + start.first).mod(WIDTH)
        val yLocation = ((velocity.second * seconds) + start.second).mod(HEIGHT)
        points.add(Pair(xLocation, yLocation))
    }
    return points
}

fun getScore(points: List<Pair<Int, Int>>): Int {
    return points.count { it.first in 0..<(WIDTH - 1) / 2 && it.second in 0..<(HEIGHT - 1) / 2 } *
            points.count { it.first in (WIDTH + 1) / 2..<WIDTH && it.second in 0..<(HEIGHT - 1) / 2 } *
            points.count { it.first in 0..<(WIDTH - 1) / 2 && it.second in (HEIGHT + 1) / 2..<HEIGHT } *
            points.count { it.first in (WIDTH + 1) / 2..<WIDTH && it.second in (HEIGHT + 1) / 2..<HEIGHT }
}