package y2024

import kotlin.math.pow

val instructions = mutableListOf<Int>()

fun main() {
    val file = Any::class::class.java.getResource("/y2024/day17.txt")?.readText()!!
    val regex = "Register A: (\\d+)\\r\\nRegister B: (\\d+)\\r\\nRegister C: (\\d+)\\r\\n\\r\\nProgram: (.*)".toRegex().find(file)!!
    var register = arrayOf(regex.groupValues[1].toLong(), regex.groupValues[2].toLong(), regex.groupValues[3].toLong())
    instructions.addAll(regex.groupValues[4].split(",").map { it.toInt() })
    var outputList = getOutput(register)
    println(outputList.joinToString(","))
    /*var i = 109019475735322
    do {
        i += 1
        register = arrayOf(i, 0, 0)
        outputList = y2024.getOutput(register)
        val a = outputList.joinToString(",")
    } while (outputList != y2024.instructions && i < 109019485735322)
    println(i)*/
    register = arrayOf(109019476330651, 0, 0)
    println(getOutput(register).joinToString(","))
}

fun reverseEngineer(target: MutableList<Int>): Long {
    val register = arrayOf(0L, target.last().toLong(), 0L)
    var pointer = instructions.size - 2
    while (register[1] != 0L || register[2] != 0L || pointer != 0) {
        val inst = instructions[pointer].toLong()
        val operand = instructions[pointer + 1].toLong()
        println("$pointer ---- $inst - $operand --- ${register.joinToString(" ")}")
        when (inst) {
            0L -> register[0] *= 1L shl getCombo(operand, register).toInt()
            1L -> register[1] = register[1] xor operand
            2L -> {
                // Reconstruct y2024.mod
                val modValue = register[1]
                register[1] = modValue + 8 * (getCombo(operand, register) / 8)
            }
            4L -> register[1] = register[1] xor register[2]
            6L -> register[1] *= 1L shl getCombo(operand, register).toInt()
            7L -> register[2] *= 1L shl getCombo(operand, register).toInt()
        }
        pointer -= 2
        if (pointer == -2 && (register[1] != 0L || register[2] != 0L)) pointer = instructions.size - 2
    }
    return register[0]
}

fun getOutput(register: Array<Long>): List<Int> {
    val outputList = mutableListOf<Int>()
    var pointer = 0
    while (pointer in instructions.indices) {
        val inst = instructions[pointer].toLong()
        val operand = instructions[pointer + 1].toLong()
        println("$pointer ---- $inst - $operand --- ${register.joinToString(" ")}")
        when (inst) {
            0L -> register[0] /= 2.0.pow(getCombo(operand, register).toInt()).toLong()
            1L -> register[1] = register[1] xor operand
            2L -> register[1] = getCombo(operand, register) % 8
            3L -> {
                if (register[0] != 0L) {
                    pointer = operand.toInt()
                    continue
                }
            }
            4L -> register[1] = register[1] xor register[2]
            5L -> outputList.add((getCombo(operand, register) % 8).toInt())
            6L -> register[1] = register[0] / 2.0.pow(getCombo(operand, register).toInt()).toInt()
            7L -> register[2] = register[0] / 2.0.pow(getCombo(operand, register).toInt()).toInt()
        }
        pointer += 2
    }
    return outputList
}

fun getCombo(i: Long, register: Array<Long>): Long {
    if (i in 0..3) {
        return i
    }
    return when (i) {
        4L -> register[0]
        5L -> register[1]
        6L -> register[2]
        else -> 0
    }
}