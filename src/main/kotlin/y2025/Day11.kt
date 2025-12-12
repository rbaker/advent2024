package y2025


fun main() {
    val network = mutableMapOf<String, List<String>>()
    Any::class::class.java.getResourceAsStream("/y2025/day11.txt")!!.bufferedReader().forEachLine { line ->
        val parts = line.split(": ")
        network.put(parts[0], parts[1].split(" "))
    }
    println(part1("you", network)) // part 1
    println(part2("svr", network)) // part 2
}

fun part1(node: String, map: Map<String, List<String>>, memo: MutableMap<String, Long> = mutableMapOf()): Long {
    memo[node]?.let { return it }
    if (node == "out") return 1L
    val totalPaths = map[node]?.sumOf { child ->
        part1(child, map, memo)
    } ?: 0L
    memo[node] = totalPaths
    return totalPaths
}

fun part2(node: String, map: Map<String, List<String>>,
    memo: MutableMap<Triple<String, Boolean, Boolean>, Long> = mutableMapOf(),
    seenFft: Boolean = false, seenDac: Boolean = false
): Long {
    val key = Triple(node, seenFft, seenDac)
    memo[key]?.let { return it }
    val hasFft = seenFft || node == "fft"
    val hasDac = seenDac || node == "dac"
    if (node == "out") return if (hasFft && hasDac) 1L else 0L
    val totalPaths = map[node]?.sumOf { child ->
        part2(child, map, memo, hasFft, hasDac)
    } ?: 0L
    memo[key] = totalPaths
    return totalPaths
}