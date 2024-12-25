fun main() {
    val locks = mutableListOf<IntArray>()
    val keys = mutableListOf<IntArray>()
    val parts = Any::class::class.java.getResource("/day25.txt")?.readText()?.split("\r\n\r\n")!!
    parts.forEach {
        val lines = it.split("\r\n")
        val solid = IntArray(5); val blank = IntArray(5)
        for (i in 1..5) {
            lines[i].toCharArray().forEachIndexed { j, c ->
                if (c == '#') solid[j]++
                else blank[j]++
            }
        }
        if (lines[0] == ".....") keys.add(solid)
        else locks.add(blank)
    }
    println(day25part1(locks, keys))
}

fun day25part1(locks: List<IntArray>, keys: List<IntArray>): Int {
    var ret = 0
    locks.forEach {
        keys.forEach keyLoop@ { key ->
            for (i in 0..4) {
                if (key[i] > it[i]) return@keyLoop
            }
            ret++
        }
    }
    return ret
}