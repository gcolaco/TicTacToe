package com.ademir.tictactoe.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by ademir on 24/03/18.
 */
@Entity(tableName = "games")
class GameResult {

    @PrimaryKey(autoGenerate = true)
    var id: Int

    var winner: String

    var winnerImagePath: String?

    var time: Long

    constructor(winner: String, winnerImagePath: String? = null) {
        this.id = 0
        this.winner = winner
        this.winnerImagePath = winnerImagePath
        this.time = System.currentTimeMillis()
    }

    companion object {
        const val CROSS = "X"
        const val Circle = "O"
    }

}