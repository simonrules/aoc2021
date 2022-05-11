import java.io.File

class Day01(path: String) {
    private val depth = mutableListOf<Long>()
    init {
        File(path).forEachLine {
            depth.add(it.toLong())
        }
    }

    fun part1(): Int {
        var increased = 0
        var currentDepth = depth[0]
        for (d in 1 until depth.size) {
            if (depth[d] > currentDepth) {
                increased++
            }
            currentDepth = depth[d]
        }

        return increased
    }

    fun depthSum(start: Int): Long {
        return depth[start] + depth[start + 1] + depth[start + 2]
    }

    fun part2(): Int {
        var increased = 0
        var currentSum = depthSum(0)

        for (d in 1 until depth.size - 2) {
            if (depthSum(d) > currentSum) {
                increased++
            }
            currentSum = depthSum(d)
        }

        return increased
    }
}

fun main(args: Array<String>) {
    val aoc = Day01("day01/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
