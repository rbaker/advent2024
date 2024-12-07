fun main() {
    var works = 0L; var worksWithConcat = 0L
    val lines = Any::class::class.java.getResourceAsStream("/day7.txt")?.bufferedReader()?.readLines()
    lines!!.forEach { line ->
        val sections = line.split(":")
        if (canReachTarget(sections[0].toLong(), sections[1].trim().split(" ").map { it.toLong() }, false)) works += sections[0].toLong()
        if (canReachTarget(sections[0].toLong(), sections[1].trim().split(" ").map { it.toLong() }, true)) worksWithConcat += sections[0].toLong()
    }
    println(works)
    println(worksWithConcat)
}

fun canReachTarget(target: Long, numbers: List<Long>, includeConcat: Boolean): Boolean {
    fun helper(index: Int, current: Long, includeConcat: Boolean): Boolean {
        if (index == numbers.size) {
            return current == target
        }
        val nextNumber = numbers[index]
        return helper(index + 1, current + nextNumber, includeConcat) ||
                helper(index + 1, current * nextNumber, includeConcat) ||
                (includeConcat && helper(index + 1, current.toString().plus(nextNumber).toLong(), true))
    }

    return helper(1, numbers[0], includeConcat)
}