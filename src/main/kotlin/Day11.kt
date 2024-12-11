fun main() {
    val numbers = Any::class::class.java.getResource("/day11.txt")?.readText()?.split(" ")
    println(getStoneCount(numbers!!, 25))
    println(getStoneCount(numbers, 75))
}

fun getStoneCount(numbers: List<String>, iterations: Int): Long {
    var counts = mutableMapOf<String, Long>()
    numbers.forEach {
        counts[it] = counts.getOrDefault(it, 0L) + 1L
    }
    repeat(iterations) {
        val nextCounts = mutableMapOf<String, Long>()
        counts.forEach {
            when {
                it.key == "0" -> nextCounts["1"] = nextCounts.getOrDefault("1", 0L) + it.value
                it.key.length % 2 == 0 -> {
                    val firstHalf = it.key.substring(0, it.key.length / 2)
                    val secondHalf = it.key.substring(it.key.length / 2, it.key.length).toLong().toString()
                    nextCounts[firstHalf] = nextCounts.getOrDefault(firstHalf, 0L) + it.value
                    nextCounts[secondHalf] = nextCounts.getOrDefault(secondHalf, 0L) + it.value
                }
                else -> nextCounts[(it.key.toLong() * 2024).toString()] = nextCounts.getOrDefault((it.key.toLong() * 2024).toString(), 0L) + it.value
            }
        }
        counts = nextCounts
    }
    return counts.values.sum()
}