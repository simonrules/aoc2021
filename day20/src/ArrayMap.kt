class ArrayMap(var width: Int = 250, var height: Int = 250) {
    private var image = Array(width * height) { false }

    val cx get() = width / 2
    val cy get() = height / 2

    private var xRange = IntRange(cx, cx)
    private var yRange = IntRange(cy, cy)

    private fun getAt(x: Int, y: Int): Boolean {
        return image[y * width + x]
    }

    private fun maybeExtendImage(x: Int, y: Int) {

    }

    private fun maybeExtendRange(x: Int, y: Int) {
        if (x < xRange.first) {
            xRange = IntRange(x, xRange.last)
        } else if (x > xRange.last) {
            xRange = IntRange(xRange.first, x)
        }

        if (y < yRange.first) {
            yRange = IntRange(y, yRange.last)
        } else if (y > yRange.last) {
            yRange = IntRange(yRange.first, y)
        }
    }

    fun setAt(x: Int, y: Int, value: Boolean) {
        maybeExtendRange(x, y)
        maybeExtendImage(x, y)
        image[y * width + x] = value
    }

    private fun getSquareValue(x: Int, y: Int): Int {
        var value = 0
        for (i in y - 1 .. y + 1) {
            for (j in x - 1 .. x + 1) {
                val pixel = getAt(j, i)
                value *= 2
                value += if (pixel) 1 else 0
            }
        }
        return value
    }

    fun iterate(algorithm: List<Boolean>): Int {
        // Special case: if algorithm[0] == true then any black surrounded pixel will turn true
        var count = 0

        // Use newImage so we don't modify as we go
        val newImage = Array(width * height) { false }

        for (i in 1 until height - 1) {
            for (j in 1 until width - 1) {
                val value = getSquareValue(j, i)
                if (algorithm[value]) {
                    count++
                }
                newImage[i * width + j] = algorithm[value]
            }
        }

        image = newImage

        xRange = IntRange(xRange.first - 1, xRange.last + 1)
        yRange = IntRange(yRange.first - 1, yRange.last + 1)

        return count
    }

    fun print() {
        //for (i in yRange) {
        //    for (j in xRange) {
        for (i in 0 until height) {
            for (j in 0 until width) {
                print(if (getAt(j, i)) '#' else '.')
            }
            println()
        }
    }

    fun count(): Int {
        return image.count { it }
    }
}
