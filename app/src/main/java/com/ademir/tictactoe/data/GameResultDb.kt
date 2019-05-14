package com.ademir.tictactoe.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ademir.tictactoe.data.dao.GameResultDao
import com.ademir.tictactoe.data.models.GameResult

/**
 * Created by ademir on 24/03/18.
 */

@Database(entities = [GameResult::class], version = 2, exportSchema = false)
abstract class GameResultDb : RoomDatabase() {

    abstract fun gameDao(): GameResultDao

    companion object {
        fun create(context: Context): GameResultDb {
            return Room
                    .databaseBuilder(context.applicationContext, GameResultDb::class.java, "tic_tac_toe.db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

}