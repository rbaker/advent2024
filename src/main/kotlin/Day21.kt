val keypad = mapOf(
    '7' to Point(0, 0), '8' to Point(0, 1), '9' to Point(0, 2),
    '4' to Point(1, 0), '5' to Point(1, 1), '6' to Point(1, 2),
    '1' to Point(2, 0), '2' to Point(2, 1), '3' to Point(2, 2),
                              '0' to Point(3, 1), 'A' to Point(3, 2)
)
val dpad = mapOf(
                              '^' to Point(0, 1), 'A' to Point(0, 2),
    '<' to Point(1, 0), 'v' to Point(1, 1), '>' to Point(1, 2)
)
fun main() {
    var complexity = 0
    Any::class::class.java.getResource("/day21.txt")?.readText()?.lines()?.forEach {code ->
        val final = finalSequence(code)
        val robot1 = robotSequence(final)
        val robot2 = robotSequence(robot1)
        println(final)
        println(robot1)
        println("$robot2  ----- ${robot2.length} - ${code.substring(0,3).toInt()}")
        complexity += robot2.length * code.substring(0,3).toInt()
    }
    println(complexity)
}

fun finalSequence(code: String): String {
    var result = ""
    var current = 'A'
    code.toCharArray().forEach {next ->
        val vertical = keypad[next]!!.x - keypad[current]!!.x
        val horizont = keypad[next]!!.y - keypad[current]!!.y
        if (horizont >= 0) for (i in 0..<horizont) result = "$result>"
        if (vertical < 0) for (i in vertical..<0) result += "^"
        if (horizont < 0) for (i in horizont..<0) result = "$result<"
        if (vertical >= 0) for (i in 0..<vertical) result += "v"
        result += "A"
        current = next
    }
    return result
}

fun robotSequence(code: String): String {
    var result = ""
    var current = 'A'
    code.toCharArray().forEach {next ->
        val vertical = dpad[next]!!.x - dpad[current]!!.x
        val horizont = dpad[next]!!.y - dpad[current]!!.y
        if (vertical >= 0) for (i in 0..<vertical) result += "v"
        if (horizont >= 0) for (i in 0..<horizont) result = "$result>"
        if (vertical < 0) for (i in vertical..<0) result += "^"
        if (horizont < 0) for (i in horizont..<0) result = "$result<"
        result += "A"
        current = next
    }
    return result
}
