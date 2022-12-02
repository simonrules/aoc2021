import java.io.File

class Day21(path: String) {
    private var start = listOf<Int>()
    private var numRolls = 0

    // Each player always rolls 3 times, but it doesn't matter what the order of the dice are.
    // Since there are 3^3=27 possibilities, we can just assume the universe branches on each
    // cluster of 3 rolls.
    private val probability = mapOf(3 to 1L, 4 to 3L, 5 to 6L, 6 to 7L, 7 to 6L, 8 to 3L, 9 to 1L)

    data class State(
        val positionA: Int,
        val positionB: Int,
        val scoreA: Int,
        val scoreB: Int,
        val nextTurnA: Boolean) {

        private fun move(position: Int, roll: Int): Int {
            var newPosition = position + roll
            if (newPosition > 10) {
                newPosition = ((newPosition - 1) % 10) + 1
            }
            return newPosition
        }

        fun move(roll: Int): State {
            return if (nextTurnA) {
                val newPosition = move(positionA, roll)
                val newScore = scoreA + newPosition
                State(newPosition, positionB, newScore, scoreB, false)
            } else {
                val newPosition = move(positionB, roll)
                val newScore = scoreB + newPosition
                State(positionA, newPosition, scoreA, newScore, true)
            }
        }
    }

    data class Result(val countA: Long, val countB: Long) {
        operator fun times(other: Long): Result {
            return Result(countA * other, countB * other)
        }

        operator fun plus(other: Result): Result {
            return Result(countA + other.countA, countB + other.countB)
        }
    }

    init {
        val values = mutableListOf<Int>()
        File(path).forEachLine { values.add(Character.getNumericValue(it.last())) }
        start = values.toList()
    }

    private fun rollDice(): Int {
        return (numRolls++) % 100 + 1
    }

    fun part1(): Int {
        val score = arrayOf(0, 0)
        val position = start.toIntArray()

        while (true) {
            var roll = rollDice() + rollDice() + rollDice()
            position[0] += roll
            if (position[0] > 10) {
                position[0] = ((position[0] - 1) % 10) + 1
            }
            score[0] += position[0]
            if (score[0] >= 1000) {
                return score[1] * numRolls
            }

            roll = rollDice() + rollDice() + rollDice()
            position[1] += roll
            if (position[1] > 10) {
                position[1] = ((position[1] - 1) % 10) + 1
            }
            score[1] += position[1]
            if (score[1] >= 1000) {
                return score[0] * numRolls
            }
        }
    }

    private fun solveRecurse(state: State): Result {
        var result = Result(0L, 0L)
        if (state.scoreA >= 21) {
            return Result(1L, 0L)
        } else if (state.scoreB >= 21) {
            return Result(0L, 1L)
        } else {
            probability.forEach { (roll, freq) ->
                val newState = state.move(roll)
                result += solveRecurse(newState) * freq
            }
        }

        return result
    }

    fun part2(): Long {
        // Player 1 goes first
        val startState = State(start[0], start[1], 0, 0, true)

        val result = solveRecurse(startState)

        return maxOf(result.countA, result.countB)
    }
}

fun main() {
    val aoc = Day21("day21/input.txt")

    println(aoc.part1())
    println(aoc.part2())
}
