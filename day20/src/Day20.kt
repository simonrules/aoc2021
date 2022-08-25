import java.io.File

class Day20(filename: String) {
    private val algorithm: String
    private var image = mutableMapOf<Pair<Int, Int>, Boolean>()
    private var w = 0
    private var h = 0
    private var left = 0
    private var top = 0
    private var cx = 0
    private var cy = 0

    init {
        var firstBlock = true
        val lines = StringBuilder()
        var row = 0
        File(filename).forEachLine { line ->
            if (line.isBlank()) {
                firstBlock = false
            } else {
                if (firstBlock) {
                    lines.append(line)
                } else {
                    w = line.length
                    line.forEachIndexed { col, c ->
                        image[Pair(col, row)] = c == '#'
                    }
                    row++
                }
                h = row
            }
        }
        algorithm = lines.toString()

        // Centre point of image
        cx = w / 2
        cy = h / 2
    }

    private fun iteration() {
        // Expand image
        left--
        top--
        w += 2
        h += 2
        val right = left + w
        val bottom = top + h

        for (i in top until bottom) {
            for (j in left until right) {
                val pixel = image[Pair(j, i)] ?: false
                println(pixel)
            }
        }
    }

    fun part1() {
        iteration()
    }
}

fun main() {
    val aoc = Day20("day20/test1.txt")
    aoc.part1()
}
