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

    fun getSortedXList(points: List<Point>): List<Int> {
        return points.map { it.x }.sorted()
    }

    fun getSortedYList(points: List<Point>): List<Int> {
        return points.map { it.y }.sorted()
    }

    fun getSortedZList(points: List<Point>): List<Int> {
        return points.map { it.z }.sorted()
    }

    fun compare(a: Scanner, b: Scanner) {
        val az = a.beacon.map { it.z }.sorted()
        val amin = az.first()
        val azTranslated = az.map { it - amin }

        val bz = b.beacon.map { -it.z }.sorted()
        val bmin = bz.first()
        val bzTranslated = bz.map { it - bmin }

        azTranslated.forEachIndexed { index, az ->
            print(az)
            print(" ")
            println(bzTranslated[index])
        }
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
