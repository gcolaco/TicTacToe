package com.ademir.tictactoe

import android.app.Application
import com.ademir.tictactoe.data.GameResultDb
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by ademir on 24/03/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        database = GameResultDb.create(this)
    }

    companion object {
        val DISK_IO: ExecutorService = Executors.newSingleThreadExecutor()
        lateinit var database: GameResultDb
    }

}