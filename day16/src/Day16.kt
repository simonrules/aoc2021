import java.io.File

class Day16(path: String) {
    var binary = ""
    var versionTotal = 0

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

    private fun parsePacket(pos: Int): Int {
        val version = getValue(binary.substring(pos, pos + 3))
        versionTotal += version
        val type = getValue(binary.substring(pos + 3, pos + 6))

        return if (type == 4) {
            parseLiteral(pos + 6)
        } else {
            // operator
            0
            //parseOperator(pos + 6)
        }
    }

    private fun parseLiteral(pos: Int): Int {
        var pos = 0
        var value = 0
        var lastOne = false
        while (!lastOne) {
            lastOne = binary[pos] == '0'
            value *= 16
            value += getValue(binary.substring(pos + 1, pos + 5))
            pos += 5
        }

        return pos
    }

    /*private fun parseOperator(pos: Int): Int {
        var pos = 1

        if (binary[0] == '0') {
            var length = getValue(binary.substring(pos, pos + 15))
            pos += 15
            println("length=$length")
            while (length > 0) {
                val bitsRead = parsePacket(binary.substring(pos, pos + length))
                length -= bitsRead
            }
        } else {
            val number = getValue(binary.substring(pos, pos + 11))
            println("number=$number")
        }

        return 0
    }*/

    private fun getValue(bits: String): Int {
        var value = 0

        for (i in 0..bits.lastIndex) {
            value *= 2
            value += if (bits[i] == '1') 1 else 0
        }

        return value
    }

    fun part1(): Int {
        parsePacket("110100101111111000101000")

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
