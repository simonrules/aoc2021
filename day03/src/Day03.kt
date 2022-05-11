import java.io.File
import javax.xml.stream.events.Characters

class Day03(path: String) {
    private val numbers = mutableListOf<String>()

    init {
        File(path).forEachLine {
            numbers.add(it)
        }
    }

    fun part1(): Int {
        var gamma = 0
        var epsilon = 0
        val sum = IntArray(numbers[0].length)

        numbers.forEach { number ->
            number.forEachIndexed { index, c ->
                sum[index] += Character.getNumericValue(c)
            }
        }

        sum.forEach {
            gamma = gamma shl 1
            epsilon = epsilon shl 1
            if (it >= numbers.size / 2) {
                // gamma
                gamma = gamma or 1
            } else {
                // epsilon
                epsilon = epsilon or 1
            }
        }

        return gamma * epsilon
    }


    fun part2(): Int {
        var o2 = 0
        var co2 = 0

        // o2
        var candidates = numbers.toMutableList()
        var pos = 0
        while (candidates.size > 1) {
            val ones = candidates.count { it[pos] == '1' }
            val zeroes = candidates.size - ones
            candidates = if (ones >= zeroes) {
                candidates.filter { it[pos] == '1' }.toMutableList()
            } else {
                candidates.filter { it[pos] == '0' }.toMutableList()
            }
            pos++
        }

        candidates[0].forEach {
            o2 = o2 shl 1
            o2 = o2 or Character.getNumericValue(it)
        }

        // co2
        candidates = numbers.toMutableList()
        pos = 0
        while (candidates.size > 1) {
            val zeroes = candidates.count { it[pos] == '0' }
            val ones = candidates.size - zeroes
            candidates = if (zeroes <= ones) {
                candidates.filter { it[pos] == '0' }.toMutableList()
            } else {
                candidates.filter { it[pos] == '1' }.toMutableList()
            }
            pos++
        }

        candidates[0].forEach {
            co2 = co2 shl 1
            co2 = co2 or Character.getNumericValue(it)
        }

        return o2 * co2
    }
}

fun main(args: Array<String>) {
    val aoc = Day03("day03/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
