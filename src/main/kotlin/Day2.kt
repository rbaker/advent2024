import kotlin.math.abs

fun main() {
    var safeCount = 0; var safeWithRemoval = 0
    Any::class::class.java.getResourceAsStream("/day2.txt")?.bufferedReader()?.forEachLine { line ->
        val parts = line.split(" ").map { s -> s.toInt() }
        if (checkList(parts)) {
            safeCount++
        } else if (safeWithRemoval(parts)) {
            safeWithRemoval++
        }
    }
    println(safeCount)
    println(safeCount + safeWithRemoval)
}

fun checkList(numbers: List<Int>): Boolean {
    var increased = false; var decreased = false
    for (i in 0..<numbers.size-1) {
        val diff = numbers[i] - numbers[i+1]
        if (diff > 0) increased = true
        else decreased = true
        if (abs(diff) !in 1..3 || increased == decreased) {
            return false
        }
    }
    return true
}

fun safeWithRemoval(numbers: List<Int>): Boolean {
    for (i in numbers.indices) {
        val list = numbers.toMutableList()
        list.removeAt(i)
        if (checkList(list)) return true
    }
    return false
}