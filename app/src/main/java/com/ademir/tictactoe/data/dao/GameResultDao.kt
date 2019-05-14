package com.ademir.tictactoe.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.ademir.tictactoe.data.models.GameResult

/**
 * Created by ademir on 24/03/18.
 */
@Dao
interface GameResultDao {

    @Insert(onConflict = REPLACE)
    fun insert(gameResult: GameResult)

    @Query("SELECT * FROM games ORDER BY time DESC")
    fun all(): LiveData<List<GameResult>>

    @Delete
    fun delete(gameResult: GameResult)

    @Query("DELETE FROM games")
    fun drop()

}