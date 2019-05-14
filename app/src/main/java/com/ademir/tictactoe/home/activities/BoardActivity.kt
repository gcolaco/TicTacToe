package com.ademir.tictactoe.home.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.prepare
import com.ademir.tictactoe.game.Board
import com.ademir.tictactoe.home.adapters.BoardAdapter
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {

    private lateinit var adapter: BoardAdapter
    private lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        adapter = BoardAdapter(this)

        board = Board(listener = adapter)

        adapter.setBoard(board)

        recyclerview.prepare(adapter)
        recyclerview.isNestedScrollingEnabled = false

        btn_restart.setOnClickListener {
            board.restart()
            adapter.notifyDataSetChanged()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            if (board.isGameOver()) {
                finish()
                return true
            } else {
                confirmExit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (board.isGameOver()) {
            super.onBackPressed()
        } else {
            confirmExit()
        }
    }

    private fun confirmExit() {
        AlertDialog.Builder(this)
                .setTitle(R.string.text_attention)
                .setMessage(R.string.text_exit_message)
                .setPositiveButton(R.string.no, { dialog, _ -> dialog.dismiss() })
                .setNegativeButton(R.string.yes, { _, _ -> finish() })
                .show()
    }

}
