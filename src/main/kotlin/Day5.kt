
fun main() {
    val text = Any::class::class.java.getResource("/day5.txt")?.readText()
    val parts = text!!.split("\r\n\r\n")
    val largerMap = hashMapOf<String, MutableList<String>>() ; val smallerMap = hashMapOf<String, MutableList<String>>()
    parts[0].split("\r\n").forEach {
        val split = it.split("|")
        largerMap.computeIfAbsent(split[0]) { mutableListOf() }.add(split[1])
        smallerMap.computeIfAbsent(split[1]) { mutableListOf() }.add(split[0])
    }
    var count = 0
    val failList = mutableListOf<List<String>>()
    parts[1].split("\r\n").forEach {
        val nums = it.split(",")
        for (i in nums.indices) {
            if (nums.subList(0, i).any { num -> largerMap.getOrDefault(nums[i], emptyList()).contains(num) } ||
                nums.subList(i + 1, nums.size).any { num -> smallerMap.getOrDefault(nums[i], emptyList()).contains(num) }) {
                failList.add(nums)
                return@forEach
            }
        }
        count += nums[nums.size / 2].toInt()
    }
    println(count)
}