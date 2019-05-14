package com.ademir.tictactoe.data

import com.ademir.tictactoe.commons.Constants
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by ademir on 25/03/18.
 */
object SearchApi {

    private val rxJavaAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    private val api = Retrofit.Builder()
            .baseUrl(Constants.Urls.SEARCH_ENGINE)
            .client(OkHttpClient())
            .addCallAdapterFactory(rxJavaAdapter)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SearchApiInterface::class.java)

    fun fetchImages(query: String) = api.fetchImages(query)

}