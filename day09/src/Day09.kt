import java.io.File

class Day09(private val path: String) {
    private val map = mutableListOf<Int>()
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
    }

    private fun getMapAt(x: Int, y: Int): Int {
        return map[y * width + x]
    }

    private fun isLowPoint(x: Int, y: Int): Boolean {
        val myDepth = getMapAt(x, y)
        if (x > 0) {
            if (myDepth >= getMapAt(x - 1, y)) {
                return false
            }
        }
        if (x < width - 1) {
            if (myDepth >= getMapAt(x + 1, y)) {
                return false
            }
        }
        if (y > 0) {
            if (myDepth >= getMapAt(x, y - 1)) {
                return false
            }
        }
        if (y < height - 1) {
            if (myDepth >= getMapAt(x, y + 1)) {
                return false
            }
        }
        return true
    }

    private fun findBasinSizeRecurse(x: Int, y: Int, visited: Array<Boolean>): Int {
        val myDepth = getMapAt(x, y)

        if (myDepth == 9 || visited[y * width + x]) {
            return 0
        }

        visited[y * width + x] = true

        var total = 1
        if (x > 0) {
            val theirDepth = getMapAt(x - 1, y)
            if (myDepth < theirDepth) {
                total += findBasinSizeRecurse(x - 1, y, visited)
            }
        }
        if (x < width - 1) {
            val theirDepth = getMapAt(x + 1, y)
            if (myDepth < theirDepth) {
                total += findBasinSizeRecurse(x + 1, y, visited)
            }
        }
        if (y > 0) {
            val theirDepth = getMapAt(x, y - 1)
            if (myDepth < theirDepth) {
                total += findBasinSizeRecurse(x, y - 1, visited)
            }
        }
        if (y < height - 1) {
            val theirDepth = getMapAt(x, y + 1)
            if (myDepth < theirDepth) {
                total += findBasinSizeRecurse(x, y + 1, visited)
            }
        }

        return total
    }

    fun part1(): Int {
        var risk = 0
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (isLowPoint(j, i)) {
                    risk += getMapAt(j, i) + 1
                }
            }
        }
        return risk
    }

    fun part2(): Int {
        val size = mutableListOf<Int>()

        for (i in 0 until height) {
            for (j in 0 until width) {
                if (isLowPoint(j, i)) {
                    val visited = Array(map.size) { false }
                    size.add(findBasinSizeRecurse(j, i, visited))
                }
            }
        }
        size.sortDescending()
        return size[0] * size[1] * size[2]
    }
}

fun main(args: Array<String>) {
    val aoc = Day09("day09/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
