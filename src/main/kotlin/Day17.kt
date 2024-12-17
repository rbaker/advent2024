import kotlin.math.pow

val instructions = mutableListOf<Long>()

fun main() {
    val file = Any::class::class.java.getResource("/day17.txt")?.readText()!!
    val regex = "Register A: (\\d+)\\r\\nRegister B: (\\d+)\\r\\nRegister C: (\\d+)\\r\\n\\r\\nProgram: (.*)".toRegex().find(file)!!
    var register = arrayOf(regex.groupValues[1].toLong(), regex.groupValues[2].toLong(), regex.groupValues[3].toLong())
    instructions.addAll(regex.groupValues[4].split(",").map { it.toLong() })
    var outputList = getOutput(register)
    println(outputList.joinToString(","))

    /*var i = 0L
    do {
        i ++
        register = arrayOf(i, 0, 0)
        outputList = getOutput(register)
        var a = outputList.reversed().joinToString("")
        println("$i,$a")
    } while (outputList != instructions)
    println(i)*/
}

fun getOutput(register: Array<Long>): List<Int> {
    val outputList = mutableListOf<Int>()
    var pointer = 0
    while (pointer in 0..<instructions.size) {
        val inst = instructions[pointer]
        val operand = instructions[pointer + 1]
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
        //println("$inst + $operand --- ${register.joinToString(" ")}")
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