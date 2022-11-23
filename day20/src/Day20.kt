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
        return image.iterate(algorithm)
    }
}

fun main() {
    val aoc = Day20("day20/test1.txt")
    // 5278 is too high
    // 5073 is too low
    println(aoc.part1())
}
