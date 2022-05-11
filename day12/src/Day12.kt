import java.io.File

class Day12(private val path: String) {
    private val connection = mutableMapOf<String, MutableSet<String>>()
    private val paths = mutableListOf<List<String>>()

    init {
        File(path).forEachLine {
            val parts = it.split("-")
            if (parts[0] != "end" && parts[1] != "start") {
                connection.getOrPut(parts[0]) { mutableSetOf() }.add(parts[1])
            }
            if (parts[0] != "start" && parts[1] != "end") {
                connection.getOrPut(parts[1]) { mutableSetOf() }.add(parts[0])
            }
        }
    }

    private fun isSmallCave(name: String): Boolean {
        return name[0] in 'a'..'z'
    }

    private fun countDistinct(start: String, visited: Set<String>, currentPath: List<String>) {
        connection[start]?.forEach {
            if (it == "end") {
                paths.add(currentPath.plus("end"))
            } else {
                if (!visited.contains(it)) {
                    val newVisited = if (isSmallCave(it)) visited.plus(it) else visited
                    countDistinct(it, newVisited, currentPath.plus(it))
                }
            }
        }
    }

    /*private fun countDistinct2(start: String, visited: Set<String>, currentPath: List<String>, visitedTwice: String, depth: Int) {
        connection[start]?.forEach {
            if (it == "end") {
                paths.add(currentPath.plus("end"))
            } else {
                if (!visited.contains(it)) {
                    var newVisited = visited
                    var newVisitedTwice = visitedTwice

                    if (isSmallCave(it)) {
                        if (visitedTwice == "") {
                            newVisitedTwice = it
                        } else {
                            newVisited = visited.plus(it)
                        }
                    }
                    countDistinct2(it, newVisited, currentPath.plus(it), newVisitedTwice, depth + 1)
                }
            }
        }
    }*/

    private fun countDistinct2(start: String, visited: Set<String>, currentPath: List<String>, visitedTwice: Boolean, depth: Int) {
        connection[start]?.forEach { next ->
            /*for (i in 0 until depth) {
                print(" ")
            }
            println(next)*/

            if (next == "end") {
                paths.add(currentPath.plus("end"))
            } else {
                if (isSmallCave(next)) {
                    if (visited.contains(next)) {
                        if (!visitedTwice) {
                            // Allow two visits to one small cave and record the double visit
                            countDistinct2(next, visited, currentPath.plus(next), true, depth + 1)
                        }
                    } else {
                        countDistinct2(next, visited.plus(next), currentPath.plus(next), visitedTwice, depth + 1)
                    }
                } else {
                    countDistinct2(next, visited, currentPath.plus(next), visitedTwice, depth + 1)
                }
            }
        }
    }

    fun part1(): Int {
        countDistinct("start", emptySet(), listOf("start"))
        //paths.forEach { println(it) }

        return paths.size
    }

    fun part2(): Int {
        countDistinct2("start", emptySet(), listOf("start"), false, 0)
        paths.forEach { println(it) }

        return paths.size
    }
}

fun main() {
    val aoc = Day12("day12/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
