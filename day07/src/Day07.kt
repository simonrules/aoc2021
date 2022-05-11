import java.io.File
import kotlin.math.absoluteValue

class Day07(path: String) {
    private var positions = mutableListOf<Int>()
    private var fuelCost = mutableListOf<Int>()

    init {
        val lines = File(path).readLines()
        val items = lines[0].split(',')
        items.forEach { positions.add(it.toInt()) }
        positions.sort()

        fuelCost.add(0)
        fuelCost.add(1)
        for (i in 2 until 9999) {
            fuelCost.add(fuelCost[i - 1] + i)
        }
    }

    fun part1(): Int {
        var minFuel = 999999
        //var minFuelPos = -1
        val left = positions.first()
        val right = positions.last()

        for (i in left..right) {
            var fuel = 0

            positions.forEach {
                fuel += (i - it).absoluteValue
            }

            if (fuel < minFuel) {
                minFuel = fuel
                //minFuelPos = i
            }
        }

        return minFuel
    }


    fun part2(): Int {
        var minFuel = 999999999
        //var minFuelPos = -1
        val left = positions.first()
        val right = positions.last()

        for (i in left..right) {
            var fuel = 0

            positions.forEach {
                fuel += fuelCost[(i - it).absoluteValue]
            }

            if (fuel < minFuel) {
                minFuel = fuel
                //minFuelPos = i
            }
        }

        return minFuel
    }
}

fun main(args: Array<String>) {
    val aoc = Day07("day07/input.txt")
    //println(aoc.part1())
    println(aoc.part2())
}
