package y2024
val xmas = "XMAS".toRegex()
val samx = "SAMX".toRegex()

fun main() {
    val lines = Any::class::class.java.getResourceAsStream("/y2024/day4.txt")?.bufferedReader()?.readLines()!!
    println(part1(lines))
    println(part2(lines))
}

fun part1(lines: List<String>): Int {
    val columns = arrayListOf<String>(); val diagonal1 = arrayListOf<String>(); val diagonal2 = arrayListOf<String>()
    lines.forEachIndexed { i, line ->
        var diagCounter = i
        line.toCharArray().forEachIndexed { j, c ->
            if (columns.getOrNull(j) == null) columns.add(c.toString())
            else columns[j] = columns[j] + c
            if (diagonal1.getOrNull(diagCounter) == null) diagonal1.add(c.toString())
            else diagonal1[diagCounter] = diagonal1[diagCounter] + c
            if (diagonal2.getOrNull(diagCounter) == null) diagonal2.add(line[lines.size - j - 1].toString())
            else diagonal2[diagCounter] = diagonal2[diagCounter] + line[lines.size - j - 1]
            diagCounter++
        }
    }
    var count = 0
    lines.forEach { text -> count += instances(text) }
    columns.forEach { text -> count += instances(text) }
    diagonal1.forEach { text -> count += instances(text) }
    diagonal2.forEach { text -> count += instances(text) }
    return count
}

fun instances(line: String): Int = xmas.findAll(line).count() + samx.findAll(line).count()

fun part2(lines: List<String>): Int {
    var count = 0
    for (i in 0..lines.size - 3) {
        for (j in 0..lines[i].length - 3) {
            val text = "" + lines[i][j] + lines[i][j+2] + lines[i+1][j+1] + lines[i+2][j] + lines[i+2][j+2]
            if (text == "MSAMS" || text == "SSAMM" || text== "SMASM" || text == "MMASS") count++
        }
    }
    return count
}