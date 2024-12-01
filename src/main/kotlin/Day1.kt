import kotlin.collections.HashMap
import kotlin.math.abs

fun main() {
    val fileContent = Any::class::class.java.getResource("/day1.txt")?.readText()?.split("\r\n")
    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()
    val map = hashMapOf<Int, Int>()
    if (fileContent != null) {
        for (line in fileContent) {
            val parts = line.split("   ")
            list1.add(Integer.parseInt(parts[0]))
            list2.add(Integer.parseInt(parts[1]))
            map.merge(Integer.parseInt(parts[1]), 1) { a, b -> Integer.sum(a, b)}
        }
    }

    var part1 = 0
    var part2 = 0
    list1.sort()
    list2.sort()
    for (i in 0..<list1.size) {
        part1 += abs(list1[i] - list2[i])
        if (map[list1[i]] != null) {
            part2 += list1[i] * map[list1[i]]!!
        }
    }

    println(part1)
    println(part2)

}
