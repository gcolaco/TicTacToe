package com.ademir.tictactoe.commons

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ademir.tictactoe.R
import com.squareup.picasso.Picasso

/**
 * Created by ademir on 23/03/18.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun RecyclerView.prepare(adapter: RecyclerView.Adapter<*>,
                         layoutManager: RecyclerView.LayoutManager = BoardLayoutManager(context, 3),
                         hasFixedSize: Boolean = true) {
    this.adapter = adapter
    this.layoutManager = layoutManager
    this.setHasFixedSize(hasFixedSize)
}

fun ImageView.load(path: String?, placeholder: Int = R.drawable.ic_launcher_background) {
    Picasso.with(context)
            .load(path)
            .placeholder(placeholder)
            .into(this)
}
