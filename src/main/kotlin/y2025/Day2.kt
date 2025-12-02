package y2025

fun main() {
    var part1 = 0L; var part2 = 0L
    val part1Regex = Regex("\\b(\\w+)\\1\\b")
    val part2Regex = Regex("\\b(\\w+)\\1+\\b")
    Any::class::class.java.getResourceAsStream("/y2025/day2.txt")?.bufferedReader()?.readText()?.split(",")?.forEach { text ->
        val parts = text.split("-")
        for (i in parts[0].toLong()..parts[1].toLong()) {
            val string = i.toString()
            if (part1Regex.matches(string)) {
                part1 += i
            }
            if (part2Regex.matches(string)) {
                part2 += i
            }
        }
    }
    println("looped $ times")
    println(part1)
    println(part2)
}
