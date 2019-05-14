package com.ademir.tictactoe.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton

/**
 * Created by ademir on 24/03/18.
 */
class BoardCellView : ImageButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}