package utils

data class Graph(val edges: List<Pair<String, String>>) {
    val adjacencyMap = mutableMapOf<String, MutableSet<String>>()

    init {
        edges.forEach { (a, b) ->
            adjacencyMap.computeIfAbsent(a) { mutableSetOf() }.add(b)
            adjacencyMap.computeIfAbsent(b) { mutableSetOf() }.add(a)
        }
    }

    fun getNodes(): Set<String> = adjacencyMap.keys
}