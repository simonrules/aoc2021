import java.io.File

data class Probe(var x: Int, var y: Int)

class Day17(path: String) {
    private val regex = "target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)".toRegex()
    private val xRange: IntRange
    private val yRange: IntRange
    private val stepToDistance = mutableListOf<Int>()
    private val distanceToStep = mutableMapOf<Int, Int>()

    init {
        val text = File(path).readText()

        var matchResult = regex.find(text)
        val (a, b, c, d) = matchResult!!.destructured
        xRange = IntRange(a.toInt(), b.toInt())
        yRange = IntRange(c.toInt(), d.toInt())

        var total = 0
        var inc = 1
        for (i in 0 until 100) {
            stepToDistance.add(total)
            total += inc
            inc++
        }
        stepToDistance.forEachIndexed { step, distance -> distanceToStep[distance] = step }
    }

    // - The probe's x position increases by its x velocity.
    // - The probe's y position increases by its y velocity.
    // - Due to drag, the probe's x velocity changes by 1 toward the value 0; that is,
    //   it decreases by 1 if it is greater than 0, increases by 1 if it is less than 0,
    //   or does not change if it is already 0.
    // - Due to gravity, the probe's y velocity decreases by 1.

    private fun inTargetArea(probe: Probe): Boolean {
        return (probe.x in xRange && probe.y in yRange)
    }

    private fun beyondTargetArea(probe: Probe) : Boolean {
        return (probe.x > xRange.last || probe.y < yRange.last)
    }

    fun part1(): Int {
        val probe = Probe(0, 0)
        val xVelInRange = mutableMapOf<Int, MutableSet<Int>>()
        val yVelInRange = mutableMapOf<Int, MutableSet<Int>>()
        val maxY = mutableMapOf<Int, Int>()

        // check x velocities for intersection first
        for (vel in 1 .. xRange.last) {
            var x = probe.x
            var xVel = vel
            while (xVel > 0 && x !in xRange) {
                x += xVel
                xVel--
            }
            while (xVel >= 0 && x in xRange) {
                xVelInRange.putIfAbsent(x, mutableSetOf())
                xVelInRange[x]!!.add(vel)
                x += xVel
                xVel--
            }
        }

        // then y
        for (vel in 1 .. 100) {
            var max = probe.y
            var y = probe.y
            var yVel = vel
            while (y >= yRange.last && y !in yRange) {
                y += yVel
                yVel--
                if (y > max) {
                    max = y
                }
            }
            maxY[vel] = max
            while (y >= yRange.first && y in yRange) {
                yVelInRange.putIfAbsent(y, mutableSetOf())
                yVelInRange[y]!!.add(vel)
                y += yVel
                yVel--
            }
        }

        return 0
    }

    fun part2(): Int {
        return 0
    }
}

fun main() {
    val aoc = Day17("day17/test1.txt")
    println(aoc.part1())
    println(aoc.part2())
}
