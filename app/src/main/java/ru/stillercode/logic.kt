package ru.stillercode

import kotlin.random.Random

data class XY(val x: Int, val y: Int) {
    fun getAdjacentSquares(): MutableList<XY> {
        val out = mutableListOf<XY>()
        for (vec in DIRECTIONS) {
            val square = add(vec)
            if (square.isOnBoard()) {
                out.add(square)
            }
        }
        return out
    }
    fun isOnBoard(): Boolean {
        return x in 0..3 && y in 0..3
    }
    fun add(vector: XY): XY {
        return XY(x + vector.x, y + vector.y)
    }
}

private val DIRECTIONS = listOf(XY(0, -1), XY(1, 0), XY(0, 1), XY(-1, 0))

class Game15(val level: Int) {
    var isSolved = false
    private var moves = 0
    private val sample = arrayOf(
        intArrayOf(1, 2, 3, 4),
        intArrayOf(5, 6, 7, 8),
        intArrayOf(9, 10, 11, 12),
        intArrayOf(13, 14, 15, 0)
    )
    private val board = arrayOf(
        intArrayOf(1, 2, 3, 4),
        intArrayOf(5, 6, 7, 8),
        intArrayOf(9, 10, 11, 12),
        intArrayOf(13, 14, 15, 0)
    )
    init {
        val shuffleMoves = getShuffleMoves()
        val states = mutableListOf<Array<IntArray>>()
        for (i in 1..shuffleMoves) {
            val zeroPosition = findZero()
            val adjacentSquares = zeroPosition.getAdjacentSquares()
            while (true) {
                var isOkay = true
                val square = adjacentSquares[Random.nextInt(adjacentSquares.size)]
                swap(square, zeroPosition)
                for (state in states) {
                    if (board contentDeepEquals state) {
                        isOkay = false
                        break
                    }
                }
                if (isOkay) {
                    states.add(deepCopy(board))
                    break
                } else {
                    swap(zeroPosition, square)
                    continue
                }
            }
            moves = 0
        }
    }
    private fun deepCopy(state: Array<IntArray>): Array<IntArray> {
        return state.map {it.clone()}.toTypedArray()
    }
    fun tap(xy: XY): String {
        if (isSolved) {
            return "Start new game! Moves made: $moves"
        }
        val zeroPosition = findZero()
        val adjacentSquares = xy.getAdjacentSquares()
        if (adjacentSquares.contains(zeroPosition)) {
            swap(xy, zeroPosition)
            isSolved = checkIfSolved()
            if (isSolved) {
                return "Success! Moves made: $moves"
            }
            return "Moves: $moves"
        } else {
            return "Click on squares adjacent\nto the empty one!\nMoves: $moves"
        }

    }
    private fun swap(xy: XY, zeroPosition: XY) {
        val n = get(xy)
        set(xy, 0)
        set(zeroPosition, n)
        moves++
    }
    fun set(xy: XY, n: Int) {
        board[xy.y][xy.x] = n
    }
    fun get(xy: XY): Int {
        return board[xy.y][xy.x]
    }
    fun get(x: Int, y: Int): Int {
        return board[y][x]
    }
    private fun findZero(): XY {
        for (x in 0..3) {
            for (y in 0..3) {
                if (get(x, y) == 0) {
                    return XY(x, y)
                }
            }
        }
        throw IllegalStateException("Zero is not on board!")
    }
    fun checkIfSolved(): Boolean {
        for (x in 0..3) {
            for (y in 0..3) {
                if (board[y][x] != sample[y][x]) {
                    return false
                }
            }
        }
        return true
    }
    fun getShuffleMoves(): Int {
        return level + 2
    }
}
