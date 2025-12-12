package y2025

fun main() {
    val network = mutableMapOf<String, List<String>>()
    Any::class::class.java.getResourceAsStream("/y2025/day11.txt")!!.bufferedReader().forEachLine { line ->
        val parts = line.split(": ")
        network.put(parts[0], parts[1].split(" "))
    }
    println(countRootToLeafPaths("you", network, mutableMapOf()).second) // part 1
    val memo = mutableMapOf<String, Pair<List<String>, Long>>()
    println(countRootToLeafPaths("svr", network, memo).second) // part 2
}

fun countRootToLeafPaths(node: String, map: Map<String, List<String>>,
                         memo: MutableMap<String, Pair<List<String>, Long>>,
                         path: List<String> = listOf()): Pair<List<String>, Long> {
    memo[node]?.let { return it }
    val currentPath = path + node
    if (node == "out") {
        return Pair(currentPath + "out", 1L)
    }
    val totalPaths = map[node]?.sumOf { child ->
        countRootToLeafPaths(child, map, memo, currentPath).second
    } ?: 0L
    memo[node] = Pair(currentPath, totalPaths)
    return Pair(currentPath, totalPaths)
}
