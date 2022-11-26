import java.io.File
import java.util.SortedSet

data class Point(val x: Int, val y: Int, val z: Int)
data class Scanner(val id: Int, val beacon: MutableList<Point>)

class Day19(path: String) {
    private val scanner = mutableListOf<Scanner>()
    private val overlapCount = 12

    init {
        var currentScanner: Scanner? = null
        File(path).forEachLine { line ->
            if (line.contains("---")) {
                val id = line.substring(12, line.length - 4).toInt()
                currentScanner = Scanner(id, mutableListOf())
                scanner.add(currentScanner!!)
            } else if (line.isNotEmpty()) {
                val coord = line.split(",")
                val point = Point(coord[0].toInt(), coord[1].toInt(), coord[2].toInt())
                currentScanner!!.beacon.add(point)
            }
        }
    }

    private fun isOverlap(a: Set<Int>, b: Set<Int>): Boolean {
        return true
    }

    private fun compareScanners(a: Int, b: Int): Boolean {

        return true
    }

    fun part1(): Int {
        val set0 = scanner[0].beacon.map { it.x }.toSortedSet()
        val set1 = scanner[1].beacon.map { -it.x + 68 }.toSortedSet()
        val intersect = set0.intersect(set1)
        //println(isOverlap(list0, list1))
        //println(compareScanners(0, 1))
        return 0
    }

    fun part2(): Int {
        return 0
    }
}

fun main() {
    val aoc = Day19("day19/test1.txt")

    println(aoc.part1())
    println(aoc.part2())
}
