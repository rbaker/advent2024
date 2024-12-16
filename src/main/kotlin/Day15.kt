import java.awt.Point

val movements = mapOf(
    '<' to Pair(0, -1),
    '>' to Pair(0, 1),
    '^' to Pair(-1, 0),
    'v' to Pair(1, 0)
)
fun main() {
    val parts = Any::class::class.java.getResource("/day15.txt")?.readText()?.split("\r\n\r\n")!!
    val lines = parts[0].split("\r\n").map { it.toCharArray() }

    val finalMap = processMap1(lines, parts[1].toCharArray())
    var res = 0
    for (x in finalMap.indices) {
        for (y in finalMap[x].indices) {
            if (finalMap[x][y] == 'O') {
                res += 100 * x + y
            }
        }
    }
    println(res)
}

fun processMap1(lines: List<CharArray>, directions: CharArray): List<CharArray> {
    var robot = Point(0,0)
    for (x in lines.indices) {
        for (y in lines[x].indices) {
            if (lines[x][y] == '@') {
                robot.x = x
                robot.y = y
                break
            }
        }
    }
    directions.forEach {direction ->
        val (dx, dy) = movements[direction] ?: return@forEach
        val newRobot = Point(robot.x + dx, robot.y + dy)
        if (newRobot.x !in 0..lines.size || newRobot.y !in 0..lines[0].size || lines[newRobot.x][newRobot.y] == '#') return@forEach
        if (lines[newRobot.x][newRobot.y] == 'O') {
            var box = newRobot

            while (lines[box.x][box.y] == 'O') {
                box = Point(box.x + dx, box.y + dy)
            }
            if (box.x !in 0..lines.size || box.y !in 0..lines[0].size || lines[box.x][box.y] == '#') return@forEach
            else lines[box.x][box.y] = 'O'
        }

        lines[robot.x][robot.y] = '.'
        lines[newRobot.x][newRobot.y] = '@'
        robot = newRobot
    }
    return lines
}