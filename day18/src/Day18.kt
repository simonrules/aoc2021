import java.io.File

sealed class Node() {
    var parent: Node? = null
    abstract val magnitude: Int

    data class Pair(var left: Node, var right: Node) : Node() {
        override fun toString() = "[$left,$right]"
        override val magnitude: Int
            get() = 3 * left.magnitude + 2 * right.magnitude
    }

    data class Number(var value: Int) : Node() {
        override fun toString() = value.toString()
        override val magnitude: Int
            get() = value
    }
}

class Day18(private val path: String) {
    private fun parsePair(expr: String): Node {
        // need to find comma not inside any brackets
        var commaPos = -1
        var nesting = 0
        expr.forEachIndexed { index, c ->
            when (c) {
                '[' -> nesting++
                ']' -> nesting--
                ',' -> if (nesting == 0) commaPos = index
            }
        }
        val leftExpr = expr.substring(0, commaPos)
        val rightExpr = expr.substring(commaPos + 1, expr.length)

        val left = parseExpr(leftExpr)
        val right = parseExpr(rightExpr)

        val pairNode = Node.Pair(left, right)
        left.parent = pairNode
        right.parent = pairNode

        return pairNode
    }

    private fun parseNumber(expr: String): Node {
        return Node.Number(expr.toInt())
    }

    private fun parseExpr(expr: String): Node {
        return if (expr.first() == '[' && expr.last() == ']') {
            parsePair(expr.substring(1, expr.length - 1))
        } else {
            parseNumber(expr)
        }
    }

    private fun add(node1: Node, node2: Node): Node.Pair {
        val newPair = Node.Pair(node1, node2)
        node1.parent = newPair
        node2.parent = newPair
        return newPair
    }

    private fun findRightValue(node: Node): Node? {
        if (node is Node.Number) {
            return null
        }

        var currentNode = node

        while (currentNode.parent != null) {
            val parent = currentNode.parent as Node.Pair
            if (parent.right !== currentNode) {
                currentNode = parent.right
                // now return the leftmost number
                while (currentNode is Node.Pair) {
                    currentNode = currentNode.left
                }
                return currentNode
            }
            currentNode = parent
        }

        return null
    }

    private fun findLeftValue(node: Node): Node? {
        if (node is Node.Number) {
            return null
        }

        var currentNode = node

        while (currentNode.parent != null) {
            val parent = currentNode.parent as Node.Pair
            if (parent.left !== currentNode) {
                currentNode = parent.left
                // now return the rightmost number
                while (currentNode is Node.Pair) {
                    currentNode = currentNode.right
                }
                return currentNode
            }
            currentNode = parent
        }

        return null
    }

    private fun findExplodingPair(root: Node, nesting: Int): Node? {
        if (nesting >= 4 && root is Node.Pair && root.left is Node.Number &&
            root.right is Node.Number) {
            return root
        }

        return when (root) {
            is Node.Pair -> {
                // Go left first, then right, returning the first non-null node
                findExplodingPair(root.left, nesting + 1) ?:
                findExplodingPair(root.right, nesting + 1)
            }
            is Node.Number -> null
        }
    }

    private fun explodePair(node: Node.Pair) {
        val left = findLeftValue(node)
        val right = findRightValue(node)

        if (left != null && left is Node.Number) {
            left.value = left.value + (node.left as Node.Number).value
        }
        if (right != null && right is Node.Number) {
            right.value = right.value + (node.right as Node.Number).value
        }
        val parent = node.parent as Node.Pair
        if (node === parent.left) {
            parent.left = Node.Number(0)
            parent.left.parent = parent
        } else {
            parent.right = Node.Number(0)
            parent.right.parent = parent
        }
    }

    private fun findSplitNumber(node: Node): Node? {
        // find leftmost number >= 10
        return when (node) {
            is Node.Pair -> {
                // Go left first, then right, returning the first non-null node
                findSplitNumber(node.left) ?:
                findSplitNumber(node.right)
            }
            is Node.Number -> if (node.value >=10) node else null
        }
    }

    private fun splitNumber(node: Node.Number) {
        if (node.value < 10) {
            return
        }

        val newPair = Node.Pair(
            Node.Number(node.value / 2),
            Node.Number((node.value + 1) / 2)
        )
        newPair.left.parent = newPair
        newPair.right.parent = newPair
        newPair.parent = node.parent

        val parent = node.parent as Node.Pair
        if (node === parent.left) {
            parent.left = newPair
        } else {
            parent.right = newPair
        }
    }

    private fun reduceNumber(node: Node.Pair) {
        do {
            var didSomething = false
            val explodingPair = findExplodingPair(node, 0)
            if (explodingPair != null) {
                explodePair(explodingPair as Node.Pair)
                didSomething = true
            } else {
                val splitNumber = findSplitNumber(node)
                if (splitNumber != null) {
                    splitNumber(splitNumber as Node.Number)
                    didSomething = true
                }
            }
        } while (didSomething)
    }

    fun part1(): Int {
        val lines = File(path).readLines()
        var node = parseExpr(lines[0])
        for (i in 1 .. lines.lastIndex) {
            var next = parseExpr(lines[i])
            node = add(node, next)

            reduceNumber(node)
        }

        return node.magnitude
    }
    fun part2(): Int {
        var maxMagnitude = 0
        val lines = File(path).readLines()

        for (a in 0 .. lines.lastIndex) {
            for (b in 0 .. lines.lastIndex) {
                if (a == b) {
                    continue
                }

                // Yeah I could precompute here but I don't want to write a clone for Node
                var node = add(parseExpr(lines[a]), parseExpr(lines[b]))
                reduceNumber(node)
                if (node.magnitude > maxMagnitude) {
                    maxMagnitude = node.magnitude
                }
            }

        }

        return maxMagnitude
    }
}

fun main() {
    val aoc = Day18("day18/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
