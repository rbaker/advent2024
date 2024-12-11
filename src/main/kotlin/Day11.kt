fun main() {
    var numbers = Any::class::class.java.getResource("/day11.txt")?.readText()?.split(" ")
    for (i in 1..25) {
        val nextArray = mutableListOf<String>()
        numbers!!.forEach {
            if (it == "0") nextArray.add("1")
            else if (it.length % 2 == 0) {
                nextArray.add(it.substring(0, it.length / 2))
                nextArray.add((it.substring(it.length / 2, it.length)).toLong().toString())
            } else nextArray.add((it.toLong() * 2024).toString())
        }
        numbers = nextArray
    }
    println(numbers?.size)
}