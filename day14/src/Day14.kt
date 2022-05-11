import java.io.File

class Day14(path: String) {
    private var template: String = ""
    private val rules = mutableMapOf<String, Char>()

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
        for (rep in 0 until 10) {
            val insertList = mutableListOf<Pair<Char, Int>>()

            for (i in 0 until template.length - 1) {
                val pair = template.substring(i..i + 1)
                val element = rules[pair]
                if (element != null) {
                    insertList.add(Pair(element, i))
                }
            }

            val newTemplate = StringBuilder(template)
            var offset = 1
            insertList.forEach {
                newTemplate.insert(it.second + offset, it.first)
                offset++
            }
            template = newTemplate.toString()
        }

        val counts = IntArray(26) { 0 }

        template.forEach {
            var c = it.toInt() - 'A'.toInt()
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

    fun part2(): Int {

        return 0
    }
}

fun main() {
    val aoc = Day14("day14/test1.txt")
    println(aoc.part1())
    println(aoc.part2())
}
