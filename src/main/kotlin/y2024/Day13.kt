package y2024

val buttonRegex =
    "Button A: X\\+(\\d*), Y\\+(\\d*)\\r\\nButton B: X\\+(\\d*), Y\\+(\\d*)\\r\\nPrize: X=(\\d*), Y=(\\d*)".toRegex()

fun main() {
    val locations = Any::class::class.java.getResource("/y2024/day13.txt")?.readText()?.split("\r\n\r\n")!!
    var total = 0L
    var totalExtended = 0L
    locations.forEach {
        val m = buttonRegex.find(it)!!
        val buttonA = Pair(m.groupValues[1].toLong(), m.groupValues[2].toLong())
        val buttonB = Pair(m.groupValues[3].toLong(), m.groupValues[4].toLong())
        val prize = Pair(m.groupValues[5].toLong(), m.groupValues[6].toLong())
        total += solve(buttonA, buttonB, prize)
        totalExtended += solve(buttonA, buttonB, Pair(prize.first + 10_000_000_000_000L, prize.second + 10_000_000_000_000L))
    }
    println(total)
    println(totalExtended)
}

fun solve(buttonA: Pair<Long, Long>, buttonB: Pair<Long, Long>, prize: Pair<Long, Long>): Long {
    val top = prize.first * buttonB.second - prize.second * buttonB.first
    val bottom = buttonB.second * buttonA.first - buttonA.second * buttonB.first
    val a = (top / bottom).takeIf { top % bottom == 0L } ?: return 0
    val b = (prize.first - buttonA.first * a) / buttonB.first
    return (a * 3) + b
}