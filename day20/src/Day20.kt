import java.io.File

class Day20(filename: String) {
    private val algorithm: List<Boolean>
    private var w = 0
    private var h = 0
    private var left = 0
    private var top = 0

    private val imageW = 1000
    private val imageH = 1000
    private var image = Array(imageW * imageH) { false }
    private val cx = imageW / 2
    private val cy = imageH / 2

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
                        setMapAt(image, col, row, c == '#')
                    }
                    row++
                }
                h = row
            }
        }
        algorithm = lines.map { it == '#' }
    }

    private fun getMapAt(map: Array<Boolean>, x: Int, y: Int): Boolean {
        val xOffset = x + cx
        val yOffset = y + cy
        return map[yOffset * imageW + xOffset]
    }

    private fun setMapAt(map: Array<Boolean>, x: Int, y: Int, value: Boolean) {
        val xOffset = x + cx
        val yOffset = y + cy
        map[yOffset * imageW + xOffset] = value
    }

    private fun iteration() {
        // Expand image
        left--
        top--
        w += 2
        h += 2
        val right = left + w
        val bottom = top + h

        // Use newImage so we don't modify as we go
        val newImage = Array(imageW * imageH) { false }

        for (i in top until bottom) {
            for (j in left until right) {
                val value = getSquareValue(j, i)
                setMapAt(newImage, j, i, algorithm[value])
            }
        }

        image = newImage
    }

    private fun getSquareValue(x: Int, y: Int): Int {
        var value = 0
        for (i in x - 1 .. x + 1) {
            for (j in y - 1 .. y + 1) {
                val pixel = getMapAt(image, j, i)
                value *= 2
                value += if (pixel) 1 else 0
            }
        }
        return value
    }

    private fun printImage() {
        val right = left + w
        val bottom = top + h

        for (i in top until bottom) {
            for (j in left until right) {
                print(if (getMapAt(image, j, i)) '#' else '.')
            }
            println()
        }
    }

    fun part1() {
        //iteration()
        //printImage()
        println(getSquareValue(0, -1))
    }
}

fun main() {
    val aoc = Day20("day20/test1.txt")
    aoc.part1()
}
