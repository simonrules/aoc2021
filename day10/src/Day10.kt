import java.io.File

class Day10(private val path: String) {
    private val lines = mutableListOf<String>()

    init {
        File(path).forEachLine { line ->
            lines.add(line)
        }

    }


    private fun match(a: Char, b: Char): Boolean {
        if (a == '(' && b == ')') {
            return true
        } else if (a == '{' && b == '}') {
            return true
        } else if (a == '[' && b == ']') {
            return true
        } else if (a == '<' && b == '>') {
            return true
        }

        return false
    }

    private fun checkAndRemove(stack: ArrayDeque<Char>, last: Char): Boolean {
        val end = stack.last()

        if (match(end, last)) {
            stack.removeLast()
        } else {
            return false
        }

        return true
    }

    private fun parseLine(line: String): Int {
        val stack = ArrayDeque<Char>()

        line.forEach { c ->
            // check for openings
            var valid = true
            when (c) {
                '[', '{', '<', '(' -> stack.add(c)
                ']', '}', '>', ')' -> valid = checkAndRemove(stack, c)
            }

            if (!valid) {
                return when (c) {
                    ')' -> 3
                    ']' -> 57
                    '}' -> 1197
                    '>' -> 25137
                    else -> 0
                }
            }
        }
        return 0
    }

    private fun parseLine2(stack: ArrayDeque<Char>, line: String): Boolean {
        line.forEach { c ->
            // check for openings
            var valid = true
            when (c) {
                '[', '{', '<', '(' -> stack.add(c)
                ']', '}', '>', ')' -> valid = checkAndRemove(stack, c)
            }

            if (!valid) {
                return false
            }
        }
        return true
    }

    private fun complete(stack: ArrayDeque<Char>): Long {
        var points = 0L
        while (stack.size > 0) {
            val last = stack.removeLast()

            points = points * 5 + when (last) {
                '(' -> 1
                '[' -> 2
                '{' -> 3
                '<' -> 4
                else -> 0
            }
        }
        return points
    }

    fun part1(): Int {
        var points = 0
        lines.forEach { line ->
            points += parseLine(line)
        }

        return points
    }

    fun part2(): Long {
        var points = mutableListOf<Long>()
        lines.forEach { line ->
            val stack = ArrayDeque<Char>()
            if (parseLine2(stack, line)) {
                points.add(complete(stack))
            }
        }
        points.sort()
        return points[points.size / 2]
    }
}

fun main(args: Array<String>) {
    val aoc = Day10("day10/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
