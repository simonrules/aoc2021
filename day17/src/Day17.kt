import java.io.File

//data class Velocity(var x: Int, var y: Int)

//data class Intercept(var pos: Int, var iteration: Int)

class Day17(path: String) {
    private val regex = "target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)".toRegex()
    private val xRange: IntRange
    private val yRange: IntRange
    private val stepToDistance = mutableListOf<Int>()
    private val distanceToStep = mutableMapOf<Int, Int>()

    //private val xIntercept = mutableSetOf<Intercept>()
    //private val yIntercept = mutableSetOf<Intercept>()

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
        for (vel in 1..xRange.last) {
            var iteration = 0
            var x = 0
            var xVel = vel

            while (xVel > 0 && !xRange.contains(x)) {
                x += xVel
                xVel--
                iteration++
            }
            while (iteration <= 1000 && xRange.contains(x)) {
                //println("$vel $xVel $x $iteration")
                iterations.add(iteration)
                x += xVel
                if (xVel > 0) {
                    xVel--
                }
                iteration++
            }
        }

        return iterations
    }

    private fun findHighestVerticalVelocity(xIterations: MutableSet<Int>): Int {
        var highest = 0

        xIterations.forEach { iterations ->
            // Try y velocities starting with 0
            for (vel in 0..10000) {
                var maxY = 0
                var yVel = vel
                var y = 0
                for (i in 1..iterations) {
                    y += yVel
                    yVel--
                    if (y > maxY) {
                        maxY = y
                    }
                    if (yRange.contains(y)) {
                        if (maxY > highest) {
                            highest = maxY
                        }
                        //println(vel)
                    }
                }
            }
        }

        return highest
    }

    private fun findHorizontalVelocities(): MutableSet<Int> {
        val velocities = mutableSetOf<Int>()

        // check x velocities for intersection
        for (vel in 1..xRange.last) {
            var x = 0
            var xVel = vel

            while (xVel > 0 && !xRange.contains(x)) {
                x += xVel
                xVel--
            }
            while (xVel > 0 && xRange.contains(x)) {
                velocities.add(vel)
                x += xVel
                if (xVel > 0) {
                    xVel--
                }
            }
        }

        return velocities
    }

    private fun findVerticalVelocities(): MutableSet<Int> {
        val velocities = mutableSetOf<Int>()

        // check y velocities for intersection
        for (vel in yRange.first..10000) {
            var y = 0
            var yVel = vel

            while (y > yRange.last) {
                y += yVel
                yVel--
            }
            while (yRange.contains(y)) {
                velocities.add(vel)
                y += yVel
                yVel--
            }
        }

        return velocities
    }

    private fun hitsTarget(xVelStart: Int, yVelStart: Int): Boolean {
        var x = 0
        var y = 0
        var xVel = xVelStart
        var yVel = yVelStart

        while (x <= xRange.last && y >= yRange.first) {
            if (xRange.contains(x) && yRange.contains(y)) {
                return true
            }

            x += xVel
            y += yVel

            if (xVel > 0) {
                xVel--
            }
            yVel--
        }

        return false
    }

    fun part1(): Int {
        val xIterations = findHorizontalIterations()

        // We need to find y velocities that end up in the target area in
        // a particular number of iterations.
        return findHighestVerticalVelocity(xIterations)
    }

    fun part2(): Int {
        val xVelocities = findHorizontalVelocities()
        val yVelocities = findVerticalVelocities()

        var count = 0
        xVelocities.forEach { x ->
            yVelocities.forEach { y ->
                if (hitsTarget(x, y)) {
                    count++
                }
            }
        }

        return count
    }
}

fun main() {
    val aoc = Day17("day17/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
