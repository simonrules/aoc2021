import java.io.File

class Day02(path: String) {
    private val command = mutableListOf<String>()
    init {
        File(path).forEachLine {
            command.add(it)
        }
    }

    fun part1(): Int {
        var x = 0
        var y = 0

        command.forEach {
            val amount = it.substring(it.lastIndexOf(" ") + 1).toInt()
            if (it.contains("forward")) {
                x += amount
            } else if (it.contains("down")) {
                y += amount
            } else if (it.contains("up")) {
                y -= amount
            }
        }

        return x * y
    }


    fun part2(): Int {
        var x = 0
        var depth = 0
        var aim = 0

        command.forEach {
            val amount = it.substring(it.lastIndexOf(" ") + 1).toInt()
            if (it.contains("forward")) {
                x += amount
                depth += amount * aim
            } else if (it.contains("down")) {
                aim += amount
            } else if (it.contains("up")) {
                aim -= amount
            }
        }

        return x * depth
    }
}

fun main(args: Array<String>) {
    val aoc = Day02("day02/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
