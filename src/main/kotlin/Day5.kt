
fun main() {
    val parts = Any::class::class.java.getResource("/day5.txt")?.readText()?.split("\r\n\r\n")!!
    val largerMap = hashMapOf<String, MutableList<String>>().withDefault { mutableListOf() }
    parts[0].split("\r\n").forEach {
        val split = it.split("|")
        largerMap.computeIfAbsent(split[0]) { mutableListOf() }.add(split[1])
    }
    var part1 = 0; var part2 = 0
    val failList = mutableListOf<List<String>>()
    parts[1].split("\r\n").forEach {
        val numbers = it.split(",")
        for (i in numbers.indices) {
            if (numbers.subList(0, i).any { num -> largerMap.getValue(numbers[i]).contains(num) }) {
                failList.add(numbers)
                return@forEach
            }
        }
        part1 += numbers[numbers.size / 2].toInt()
    }
    println(part1)
    failList.forEach {
        val sorted = it.sortedWith(Comparator { o1: String, o2: String ->
            var comp = 1
            if (largerMap.getValue(o1).contains(o2)) comp = -1
            return@Comparator comp
        })
        part2 += sorted[sorted.size / 2].toInt()
    }
    println(part2)
}