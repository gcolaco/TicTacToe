package com.ademir.tictactoe.game

import android.graphics.Point
import java.util.*

/**
 * Created by ademir on 24/03/18.
 */
class Board {

    private val TAG = Board::class.java.simpleName

    private val cells: LinkedHashMap<Point, Cell>
    private var listener: OnGameOverListener?
    private var movesCount: Int
    var victoryCells = ArrayList<Cell?>()
    val size: Int
    var status: Int = CROSS_TURN

    var cellCount: Int

    constructor(boardSize: Int = DEFAULT_BOARD_SIZE, listener: OnGameOverListener? = null) {
        this.cells = LinkedHashMap(boardSize * boardSize)
        this.listener = listener
        this.size = boardSize
        this.movesCount = 0
        this.cellCount = size * size
        initCells()
    }

    private fun initCells() {
        for (x in 0 until size) {
            for (y in 0 until size) {
                val point = Point(x, y)
                cells[point] = Cell(point)
            }
        }
    }

    fun restart() {
        cells.values.forEach { it.reset() }
        status = CROSS_TURN
        movesCount = 0
        victoryCells.clear()
    }

    fun addMove(cell: Cell) {
        if (movesCount < cellCount && status !in arrayOf(TIED, CIRCLE_WON, CROSS_WON)) {
            if (cell.value == Cell.EMPTY) {
                ++movesCount
                when (status) {
                    CROSS_TURN -> cell.value = Cell.CROSS
                    CIRCLE_TURN -> cell.value = Cell.CIRCLE
                }
                updateState(cell.position, cell.value)
            } else {
                // Cell already taken
            }
        }
    }

    fun isGameOver() = movesCount == 0 || status in arrayOf(TIED, CIRCLE_WON, CROSS_WON)

    fun getCells(): Collection<Cell> {
        return cells.values
    }

    fun setListener(listener: OnGameOverListener) {
        this.listener = listener
    }

    private fun updateState(lastMovePosition: Point, lastMoveCellValue: Int) {
        val move = lastMovePosition
        val value = lastMoveCellValue
        // Check if reached the min movesCount to win
        if (movesCount < (2 * size) - 1) {
            // Toggle turn
            when (status) {
                CROSS_TURN -> status = CIRCLE_TURN
                CIRCLE_TURN -> status = CROSS_TURN
            }
        } else {
            // Check if someone has won

            // Check the row of the last moves
            for (x in 0 until size) {
                val cell = cells[Point(x, move.y)]
                victoryCells.add(cell)
                if (cell?.value != value) {
                    victoryCells.clear()
                    break
                } else if (x == size - 1) {
                    // Could also just set the status to receive lastMoveCellValue
                    // then when the listener is notified the last status has the winner
                    status = if (value == CROSS_TURN) {
                        CROSS_WON
                    } else {
                        CIRCLE_WON
                    }
                    listener?.onGameOver(status)
                    return
                }
            }

            // Check columns of the las move
            for (y in 0 until size) {
                val cell = cells[Point(move.x, y)]
                victoryCells.add(cell)
                if (cell?.value != value) {
                    victoryCells.clear()
                    break
                } else if (y == size - 1) {
                    // Could also just set the status to receive lastMoveCellValue
                    // then when the listener is notified the last status has the winner
                    status = if (value == CROSS_TURN) {
                        CROSS_WON
                    } else {
                        CIRCLE_WON
                    }
                    listener?.onGameOver(status)
                    return
                }
            }

            // If last movement was made in the main diagonal
            if (move.x == move.y) {
                for (i in 0 until size) {
                    val cell = cells[Point(i, i)]
                    victoryCells.add(cell)
                    if (cell?.value != value) {
                        victoryCells.clear()
                        break
                    } else if (i == size - 1) {
                        // Could also just set the status to receive lastMoveCellValue
                        // then when the listener is notified the last status has the winner
                        status = if (value == CROSS_TURN) {
                            CROSS_WON
                        } else {
                            CIRCLE_WON
                        }
                        listener?.onGameOver(status)
                        return
                    }
                }
            }

            // If last movement was made in the secondary diagonal
            if (move.x + move.y == size - 1) {
                for (i in 0 until size) {
                    val cell = cells[Point(i, (size - 1) - i)]
                    victoryCells.add(cell)
                    if (cell?.value != value) {
                        victoryCells.clear()
                        break
                    } else if (i == size - 1) {
                        // Could also just set the status to be receive lastMoveCellValue
                        // then when the listener is notified the last status has the winner
                        status = if (value == CROSS_TURN) {
                            CROSS_WON
                        } else {
                            CIRCLE_WON
                        }
                        listener?.onGameOver(status)
                        return
                    }
                }
            }

            // Check if all moves were made
            if (movesCount == (cellCount)) {
                status = TIED
                listener?.onGameOver(status)
            } else {
                // Toggle 'player' turn
                when (status) {
                    CROSS_TURN -> status = CIRCLE_TURN
                    CIRCLE_TURN -> status = CROSS_TURN
                }
            }

        }
    }

    override fun toString(): String {
        val s = when (status) {
            CROSS_TURN -> "Cross"
            CIRCLE_TURN -> "Circle"
            TIED -> "Tied"
            CROSS_WON -> "X Won"
            CIRCLE_WON -> "O Won"
            else -> throw Exception("Invalid board state")
        }
        return "Turn: $s\nMoves: $movesCount"
    }

    data class Cell(val position: Point, var value: Int = EMPTY) {

        var victoryCell: Boolean = false

        fun reset() {
            value = EMPTY
        }

        companion object {
            const val EMPTY = -1
            const val CROSS = 0
            const val CIRCLE = 1
        }

    }

    interface OnGameOverListener {
        fun onGameOver(status: Int)
    }

    companion object {

        const val CROSS_TURN = 0
        const val CIRCLE_TURN = 1
        const val TIED = 2
        const val CROSS_WON = 3
        const val CIRCLE_WON = 4

        const val DEFAULT_BOARD_SIZE = 3

        const val PREF_CROSS_IMAGE = "pref_cross_image"
        const val PREF_CIRCLE_IMAGE = "pref_circle_image"

    }

}