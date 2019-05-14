package com.ademir.tictactoe.home.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ademir.tictactoe.App
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.prepare
import com.ademir.tictactoe.home.adapters.ResultAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            title = getString(R.string.text_results)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val adapter = ResultAdapter()

        recyclerview.prepare(adapter, LinearLayoutManager(this, VERTICAL, false))

        App.database.gameDao().all().observe(this, Observer {
            it?.let {
                progressbar.visibility = View.GONE
                adapter.setData(it)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_results, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_clear) {
            App.DISK_IO.execute {
                App.database.gameDao().drop()
                AndroidSchedulers.mainThread().scheduleDirect {
                    recyclerview.adapter.notifyDataSetChanged()
                }
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
