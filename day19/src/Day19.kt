import java.io.File
import kotlin.math.abs

data class Point(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point): Point =
        Point(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Point): Point =
        Point(x - other.x, y - other.y, z - other.z)

    fun manhattan(other: Point): Int {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }
}
data class Scanner(
    val beacon: Set<Point>,
    var solved: Boolean = false,
    var offset: Point = Point(0, 0, 0)
)

data class Result(
    val beacons: Set<Point>,
    val offset: Point
)

class Day19(path: String) {
    private val scanner = mutableListOf<Scanner>()
    private val finalBeacons = mutableSetOf<Point>()
    private val overlapCount = 12

    init {
        var beacons: MutableSet<Point> = mutableSetOf()
        File(path).forEachLine { line ->
            if (line.contains("---")) {
                beacons = mutableSetOf()
            } else if (line.isNotEmpty()) {
                val coord = line.split(",")
                val point = Point(coord[0].toInt(), coord[1].toInt(), coord[2].toInt())
                beacons.add(point)
            } else {
                scanner.add(Scanner(beacons))
            }
        }
        scanner.add(Scanner(beacons))
    }

    private fun rotate(p: Point, combination: Int): Point {
        // From https://www.euclideanspace.com/maths/algebra/matrix/transforms/examples/index.htm
        return when (combination) {
            0 -> Point(p.x, p.y, p.z)
            1 -> Point(p.x, p.z, -p.y)
            2 -> Point(p.x, -p.y, -p.z)
            3 -> Point(p.x, -p.z, p.y)

            4 -> Point(-p.y, p.x, p.z)
            5 -> Point(p.z, p.x, p.y)
            6 -> Point(p.y, p.x, -p.z)
            7 -> Point(-p.z, p.x, -p.y)

            8 -> Point(-p.x, -p.y, p.z)
            9 -> Point(-p.x, -p.z, -p.y)
            10 -> Point(-p.x, p.y, -p.z)
            11 -> Point(-p.x, p.z, p.y)

            12 -> Point(p.y, -p.x, p.z)
            13 -> Point(p.z, -p.x, -p.y)
            14 -> Point(-p.y, -p.x, -p.z)
            15 -> Point(-p.z, -p.x, p.y)

            16 -> Point(-p.z, p.y, p.x)
            17 -> Point(p.y, p.z, p.x)
            18 -> Point(p.z, -p.y, p.x)
            19 -> Point(-p.y, -p.z, p.x)

            20 -> Point(-p.z, -p.y, -p.x)
            21 -> Point(-p.y, p.z, -p.x)
            22 -> Point(p.z, p.y, -p.x)
            23 -> Point(p.y, -p.z, -p.x)

            else -> Point(0, 0, 0)
        }
    }

    private fun compareBeacons(aSet: Set<Point>, bSet: Set<Point>): Result? {
        (0..23).forEach { combination ->
            val bSetRotated = bSet.map { rotate(it, combination) }.toSet()

            aSet.forEach { a ->
                bSetRotated.forEach { b ->
                    val offset = a.minus(b)
                    val bSetRotatedTranslated = bSetRotated.map { it.plus(offset) }.toSet()

                    val intersect = aSet.intersect(bSetRotatedTranslated)
                    if (intersect.size >= overlapCount) {
                        return Result(bSetRotatedTranslated, offset)
                    }
                }
            }
        }

        return null
    }

    fun part1(): Int {
        scanner[0].solved = true
        finalBeacons.addAll(scanner[0].beacon)

        while (scanner.count { it.solved } < scanner.size) {
            for (i in 0 until scanner.size) {
                // Don't anchor on unsolved scanner
                if (!scanner[i].solved) {
                    continue
                }
                for (j in 0 until scanner.size) {
                    // Don't compare against self, don't compare with already solved scanner
                    if ((i == j) || scanner[j].solved) {
                        continue
                    }
                    val result = compareBeacons(scanner[i].beacon, scanner[j].beacon)
                    if (result != null) {
                        println("scanners $i and $j overlap")
                        finalBeacons.addAll(result.beacons)
                        scanner[j] = Scanner(result.beacons, true, result.offset)
                    }
                }
            }
        }

        return finalBeacons.size
    }

    fun part2(): Int {
        var max = 0

        for (a in scanner) {
            for (b in scanner) {
                if (a.offset == b.offset) {
                    continue
                }

                val distance = a.offset.manhattan(b.offset)
                if (distance > max) {
                    max = distance
                }
            }
        }

        return max
    }
}

fun main() {
    val aoc = Day19("day19/input.txt")

    println(aoc.part1())
    println(aoc.part2())
}
