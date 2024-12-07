package utils

import java.awt.Point

class Guard(val location: Point, var direction: Char) {
    fun move() {
        val p = nextPosition()
        location.x = p.x
        location.y = p.y
    }

    fun nextPosition(): Point {
        val next = Point(location)
        when (direction) {
            'N' -> next.y--
            'E' -> next.x++
            'S' -> next.y++
            'W' -> next.x--
        }
        return next
    }

    fun turn() {
        when (direction) {
            'N' -> direction = 'E'
            'E' -> direction = 'S'
            'S' -> direction = 'W'
            'W' -> direction = 'N'
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Guard

        if (location != other.location) return false
        if (direction != other.direction) return false

        return true
    }

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + direction.hashCode()
        return result
    }

}