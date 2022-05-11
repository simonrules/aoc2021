import java.io.File

class Day05(path: String) {
    data class Line(val x1: Int, val y1: Int, val x2: Int, val y2:Int)

    private val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    private val lines = mutableListOf<Line>()
    private val size = 1000
    private val map = IntArray(size * size)

    init {
        File(path).forEachLine { line ->
            var matchResult = regex.find(line)
            if (matchResult != null) {
                val (a, b, c, d) = matchResult.destructured
                val line = Line(a.toInt(), b.toInt(), c.toInt(), d.toInt())

                lines.add(line)
            }
        }
    }

    private fun getMapAt(x: Int, y: Int): Int {
        return map[y * size + x]
    }

    private fun setMapAt(x: Int, y: Int, value: Int) {
        map[y * size + x] = value
    }

    private fun plotLine(line: Line, diagonals: Boolean) {
        if (line.x1 == line.x2) {
            if (line.y1 < line.y2) {
                for (y in line.y1..line.y2) {
                    setMapAt(line.x1, y, getMapAt(line.x1, y) + 1)
                }
            } else {
                for (y in line.y2..line.y1) {
                    setMapAt(line.x1, y, getMapAt(line.x1, y) + 1)
                }
            }
        } else if (line.y1 == line.y2) {
            if (line.x1 < line.x2) {
                for (x in line.x1..line.x2) {
                    setMapAt(x, line.y1, getMapAt(x, line.y1) + 1)
                }
            } else {
                for (x in line.x2..line.x1) {
                    setMapAt(x, line.y1, getMapAt(x, line.y1) + 1)
                }
            }
        } else if (diagonals) {
            // diagonal
            if (line.x1 < line.x2) {
                if (line.y1 < line.y2) {
                    var y = line.y1
                    for (x in line.x1..line.x2) {
                        setMapAt(x, y, getMapAt(x, y) + 1)
                        y++
                    }
                } else {
                    var y = line.y1
                    for (x in line.x1..line.x2) {
                        setMapAt(x, y, getMapAt(x, y) + 1)
                        y--
                    }
                }
            } else {
                if (line.y1 < line.y2) {
                    var y = line.y2
                    for (x in line.x2..line.x1) {
                        setMapAt(x, y, getMapAt(x, y) + 1)
                        y--
                    }
                } else {
                    var y = line.y2
                    for (x in line.x2..line.x1) {
                        setMapAt(x, y, getMapAt(x, y) + 1)
                        y++
                    }
                }
            }
        }
    }

    fun part1(): Int {
        lines.forEach { l ->
            plotLine(l, diagonals = false)
        }

        return map.count { it > 1 }
    }


    fun part2(): Int {
        lines.forEach { l ->
            plotLine(l, diagonals = true)
        }

        return map.count { it > 1 }
    }
}

fun main(args: Array<String>) {
    val aoc = Day05("day05/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
