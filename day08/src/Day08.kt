import com.sun.xml.internal.fastinfoset.util.StringArray
import java.io.File

class Day08(private val path: String) {
    fun part1(): Int {
        var unique = 0
        File(path).forEachLine {
            val parts = it.split(" | ")
            val output = parts[1].split(' ')
            output.forEach { part ->
                when (part.length) {
                    2, 3, 4, 7 -> unique++
                }
            }
        }
        return unique
    }

    fun part2(): Long {
        val digits = Array(10) { mutableSetOf<Char>() }
        var total = 0L

        File(path).forEachLine {
            val parts = it.split(" | ")
            val pattern = parts[0].split(' ')

            // first pass
            pattern.forEach { part ->
                val set = part.toSortedSet()
                when (part.length) {
                    2 -> digits[1] = set
                    3 -> digits[7] = set
                    4 -> digits[4] = set
                    7 -> digits[8] = set
                }
            }

            // second pass
            pattern.forEach { part ->
                val set = part.toSortedSet()
                when (part.length) {
                    5 -> {
                        if (set.containsAll(digits[1])) {
                            digits[3] = set
                        } else {
                            if (set.plus(digits[4]).size == 7) {
                                digits[2] = set
                            } else {
                                digits[5] = set
                            }
                        }
                    }
                }
            }

            // third pass
            pattern.forEach { part ->
                val set = part.toSortedSet()
                when (part.length) {
                    6 -> {
                        if (set.containsAll(digits[1])) {
                            if (set.containsAll(digits[5])) {
                                digits[9] = set
                            } else {
                                digits[0] = set
                            }
                        } else {
                            digits[6] = set
                        }
                    }
                }
            }

            val map = mutableMapOf<MutableSet<Char>, Int>()

            digits.forEachIndexed { index, mutableSet ->
                map[mutableSet] = index
            }

            val output = parts[1].split(' ')

            var value = 0
            output.forEach { part ->
                val set = part.toSortedSet()

                value *= 10
                value += map[set]!!
            }
            total += value
        }

        return total
    }
}

fun main(args: Array<String>) {
    val aoc = Day08("day08/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
