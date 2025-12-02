package y2024

fun main() {
    val parts = Any::class::class.java.getResource("/y2024/day19.txt")?.readText()?.split("\r\n\r\n")!!
    val fragments = parts[0].split(",").map { it.trim() }
    var part1 = 0; var part2 = 0L
    parts[1].split("\r\n").forEach { target ->
        val i = countWaysToConstruct(target, fragments)
        if (i > 0) part1++
        part2 += i
    }
    println(part1)
    println(part2)
}

fun countWaysToConstruct(target: String, fragments: List<String>): Long {
    val n = target.length
    val dp = LongArray(n + 1) { 0 }
    dp[0] = 1

    for (i in 1..n) {
        for (fragment in fragments) {
            if (i >= fragment.length && target.substring(i - fragment.length, i) == fragment) {
                dp[i] += dp[i - fragment.length]
            }
        }
    }
    return dp[n]
}