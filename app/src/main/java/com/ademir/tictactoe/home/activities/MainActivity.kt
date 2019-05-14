package com.ademir.tictactoe.home.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.load
import com.ademir.tictactoe.data.SearchApi
import com.ademir.tictactoe.game.Board
import com.ademir.tictactoe.home.dialogs.SearchDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Accesses to shared preferences can be made in a background thread for performance
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        iv_cross.load(prefs.getString(Board.PREF_CROSS_IMAGE, null), R.drawable.cross_default)
        iv_circle.load(prefs.getString(Board.PREF_CIRCLE_IMAGE, null), R.drawable.circle_default)

        iv_cross.setOnClickListener {
            SearchDialog.newInstance(SearchApi,
                    {
                        iv_cross.load(it, R.drawable.cross_default)
                        prefs.edit().putString(Board.PREF_CROSS_IMAGE, it).apply()
                    }
            ).show(supportFragmentManager)
        }

        iv_circle.setOnClickListener {
            SearchDialog.newInstance(SearchApi,
                    {
                        iv_circle.load(it, R.drawable.circle_default)
                        prefs.edit().putString(Board.PREF_CIRCLE_IMAGE, it).apply()
                    }
            ).show(supportFragmentManager)
        }

        btn_play.setOnClickListener {
            startActivity(Intent(this, BoardActivity::class.java))
        }

        btn_records.setOnClickListener {
            startActivity(Intent(this, ResultsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
    }

}
