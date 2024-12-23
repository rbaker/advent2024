import kotlin.math.floor

const val mod = 16777216

fun main() {
    val lines = Any::class::class.java.getResource("/day22.txt")?.readText()?.lines()!!
    var secretAfter2000 = 0L
    val bananaMap = mutableMapOf<List<Int>, Int>()
    lines.forEach { line ->
        var secret = line.toInt()
        val differences = mutableListOf<Int>()
        val localBananaMap = mutableMapOf<List<Int>, Int>()
        var lastDigit = secret.toString().last().toString().toInt()
        for (j in 0..2000) {
            secret = digest(secret)
            val nextLastDigit = secret.toString().last().toString().toInt()
            differences.add((nextLastDigit - lastDigit))
            if (j > 3) {
                val window = differences.subList(j - 4, j)
                localBananaMap.computeIfAbsent(window.toList()) { lastDigit }
            }
            lastDigit = nextLastDigit
            if (j == 1999) secretAfter2000 += secret
        }
        localBananaMap.forEach {
            bananaMap.merge(it.key, it.value) { a, b -> a + b }
        }
    }
    println(secretAfter2000)
    println(bananaMap.values.max())
}

fun digest(secret: Int): Int {
    var res = ((secret * 64) xor secret).mod(mod)
    res = (floor(res / 32.0).toInt() xor res).mod(mod)
    res = ((res * 2048) xor res).mod(mod)
    return res
}