import java.io.File

class Day16(path: String) {
    var binary = ""
    init {
        val text = File(path).readText()

        text.forEach {
            binary = binary.plus(when(it) {
                '0' -> "0000"
                '1' -> "0001"
                '2' -> "0010"
                '3' -> "0011"
                '4' -> "0100"
                '5' -> "0101"
                '6' -> "0110"
                '7' -> "0111"
                '8' -> "1000"
                '9' -> "1001"
                'A' -> "1010"
                'B' -> "1011"
                'C' -> "1100"
                'D' -> "1101"
                'E' -> "1110"
                'F' -> "1111"
                else -> ""
            })
        }
    }
    fun part1(): Int {

        return 0
    }

    fun part2(): Int {

        return 0
    }
}

fun main() {
    val aoc = Day16("day16/test1.txt")
    println(aoc.part1())
    println(aoc.part2())
}
