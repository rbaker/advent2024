fun main() {
    val files = mutableListOf<Pair<Int, Int>>()
    var id = 0
    Any::class::class.java.getResource("/day9.txt")?.readText()?.toCharArray()?.forEachIndexed { i, c ->
        if (i % 2 == 0) files.add(Pair(id++, c.toString().toInt()))
        else files.add(Pair(-1, c.toString().toInt()))
    }
    println(day9part1(files))
    println(day9part2(files))
}

fun day9part1(files: List<Pair<Int, Int>>): Long {
    val contents = mutableListOf<Int>()
    files.forEach {
        for (i in 0..<it.second) contents.add(it.first)
    }
    for (i in contents.indices.reversed()) {
        if (contents[i] != -1) {
            contents[contents.indexOf(-1)] = contents[i]
            contents[i] = -1
        }
    }
    contents.removeAll { i -> i == -1 }
    return contents.map { it.toLong() }.reduceIndexed { idx, acc, v -> acc + (idx * v) }
}

fun day9part2(files: MutableList<Pair<Int, Int>>): Long {
    for (i in files.size -1 downTo 0) {
        if (files[i].first == -1) continue
        for (j in 0..i) {
            if (files[j].first == -1 && files[j].second >= files[i].second) {
                files[j] = Pair(-1, files[j].second - files[i].second)
                val p = files[i]
                files[i] = Pair(-1, files[i].second)
                files.add(j, p)
                break
            }
        }
    }
    var total = 0L; var track = 0
    files.forEach {
        for (i in 0..<it.second) {
            if (it.first != -1) total += it.first * track
            track++
        }
    }
    return total
}
