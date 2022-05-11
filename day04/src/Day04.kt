import java.io.File

class Day04(path: String) {
    private var numbers: List<Int>
    private val boards = mutableListOf<MutableList<Int>>()
    private val won: MutableList<Boolean>

    init {
        val lines = File(path).readLines()

        numbers = lines[0].split(',').map { it.toInt() }

        val numBoards = (lines.size - 1) / 6
        won = MutableList(numBoards) { false }

        var start = 2 // start at line 2
        for (b in 0 until numBoards) {
            // read boards
            val board = mutableListOf<Int>()
            for (i in 0..4) {
                val row = lines[start + i].trim().split("\\s+".toRegex()).map { it.toInt() }
                board.addAll(row)
            }
            boards.add(board)
            start += 6
        }
    }

    private fun isWinningBoard(b: Int): Boolean {
        val board = boards[b]

        // check rows
        for (r in 0..4) {
            var count = 0
            for (c in 0..4) {
                if (board[r * 5 + c] == -1) {
                    count++
                }
            }
            if (count == 5) {
                return true
            }
        }

        // check cols
        for (c in 0..4) {
            var count = 0
            for (r in 0..4) {
                if (board[r * 5 + c] == -1) {
                    count++
                }
            }
            if (count == 5) {
                return true
            }
        }

        return false
    }

    private fun findWinningBoard(): Int {
        for (b in boards.indices) {
            // skip any already winning boards
            if (won[b]) {
                continue
            }
            if (isWinningBoard(b)) {
                return b
            }
        }
        return -1
    }

    private fun boardScore(b: Int): Int {
        var score = 0
        val board = boards[b]

        for (i in board.indices) {
            if (board[i] != -1) {
                score += board[i]
            }
        }

        return score
    }

    fun part1(): Int {
        numbers.forEach { n ->
            boards.forEach { b ->
                // doesn't work :(
                //b.replaceAll { elem -> if (elem == n) -1 else n }

                for (i in b.indices) {
                    if (b[i] == n) {
                        b[i] = -1
                    }
                }

                val winning = findWinningBoard()
                if (winning != -1) {
                    return n * boardScore(winning)
                }
            }
        }

        return 0
    }


    fun part2(): Int {
        var winning = 0
        numbers.forEach { n ->
            boards.forEachIndexed { i, b ->
                // doesn't work :(
                //b.replaceAll { elem -> if (elem == n) -1 else n }

                for (i in b.indices) {
                    if (b[i] == n) {
                        b[i] = -1
                    }
                }

                winning = findWinningBoard()
                if (winning != -1) {
                    won[i] = true
                }
                if (won.all { it }) {
                    return n * boardScore(winning)
                }
            }
        }

        return 0
    }
}

fun main(args: Array<String>) {
    val aoc = Day04("day04/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
