package y2025

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver

fun main() {
    val targetRegex = "\\[(.*)] (\\(.*\\)) \\{(.*)}".toRegex()
    var part1 = 0; var part2 = 0L
    Loader.loadNativeLibraries()
    val solver = MPSolver.createSolver("SCIP")
    Any::class::class.java.getResourceAsStream("/y2025/day10.txt")?.bufferedReader()?.forEachLine { line ->
        val (target, buttons, joltage) = targetRegex.find(line)!!.destructured
        val targetList = target.toCharArray().map { it == '#' }
        val buttonList = buttons.split(" ").map { nums -> nums.split(",").map { it.replace("[^A-Za-z0-9 ]".toRegex(), "").toInt() } }
        val joltageList = joltage.split(",").map { it.toInt() }.toIntArray()
        part1 += minButtonPresses(buttonList, targetList)
        part2 += minButtonPressesILP(buttonList, joltageList, solver)
    }
    println(part1)
    println(part2)
}

fun minButtonPresses(buttons: List<List<Int>>, target: List<Boolean>): Int {
    val buttonVectors = buttons.map { BooleanArray(target.size) { i -> i in it } }
    val targetVector = target.toBooleanArray()
    val n = buttons.size
    var minPresses = Int.MAX_VALUE

    for (mask in 1 until (1 shl n)) {
        val result = BooleanArray(target.size)
        var presses = 0
        for (i in 0 until n) {
            if ((mask shr i) and 1 == 1) {
                presses++
                for (j in 0 until target.size) result[j] = result[j] xor buttonVectors[i][j]
            }
        }
        if (result.contentEquals(targetVector)) {
            minPresses = minOf(minPresses, presses)
        }
    }
    return if (minPresses == Int.MAX_VALUE) -1 else minPresses
}

fun minButtonPressesILP(buttons: List<List<Int>>, target: IntArray, solver: MPSolver): Int {
    val n = buttons.size
    val m = target.size

    val x = Array(n) { solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x$it") }

    for (i in 0 until m) {
        val ct = solver.makeConstraint(target[i].toDouble(), target[i].toDouble())
        for (j in 0 until n) if (i in buttons[j]) ct.setCoefficient(x[j], 1.0)
    }

    // Objective: minimize total presses
    val objective = solver.objective()
    for (j in 0 until n) objective.setCoefficient(x[j], 1.0)
    objective.setMinimization()

    val resultStatus = solver.solve()
    return if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
        x.sumOf { it.solutionValue().toInt() }
    } else {
        -1
    }
}