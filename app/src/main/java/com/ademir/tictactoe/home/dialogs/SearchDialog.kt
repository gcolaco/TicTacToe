package com.ademir.tictactoe.home.dialogs

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.prepare
import com.ademir.tictactoe.data.SearchApi
import com.ademir.tictactoe.data.models.NetworkState
import com.ademir.tictactoe.home.adapters.SearchResultAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_search.*
import java.util.concurrent.TimeUnit

/**
 * Created by ademir on 25/03/18.
 */
class SearchDialog : DialogFragment() {

    private val TAG = SearchDialog::class.java.simpleName

    private lateinit var searchApi: SearchApi
    private lateinit var adapter: SearchResultAdapter
    private var disposable: Disposable? = null
    private val networkState = MutableLiveData<NetworkState>().apply {
        value = NetworkState.LOADED
    }
    private var imageSelectedListener: (String) -> Unit = { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchResultAdapter(
                {
                    imageSelectedListener(it)
                    dismissAllowingStateLoss()
                },
                { fetchImages(et_search.text.toString()) }
        )

        val layoutManager = object : GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false) {
            init {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        when {
                            adapter.getItemViewType(position) == SearchResultAdapter.VIEW_EMPTY -> {
                                return spanCount
                            }
                            else -> {
                                return 1
                            }
                        }
                    }
                }
            }
        }

        rv_search.prepare(adapter, layoutManager)

        networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        RxTextView.afterTextChangeEvents(et_search)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    fetchImages(it.editable().toString())
                }

        et_search.setOnEditorActionListener { tv, _, _ ->
            fetchImages(tv.text.toString())
            true
        }

    }

    override fun onResume() {
        val params = dialog.window.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window.attributes = params
        super.onResume()
    }

    private fun fetchImages(query: String): LiveData<NetworkState> {

        adapter.clear()

        if (query.isEmpty()) {
            networkState.value = NetworkState.LOADED
        } else {
            networkState.value = NetworkState.LOADING

            Log.i(TAG, "Dispatching search request for query: '$query'")

            disposable = searchApi.fetchImages(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                adapter.setData(it.items.map { it.image.thumb })
                                networkState.value = NetworkState.LOADED
                            },
                            {
                                Log.e(TAG, "Error while fetching images", it)
                                networkState.value = NetworkState.error(getString(R.string.error_search))
                            }
                    )
        }

        return networkState
    }

    fun show(fm: FragmentManager) = super.show(fm, "search_fragment")

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        fun newInstance(searchApi: SearchApi, action: (String) -> Unit): SearchDialog {
            val dialog = SearchDialog()
            dialog.imageSelectedListener = action
            dialog.searchApi = searchApi
            return dialog
        }
    }

}