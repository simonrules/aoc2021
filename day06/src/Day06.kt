import java.io.File

class Day06(path: String) {
    private val ages = mutableListOf<Int>()
    private val counts = LongArray(9) { 0L }

    init {
        val lines = File(path).readLines()
        val items = lines[0].split(',')
        items.forEach {
            ages.add(it.toInt())
            counts[it.toInt()]++
        }

    }

    private fun doTimer() {
        var newFish = 0
        for (a in ages.indices) {
            ages[a]--
            if (ages[a] == -1) {
                ages[a] = 6
                newFish++
            }
        }

        for (i in 0 until newFish) {
            ages.add(8)
        }
    }

    private fun printList(day: Int) {
        print("After $day day(s): ")
        ages.forEach { print("$it,") }
        println()
    }

    fun part1(): Int {
        for (day in 1..80) {
            doTimer()
            printList(day)
        }

        return ages.size
    }

    private fun doTimer2() {
        val newFish = counts[0]

        for (i in 0 until 8) {
            counts[i] = counts[i + 1]
        }

        counts[6] += newFish
        counts[8] = newFish
    }

    fun part2(): Long {

        for (day in 1..256) {
            doTimer2()
        }

        return counts.sum()
    }
}

fun main(args: Array<String>) {
    val aoc = Day06("day06/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
