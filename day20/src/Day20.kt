import java.io.File

class Day20(filename: String) {
    private val algorithm: List<Boolean>
    private var image = ArrayMap()

    init {
        var firstLine = true
        var row = 0
        var algorithmString = ""
        File(filename).forEachLine { line ->
            if (line.isBlank()) {
                firstLine = false
            } else {
                if (firstLine) {
                    algorithmString = line
                } else {
                    line.forEachIndexed { col, c ->
                        image.setAt(col + image.cx, row + image.cy, c == '#')
                    }
                    row++
                }
            }
        }

        algorithm = algorithmString.map { it == '#' }
    }

    fun part1(): Int {
        image.iterate(algorithm)
        image.iterate(algorithm)

        return image.count()
    }

    fun part2(): Int {
        repeat(50) {
            image.iterate(algorithm)
        }

        return image.count()
    }
}

fun main() {
    val aoc = Day20("day20/input.txt")

    // Can't run part 1 then 2, and can't be bothered to fix it.
    //println(aoc.part1())
    println(aoc.part2())
}
