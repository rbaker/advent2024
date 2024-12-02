import kotlin.math.abs

fun main() {
    val list1 = arrayListOf<Int>()
    val list2 = arrayListOf<Int>()
    val map = hashMapOf<Int, Int>().withDefault { 0 }

    Any::class::class.java.getResourceAsStream("/day1.txt")?.bufferedReader()?.forEachLine { line ->
        val parts = line.split("   ")
        list1.add(parts[0].toInt())
        list2.add(parts[1].toInt())
        map.merge(parts[1].toInt(), 1, Int::plus)
    }

    var part1 = 0; var part2 = 0
    list1.sort(); list2.sort()
    for (i in 0..<list1.size) {
        part1 += abs(list1[i] - list2[i])
        part2 += list1[i] * map.getValue(list1[i])
    }

    println(part1)
    println(part2)

}
