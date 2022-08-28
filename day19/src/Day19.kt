import java.io.File
import java.util.SortedSet

data class Point(val x: Int, val y: Int, val z: Int)
data class Scanner(val id: Int, val beacon: MutableList<Point>)

class Day19(path: String) {
    private val scanner = mutableListOf<Scanner>()

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

    private fun getXTranslated(scanner: Scanner): Set<Int> {
        val list = scanner.beacon.map { it.x }.sorted()
        val min = list.first()
        return list.map { it - min }.toSet()
    }

    private fun getXFlippedTranslated(scanner: Scanner): Set<Int> {
        val list = scanner.beacon.map { -it.x }.sorted()
        val min = list.first()
        return list.map { it - min }.toSet()
    }

    fun getYTranslatedList(scanner: Scanner): List<Int> {
        val list = scanner.beacon.map { it.y }.sorted()
        val min = list.first()
        return list.map { it - min }
    }

    fun getYFlippedTranslatedList(scanner: Scanner): List<Int> {
        val list = scanner.beacon.map { -it.y }.sorted()
        val min = list.first()
        return list.map { it - min }
    }

    fun getZTranslatedList(scanner: Scanner): List<Int> {
        val list = scanner.beacon.map { it.z }.sorted()
        val min = list.first()
        return list.map { it - min }
    }

    fun getZFlippedTranslatedList(scanner: Scanner): List<Int> {
        val list = scanner.beacon.map { -it.z }.sorted()
        val min = list.first()
        return list.map { it - min }
    }

    private fun compareSets(a: Set<Int>, b: Set<Int>): Boolean {
        for (offset in -b.maxOrNull()!! until a.maxOrNull()!!) {
            val newSet = b.map { it + offset }
            if (newSet.intersect(a).size == 12) {
                return true
            }
        }

        return false
    }

    fun part1(): Int {
        val a = getXTranslated(scanner[0])
        val b = getXFlippedTranslated(scanner[1])
        compareSets(a, b)
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
