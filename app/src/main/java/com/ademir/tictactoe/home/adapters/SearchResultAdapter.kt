package com.ademir.tictactoe.home.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.inflate
import com.ademir.tictactoe.commons.load
import com.ademir.tictactoe.data.models.NetworkState
import com.ademir.tictactoe.data.models.Status
import kotlinx.android.synthetic.main.row_cell.view.*
import kotlinx.android.synthetic.main.row_loader.view.*

/**
 * Created by ademir on 25/03/18.
 */
class SearchResultAdapter(val imageSelectedListener: (String) -> Unit,
                          val retryListener: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<String>()
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_EMPTY -> EmptyViewHolder(parent.inflate(viewType), retryListener)
            VIEW_ITEM -> ImageViewHolder(parent.inflate(viewType), imageSelectedListener)
            else -> throw IllegalArgumentException("Invalid view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_ITEM -> (holder as ImageViewHolder).bind(dataSet[position])
            VIEW_EMPTY -> (holder as EmptyViewHolder).bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (dataSet.isEmpty() && position == 0) {
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

    fun setNetworkState(networkState: NetworkState?) {
        this.networkState = networkState
        notifyItemChanged(0)
    }

    fun setData(images: Collection<String>) {
        dataSet.clear()
        dataSet.addAll(images)
        notifyDataSetChanged()
    }

    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    class ImageViewHolder(view: View, val imageSelectedListener: (String) -> Unit = { }) : RecyclerView.ViewHolder(view) {

        fun bind(imagePath: String) = with(itemView) {
            iv_picture.load(imagePath)
            setOnClickListener { imageSelectedListener(imagePath) }
        }

    }

    inner class EmptyViewHolder(view: View, val retryListener: () -> Unit = { }) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) = with(itemView) {
            networkState?.let {

                if (networkState.status == Status.RUNNING) {
                    progressbar.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                } else {
                    progressbar.visibility = View.GONE
                }

                when (networkState.status) {
                    Status.SUCCESS -> {
                        if (dataSet.isEmpty()) {
                            tv_error.visibility = View.VISIBLE
                        }
                    }
                    Status.FAILED -> {
                        tv_error.text = context.getString(R.string.text_something_went_wrong)
                        tv_error.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }
            setOnClickListener { retryListener() }
        }

    }

    companion object {
        const val VIEW_EMPTY = R.layout.row_loader
        const val VIEW_ITEM = R.layout.row_cell
    }

}