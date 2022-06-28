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

    private fun findHorizontalIterations(): MutableSet<Int> {
        val iterations = mutableSetOf<Int>()

        // check x velocities for intersection
        val vel = 6
        //for (vel in 1..xRange.last) {
            var iteration = 0
            var x = 0
            var xVel = vel

            while (xVel > 0 && !xRange.contains(x)) {
                x += xVel
                xVel--
                iteration++
            }
            while (xVel >= 0 && xRange.contains(x)) {
                //println("$vel $xVel $x $iteration")
                iterations.add(iteration)
                x += xVel
                xVel--
                iteration++
            }
        //}

        return iterations
    }

    private fun findVerticalVelocities(iterations: Int): MutableSet<Int> {
        val velocities = mutableSetOf<Int>()

        for (vel in 1..yRange.last) {
            for (i in 1..iterations) {

            }
        }

        return velocities
    }

    fun part1(): Int {
        val xIterations = findHorizontalIterations()

        // We need to find y velocities that end up in the target area in
        // a particular number of iterations.
        val yVelocities = xIterations.forEach {
            findVerticalVelocities(it)
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
