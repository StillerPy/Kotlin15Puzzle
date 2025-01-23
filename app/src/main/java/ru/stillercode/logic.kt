package ru.stillercode

data class XY(val x: Int, val y: Int)

class Game15(val level: Int) {
    val board = arrayOf(
        intArrayOf(1, 2, 3, 4),
        intArrayOf(5, 6, 7, 8),
        intArrayOf(9, 10, 11, 12),
        intArrayOf(13, 14, 15, 0)
    )
    fun tap(xy: XY) {
        board[xy.y][xy.x] = 0
    }
    fun get(x: Int, y: Int): Int {
        return board[y][x]
    }
}
