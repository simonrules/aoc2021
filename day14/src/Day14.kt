import java.io.File

class Day14(path: String) {
    private var template: String = ""
    private val rules = mutableMapOf<String, Char>()
    private var pairs = mutableMapOf<String, Long>()

    init {
        var line = 0
        File(path).forEachLine {
            if (line == 0) {
                template = it
            } else if (line > 1) {
                val rule = it.split(" -> ")
                rules[rule[0]] = rule[1][0] // first char only
            }
            line++
        }
    }

    fun part1(): Int {
        var polymer = template

        for (iteration in 0 until 10) {
            val insertList = mutableListOf<Pair<Char, Int>>()

            for (i in 0 until polymer.length - 1) {
                val pair = polymer.substring(i..i + 1)
                val element = rules[pair]
                if (element != null) {
                    insertList.add(Pair(element, i))
                }
            }

            val newPolymer = StringBuilder(polymer)
            var offset = 1
            insertList.forEach {
                newPolymer.insert(it.second + offset, it.first)
                offset++
            }
            polymer = newPolymer.toString()
        }

        val counts = IntArray(26) { 0 }

        polymer.forEach {
            var c = it.code - 'A'.code
            counts[c]++
        }

        var min = 9999999
        var max = 0
        counts.forEach {
            if (it in 1 until min) {
                min = it
            }

            if (it > max) {
                max = it
            }
        }

        return max - min
    }

    private fun addToPairCounts(
        pair: String,
        pairCounts: MutableMap<String, Long>,
        count: Long
    ) {
        if (pair in pairCounts) {
            pairCounts[pair] = pairCounts[pair]!! + count
        } else {
            pairCounts[pair] = count
        }
    }

    fun part2(): Long {
        // First put count of all pairs in the map
        for (i in 0 until template.length - 1) {
            val pair = template.substring(i, i + 2)
            addToPairCounts(pair, pairs, 1L)
        }

        for (iteration in 0 until 40) {
            // Now generate new pairs via insertion rules
            val newPairs = mutableMapOf<String, Long>()
            pairs.forEach { (pair, count) ->
                val insert = rules[pair]
                if (insert == null) {
                    newPairs[pair] = count
                } else {
                    val pair1 = pair[0] + insert.toString()
                    val pair2 = insert.toString() + pair[1]
                    addToPairCounts(pair1, newPairs, count)
                    addToPairCounts(pair2, newPairs, count)
                }
            }

            pairs = newPairs
        }

        val elementCounts = mutableMapOf<Char, Long>()
        pairs.forEach { (pair, count) ->
            if (pair[0] !in elementCounts) {
                elementCounts[pair[0]] = count
            } else {
                elementCounts[pair[0]] = elementCounts[pair[0]]!! + count
            }

            if (pair[1] !in elementCounts) {
                elementCounts[pair[1]] = count
            } else {
                elementCounts[pair[1]] = elementCounts[pair[1]]!! + count
            }
        }

        // The first and last chars never change, and they can't overlap, so add them back in
        elementCounts[template.first()] = elementCounts[template.first()]!! + 1L
        elementCounts[template.last()] = elementCounts[template.last()]!! + 1L

        // The divide by 2 accounts for overlap
        val most = elementCounts.values.max() / 2
        val least = elementCounts.values.min() / 2

        return most - least
    }
}

fun main() {
    val aoc = Day14("day14/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
