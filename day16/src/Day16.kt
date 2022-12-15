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

    private fun parsePacket(pos: Int): Pair<Int, Long> {
        val version = getValue(binary.substring(pos, pos + 3))
        versionTotal += version
        val type = getValue(binary.substring(pos + 3, pos + 6))

        return if (type == 4) {
            parseLiteral(pos + 6)
        } else {
            parseOperator(pos + 6, type)
        }
    }

    private fun parseLiteral(pos: Int): Pair<Int, Long> {
        var cur = pos
        var value = 0L
        var lastOne = false
        while (!lastOne) {
            lastOne = binary[cur] == '0'
            value *= 16 // number is in chunks of 4 bits
            value += getValue(binary.substring(cur + 1, cur + 5))
            cur += 5
        }
        //println("literal=$value")

        return Pair(cur, value)
    }

    private fun operation(type: Int, values: List<Long>): Long {
        return when(type) {
            0 -> values.sum()
            1 -> values.reduce { acc, i ->  acc * i }
            2 -> values.minOrNull()!!
            3 -> values.maxOrNull()!!
            5 -> if (values[0] > values[1]) 1 else 0
            6 -> if (values[0] < values[1]) 1 else 0
            7 -> if (values[0] == values[1]) 1 else 0
            else -> 0
        }
    }

    private fun parseOperator(pos: Int, type: Int): Pair<Int, Long> {
        var cur = pos
        val values = mutableListOf<Long>()

        if (binary[cur] == '0') {
            var length = getValue(binary.substring(cur + 1, cur + 16))
            cur += 16
            //println("length=$length")
            while (length > 0) {
                val (newCur, value) = parsePacket(cur)
                length -= (newCur - cur)
                cur = newCur
                values.add(value)
            }
        } else {
            var number = getValue(binary.substring(cur + 1, cur + 12))
            cur += 12
            //println("number=$number")
            while (number > 0) {
                val (newCur, value) = parsePacket(cur)
                number--
                cur = newCur
                values.add(value)
            }
        }

        return Pair(cur, operation(type, values))
    }

    private fun getValue(bits: String): Int {
        var value = 0

        for (i in 0..bits.lastIndex) {
            value *= 2
            value += if (bits[i] == '1') 1 else 0
        }

        return value
    }

    fun part1(): Int {
        parsePacket(0)

        return versionTotal
    }

    fun part2(): Long {
        val (_, value) = parsePacket(0)

        return value
    }
}

fun main() {
    val aoc = Day16("day16/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
