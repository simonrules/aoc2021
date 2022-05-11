import java.io.File
import java.util.*

data class Point(val x: Int, val y: Int)
data class Vertex(val point: Point, val dist: Int)

class Day15(path: String) {
    private var map = mutableListOf<Int>()
    private var dist = mutableListOf<Int>()
    private var height = 0
    private var width = 0

    // priority queue ordered by the dist (risk) ascending
    private var queue = PriorityQueue { v1: Vertex, v2: Vertex -> v1.dist - v2.dist }
    private var visited = mutableSetOf<Point>()

    init {
        var i = 0
        var j = 0
        File(path).forEachLine { line ->
            j = line.length
            line.forEach {
                map.add(Character.getNumericValue(it))
                dist.add(Int.MAX_VALUE)
            }
            i++
        }
        height = i
        width = j
    }

    private fun getMapAt(p: Point): Int {
        return map[p.y * width + p.x]
    }

    private fun getDistAt(p: Point): Int {
        return dist[p.y * width + p.x]
    }

    private fun setDistAt(p: Point, v: Int) {
        dist[p.y * width + p.x] = v
    }

    private fun addToQueue(u: Point, v: Vertex) {
        if (!visited.contains(u)) {
            val weight = getMapAt(u)
            if (getDistAt(v.point) + weight < getDistAt(u)) {
                // Remove existing vertex, if present
                // bug here: does not work, so can be re-added below :(
                setDistAt(u, getDistAt(v.point) + weight)
            }
            // update the dist, don't just insert a new element
            // we do this by removing and inserting
            queue.removeIf { it.point == u }
            queue.add(Vertex(u, getDistAt(u)))
        }
    }

    private fun dijkstra(source: Point) {
        setDistAt(source, 0) // distance to start is zero
        queue.add(Vertex(source, 0))

        while (queue.size > 0) {
            val v = queue.poll()
            visited.add(v.point)

            // consider four neighbouring nodes and visit all unvisited ones
            // left
            if (v.point.x > 0) {
                addToQueue(Point(v.point.x - 1, v.point.y), v)
            }

            // right
            if (v.point.x < width - 1) {
                addToQueue(Point(v.point.x + 1, v.point.y), v)
            }

            // top
            if (v.point.y > 0) {
                addToQueue(Point(v.point.x, v.point.y - 1), v)
            }

            // bottom
            if (v.point.y < height - 1) {
                addToQueue(Point(v.point.x, v.point.y + 1), v)
            }
        }
    }

    private fun enlargeMap() {
        val newMap = mutableListOf<Int>()
        val newDist = mutableListOf<Int>()

        for (i in 0 until width * 5 * height * 5) {
            newMap.add(0)
            newDist.add(Int.MAX_VALUE)
        }
        val newWidth = width * 5
        val newHeight = height * 5

        // copy to top left
        for (y in 0 until height) {
            for (x in 0 until width) {
                newMap[y * newWidth + x] = getMapAt(Point(x, y))
            }
        }

        // increment
        for (y in 0 until newHeight) {
            for (x in 0 until newWidth) {
                if (x < width && y < height) {
                    continue
                }

                val risk = getMapAt(Point(x % width, y % height))
                val increment = (x / width).toInt() + (y / height).toInt()
                var newVal = risk + increment
                if (newVal > 9) {
                    newVal -= 9
                }
                newMap[y * newWidth + x] = newVal
            }
        }

        width = newWidth
        height = newHeight
        map = newMap
        dist = newDist
    }

    fun part1(): Int {
        dijkstra(Point(0, 0))

        return getDistAt(Point(width - 1, height - 1))
    }

    fun part2(): Int {
        enlargeMap()
        dijkstra(Point(0, 0))

        return getDistAt(Point(width - 1, height - 1))
    }
}

fun main() {
    val aoc = Day15("day15/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
