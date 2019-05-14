package com.ademir.tictactoe.home.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.inflate
import com.ademir.tictactoe.commons.load
import com.ademir.tictactoe.data.models.GameResult
import kotlinx.android.synthetic.main.row_result.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ademir on 24/03/18.
 */
class ResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<GameResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_ITEM -> ResultViewHolder(parent.inflate(viewType))
            VIEW_EMPTY -> EmptyViewHolder(parent.inflate(viewType))
            else -> throw Exception("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_ITEM) {
            (holder as ResultViewHolder).bind(dataSet[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (dataSet.isEmpty()) {
            return VIEW_EMPTY
        } else {
            return VIEW_ITEM
        }
    }

    override fun getItemCount(): Int {
        if (dataSet.isEmpty()) {
            return 1
        } else {
            return dataSet.size
        }
    }

    fun setData(results: Collection<GameResult>) {
        this.dataSet.clear()
        this.dataSet.addAll(results)
        notifyDataSetChanged()
    }

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateFormatter by lazy {
            SimpleDateFormat(view.context.getString(R.string.date_formatter_pattern), Locale.getDefault())
        }

        fun bind(result: GameResult) = with(itemView) {
            val text: String
            val placeHolder: Int
            if (result.winner == GameResult.CROSS) {
                text = context.getString(R.string.text_cross_won)
                placeHolder = R.drawable.cross_default
            } else {
                text = context.getString(R.string.text_circle_won)
                placeHolder = R.drawable.circle_default
            }
            tv_winner.text = text
            tv_date.text = dateFormatter.format(Date(result.time))
            iv_picture.load(result.winnerImagePath, placeHolder)
        }
    }

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        const val VIEW_ITEM = R.layout.row_result
        const val VIEW_EMPTY = R.layout.row_result_empty
    }

}