package y2024

val regex = "([a-z0-9]{3}) (XOR|OR|AND) ([a-z0-9]{3}) -> ([a-z0-9]{3})".toRegex()

fun main() {
    val wires = mutableMapOf<String, Boolean>()
    val gates = mutableListOf<Gate>()
    val parts = Any::class::class.java.getResource("/y2024/day24.txt")?.readText()?.split("\r\n\r\n")!!
    parts[0].split("\r\n").forEach {
        val wire = it.split(": ")
        wires[wire[0]] = wire[1] == "1"
    }
    parts[1].split("\r\n").forEach { line ->
        val (input1, operation, input2, output) = regex.find(line)!!.destructured
        gates.add(Gate(input1, operation, input2, output))
    }
    println(getSum(gates, wires))
}

private fun getSum(gates: List<Gate>, wires: MutableMap<String, Boolean>): Long {
    val gm = gates.toMutableList()
    while (gm.isNotEmpty()) {
        gm.filter { wires.contains(it.input1) && wires.contains(it.input2) }.forEach { gate ->
            val input1 = wires[gate.input1]!!
            val input2 = wires[gate.input2]!!
            val result = when (gate.operation) {
                "AND" -> input1 && input2
                "OR" -> input1 || input2
                "XOR" -> input1 xor input2
                else -> throw IllegalArgumentException("Unknown operation")
            }
            wires[gate.output] = result
            gm.remove(gate)
        }
    }
    var resultString = ""
    wires.filter { it.key.startsWith("z") }.toSortedMap().forEach { resultString += (if (it.value) "1" else "0") }
    return resultString.reversed().toLong(2)
}

data class Gate(val input1: String, val operation: String, val input2: String, val output: String)