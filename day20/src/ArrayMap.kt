class ArrayMap(private var width: Int = 800, private var height: Int = 800) {
    private var image = Array(width * height) { false }

    val cx get() = width / 2
    val cy get() = height / 2

    private var xRange = IntRange(cx, cx)
    private var yRange = IntRange(cy, cy)

    private var iteration = 0

    private fun getAt(x: Int, y: Int): Boolean {
        return image[y * width + x]
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
        var count = 0

        // Use newImage so we don't modify as we go
        val newImage = Array(width * height) { false }

        for (i in yRange.first - 1 .. yRange.last + 1) {
            for (j in xRange.first - 1 .. xRange.last + 1) {
                val value = getSquareValue(j, i)
                if (algorithm[value]) {
                    count++
                }
                newImage[i * width + j] = algorithm[value]
            }
        }

        // Expand the range for the new pixels
        xRange = IntRange(xRange.first - 1, xRange.last + 1)
        yRange = IntRange(yRange.first - 1, yRange.last + 1)

        // Special case: if algorithm[0] == true then any black surrounded pixel will turn true
        // at the end of an even iteration.
        if (algorithm[0] && iteration % 2 == 0) {
            drawBorder(newImage, 1)
            drawBorder(newImage, 2)
        }

        image = newImage
        iteration++

        return count
    }

    private fun drawBorder(image: Array<Boolean>, offset: Int) {
        val left = xRange.first - offset
        val right = xRange.last + offset
        val bottom = yRange.first - offset
        val top = yRange.last + offset

        // Draw box around image
        for (i in bottom..top) {
            image[i * width + left] = true
            image[i * width + right] = true
        }
        for (j in left..right) {
            image[bottom * width + j] = true
            image[top * width + j] = true
        }
    }

    fun print() {
        for (i in yRange.first - 2 .. yRange.last + 2) {
            for (j in xRange.first - 2 .. yRange.last + 2) {
                print(if (getAt(j, i)) '#' else '.')
            }
            println()
        }
    }

    fun count(): Int {
        return image.count { it }
    }
}
