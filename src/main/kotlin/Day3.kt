
val mulRegex = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)".toRegex()

fun main() {
    val text = Any::class::class.java.getResource("/day3.txt")?.readText()?.replace("\r\n", "")
    println(part1(text!!))
    println(part2(text))
}

fun part1(text: String): Int = getTotal(text)

fun part2(text: String): Int {
    var result = 0
    val activeSections = "(?:do\\(\\)|^).*?(?:don't\\(\\)|\$)".toRegex()
    activeSections.findAll(text).forEach { section ->
        result += getTotal(section.value)
    }
    return result
}

fun getTotal(text: String): Int {
    var result = 0
    mulRegex.findAll(text).forEach { match ->
        val (a, b) = match.destructured
        result += a.toInt() * b.toInt()
    }
    return result
}