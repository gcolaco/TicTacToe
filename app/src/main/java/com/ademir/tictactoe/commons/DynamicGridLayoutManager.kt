package com.ademir.tictactoe.commons

import android.content.Context
import android.support.v7.widget.GridLayoutManager

/**
 * Created by ademir on 15/01/18.
 */
class BoardLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {
    
    // The first item (Header) in the layout will fill all columns
    // Any other item (Cells) will fill only 1 column
    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> spanCount
                    else -> 1
                }
            }
        }
    }

}