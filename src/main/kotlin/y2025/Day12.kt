package y2025

import kotlin.math.*

data class Shape(val cells: List<Pair<Int, Int>>)

fun parseShapes(): List<Shape> {
    // Define the 6 shapes as lists of (x, y) coordinates
    val rawShapes = listOf(
        listOf("###", "##.", "##."),
        listOf("###", "##.", ".##"),
        listOf(".##", "###", "##."),
        listOf("##.", "###", "##."),
        listOf("###", "#..", "###"),
        listOf("###", ".#.", "###")
    )
    return rawShapes.map { lines ->
        val cells = mutableListOf<Pair<Int, Int>>()
        for ((y, line) in lines.withIndex())
            for ((x, c) in line.withIndex())
                if (c == '#') cells.add(x to y)
        Shape(cells)
    }
}

fun allOrientations(shape: Shape): Set<Shape> {
    // Generate all unique rotations and flips
    val result = mutableSetOf<List<Pair<Int, Int>>>()
    var variants = listOf(shape.cells)
    repeat(4) {
        variants = variants.map { rotate(it) }
        result.addAll(variants.map { normalize(it) })
        result.addAll(variants.map { normalize(flip(it)) })
    }
    return result.map { Shape(it) }.toSet()
}

fun rotate(cells: List<Pair<Int, Int>>): List<Pair<Int, Int>> =
    cells.map { (x, y) -> -y to x }

fun flip(cells: List<Pair<Int, Int>>): List<Pair<Int, Int>> =
    cells.map { (x, y) -> -x to y }

fun normalize(cells: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val minX = cells.minOf { it.first }
    val minY = cells.minOf { it.second }
    return cells.map { (x, y) -> x - minX to y - minY }.sortedWith(compareBy({ it.second }, { it.first }))
}

fun canFit(
    width: Int, height: Int,
    shapes: List<Shape>, counts: List<Int>
): Boolean {
    val grid = Array(height) { BooleanArray(width) }
    val shapeVariants = shapes.map { allOrientations(it) }
    fun place(idx: Int): Boolean {
        if (idx == shapes.size) return true
        if (counts[idx] == 0) return place(idx + 1)
        for (variant in shapeVariants[idx]) {
            val maxX = variant.cells.maxOf { it.first }
            val maxY = variant.cells.maxOf { it.second }
            for (y in 0..height - maxY - 1) {
                for (x in 0..width - maxX - 1) {
                    if (variant.cells.all { (dx, dy) -> !grid[y + dy][x + dx] }) {
                        // Place shape
                        variant.cells.forEach { (dx, dy) -> grid[y + dy][x + dx] = true }
                        val newCounts = counts.toMutableList()
                        newCounts[idx]--
                        if (canFit(width, height, shapes, newCounts)) return true
                        // Backtrack
                        variant.cells.forEach { (dx, dy) -> grid[y + dy][x + dx] = false }
                    }
                }
            }
        }
        return false
    }
    return place(0)
}

// Example usage:
fun main() {
    val shapes = parseShapes()
    val (w, h) = 12 to 5
    val counts = listOf(1,0,1,0,3,2)
    println(canFit(w, h, shapes, counts)) // true
}