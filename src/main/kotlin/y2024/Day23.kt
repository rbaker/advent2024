package y2024

import utils.Graph

fun main() {
    val lines = Any::class::class.java.getResource("/y2024/day23.txt")?.readText()?.lines()?.map { it.split("-").let { (a, b) -> a to b } }!!
    val graph = Graph(lines)
    println(day23part1(graph))
    println(day23part2(graph).sorted().joinToString(","))
}

fun day23part1(graph: Graph): Int {
    val groups = mutableSetOf<List<String>>()
    graph.adjacencyMap.forEach { (key, value) ->
        value.forEach { v1 ->
            graph.adjacencyMap[v1]?.forEach { v2 ->
                if (v2 != key && graph.adjacencyMap[v2]?.contains(key)!!) groups.add(listOf(key, v1, v2).sorted())
            }
        }
    }
    return groups.count { f -> f.find { it.startsWith("t") }?.isNotEmpty() ?: false }
}

fun day23part2(graph: Graph): Set<String> {
    val nodes = graph.getNodes().toList()
    val matrix = Array(nodes.size) { BooleanArray(nodes.size) }
    val nodeIndex = nodes.withIndex().associate { it.value to it.index }
    graph.edges.forEach { (a, b) ->
        val i = nodeIndex[a]!!
        val j = nodeIndex[b]!!
        matrix[i][j] = true
        matrix[j][i] = true
    }
    return maximumClique(nodes, matrix)
}

fun maximumClique(nodes: List<String>, adjacency: Array<BooleanArray>): Set<String> {
    val result = mutableSetOf<String>()
    val current = mutableSetOf<String>()

    fun expand(candidateSet: Set<String>, excludedSet: Set<String>) {
        if (candidateSet.isEmpty() && excludedSet.isEmpty()) {
            if (current.size > result.size) {
                result.clear()
                result.addAll(current)
            }
            return
        }

        val pivot = (candidateSet + excludedSet).maxByOrNull { node ->
            candidateSet.count { adjacency[nodes.indexOf(node)][nodes.indexOf(it)] }
        }!!

        val newCandidates = candidateSet.filterNot { adjacency[nodes.indexOf(pivot)][nodes.indexOf(it)] }
        for (node in newCandidates) {
            current.add(node)

            val neighbors = nodes.filter { adjacency[nodes.indexOf(node)][nodes.indexOf(it)] }.toSet()
            expand(candidateSet.intersect(neighbors), excludedSet.intersect(neighbors))

            current.remove(node)
            candidateSet.minus(node)
            excludedSet.plus(node)
        }
    }

    expand(nodes.toSet(), emptySet())
    return result
}