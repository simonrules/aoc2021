import java.io.File

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

    fun getXTranslatedList(scanner: Scanner): List<Int> {
        val list = scanner.beacon.map { it.x }.sorted()
        val min = list.first()
        return list.map { it - min }
    }

    fun getXFlippedTranslatedList(scanner: Scanner): List<Int> {
        val list = scanner.beacon.map { -it.x }.sorted()
        val min = list.first()
        return list.map { it - min }
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

    fun difference(sorted: List<Int>): List<Int> {
        val diff = mutableListOf<Int>()

        for (i in 1 until sorted.size) {
            diff.add(sorted[i] - sorted[i - 1])
        }

        return diff
    }

    fun compare(a: Scanner, b: Scanner) {
        val listA = a.beacon.map { it.x }.sorted()
        val diffA = difference(listA)
        val listB = b.beacon.map { -it.x }.sorted()
        val diffB = difference(listB)
        println(diffA.intersect(diffB))
        /*val translated = getXTranslatedList(a)

        if (translated.intersect(getXTranslatedList(b)).size >= 12) {
            println("x")
        } else if (translated.intersect(getXFlippedTranslatedList(b)).size >= 12) {
            println("x flipped")
        } else if (translated.intersect(getYTranslatedList(b)).size >= 12) {
            println("y")
        } else if (translated.intersect(getYFlippedTranslatedList(b)).size >= 12) {
            println("y flipped")
        } else if (translated.intersect(getZTranslatedList(b)).size >= 12) {
            println("z")
        } else if (translated.intersect(getZFlippedTranslatedList(b)).size >= 12) {
            println("z flipped")
        }*/
    }

    fun part1(): Int {
        compare(scanner[0], scanner[1])
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
