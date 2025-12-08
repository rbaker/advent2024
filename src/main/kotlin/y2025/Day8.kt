package y2025

import kotlin.math.sqrt

const val LOOPS = 1000

fun main() {
    val junctions = mutableListOf<Point3D>()
    val distances = mutableListOf<Triple<Point3D, Point3D, Double>>()
    Any::class::class.java.getResourceAsStream("/y2025/day8.txt")?.bufferedReader()?.forEachLine { line ->
        val coords = line.split(",").map { it.toDouble() }
        junctions.add(Point3D(coords[0].toInt(), coords[1].toInt(), coords[2].toInt()))
    }
    val circuits = mutableListOf<MutableSet<Point3D>>()
    junctions.forEachIndexed { i, junction ->
        (i + 1..<junctions.size).forEach { j ->
            val otherJunction = junctions[j]
            val distance = junction.distanceTo(otherJunction)
            distances.add(Triple(junction, otherJunction, distance))
        }
        circuits.add(mutableSetOf(junction))
    }
    distances.sortWith(Comparator { i1, i2 -> i1.third.compareTo(i2.third) })
    distances.forEachIndexed { i, (j1, j2, _) ->
        if (i == LOOPS) {
            println(circuits.map { it.size }.sortedDescending().take(3).reduce { a,b -> a * b}) // part 1 solution
        }
        val set1 = circuits.find { it.contains(j1) }
        val set2 = circuits.find { it.contains(j2) }
        when {
            set1 != null && set2 == null -> set1.add(j2)
            set1 == null && set2 != null -> set2.add(j1)
            set1 != set2 -> {
                set1?.addAll(set2!!)
                circuits.remove(set2)
            }
        }
        if (circuits.size == 1) {
            println(j1.x * j2.x) // part 2 solution
            return
        }
    }
}

data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(other: Point3D): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        val dz = (z - other.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }
}