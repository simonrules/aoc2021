import java.io.File

class Day13(path: String) {
    private val size = 2000
    private val map = BooleanArray(size * size)
    private val folds = mutableListOf<Pair<Char, Int>>()
    private var width = 0
    private var height = 0

    init {
        File(path).forEachLine {
            if (it.contains(",")) {
                // dot
                val coord = it.split(",")
                val x = coord[0].toInt()
                val y = coord[1].toInt()

                if (x > width) {
                    width = x
                }
                if (y > height) {
                    height = y
                }

                setMapAt(x, y, true)
            } else if (it.contains("=")) {
                // fold
                val fold = it.split("=")

                folds.add(Pair(fold[0].last(), fold[1].toInt()))
            }
        }

        width++
        height++
    }

    private fun getMapAt(x: Int, y: Int): Boolean {
        return map[y * size + x]
    }

    private fun setMapAt(x: Int, y: Int, value: Boolean) {
        map[y * size + x] = value
    }

    private fun printMap(w: Int, h: Int) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                print(if (getMapAt(j, i)) '#' else '.')
            }
            println()
        }
        println()
    }

    private fun countDots(w: Int, h: Int): Int {
        var count = 0
        for (i in 0 until h) {
            for (j in 0 until w) {
                if (getMapAt(j, i)) {
                    count++
                }
            }
        }
        return count
    }

    private fun doFold(fold: Pair<Char, Int>) {
        val axis = fold.first
        val v = fold.second
        if (axis == 'x') {
            var jj = v - 1
            for (j in v + 1 until width) {
                for (i in 0 until height) {
                    val value = getMapAt(j, i)
                    if (value) {
                        setMapAt(jj, i, true)
                    }
                }
                jj--
            }
            width = v
        } else if (axis == 'y') {
            var ii = v - 1
            for (i in v + 1 until height) {
                for (j in 0 until width) {
                    val value = getMapAt(j, i)
                    if (value) {
                        setMapAt(j, ii, true)
                    }
                }
                ii--
            }
            height = v
        }
    }

    fun part1(): Int {
        val fold = folds[0]

        doFold(fold)

        return countDots(width, height)
    }

    fun part2(): Int {
        folds.forEach { doFold(it) }

        printMap(width, height)

        return 0
    }
}

fun main() {
    val aoc = Day13("day13/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
