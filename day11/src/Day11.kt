import java.io.File

class Day11(private val path: String) {
    private var map = mutableListOf<Int>()
    private var flashMap: BooleanArray
    private var height = 0
    private var width = 0

    init {
        var i = 0
        var j = 0
        File(path).forEachLine { line ->
            j = line.length
            line.forEach {
                map.add(Character.getNumericValue(it))
            }
            i++
        }
        height = i
        width = j

        flashMap = BooleanArray(map.size) { false }
    }

    private fun getMapAt(m: List<Int>, x: Int, y: Int): Int {
        return m[y * width + x]
    }

    private fun setMapAt(m: MutableList<Int>, x: Int, y: Int, value: Int) {
        m[y * width + x] = value
    }

    private fun increaseAdjacent(m: MutableList<Int>, f: BooleanArray) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (f[y * width + x]) {
                    for (i in -1..1) {
                        for (j in -1..1) {
                            if (i != 0 || j != 0) {
                                if ((x + j in 0 until width) && (y + i in 0 until height)) {
                                    setMapAt(m, x + j, y + i, getMapAt(m, x + j, y + i) + 1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun flash(m: MutableList<Int>, f: BooleanArray) {
        for (i in 0 until height) {
            for (j in 0 until width) {
                var energy = getMapAt(m, j, i)
                if (energy > 9 && !flashMap[i * width + j]) {
                    f[i * width + j] = true
                    flashMap[i * width + j] = true
                    //increaseAdjacent(m, f)
                }
            }
        }
    }

    private fun printMap(m: MutableList<Int>) {
        for (i in 0 until height) {
            for (j in 0 until width) {
                print(getMapAt(m, j, i))
                print(" ")
            }
            println()
        }
        println()
    }

    private fun step(): Int {
        var totalFlashes = 0

        // 1. Increase energy
        val newMap = map.map { it + 1 }.toMutableList()

        //printMap(newMap)

        // 2. Flash
        do {
            val flashed = BooleanArray(map.size) { false }
            flash(newMap, flashed)
            val flashes = flashed.count { it }
            if (flashes > 0) {
                increaseAdjacent(newMap, flashed)
            }
            totalFlashes += flashes
        } while (flashes > 0)

        //printMap(newMap)

        // 3. Reset
        newMap.forEachIndexed { index, _ ->
            flashMap[index] = false
            if (newMap[index] > 9) {
                newMap[index] = 0
            }
        }

        map = newMap

        return totalFlashes
    }

    fun part1(): Int {
        var totalFlashes = 0
        for (s in 1..100) {
            totalFlashes += step()
            printMap(map)
        }

        return totalFlashes
    }

    fun part2(): Int {
        var step = 0
        do {
            step()
            step++
            val sum = map.sum()
        } while (sum != 0)

        return step
    }
}

fun main() {
    val aoc = Day11("day11/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
