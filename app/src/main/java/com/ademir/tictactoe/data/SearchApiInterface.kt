package com.ademir.tictactoe.data

import com.ademir.tictactoe.BuildConfig
import com.ademir.tictactoe.commons.Constants
import com.ademir.tictactoe.data.models.SearchItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ademir on 25/03/18.
 */
interface SearchApiInterface {

    @GET(Constants.Urls.SEARCH_ENGINE)
    fun fetchImages(@Query("q") query: String,
                    @Query("key") key: String = BuildConfig.SEARCH_ENGINE_KEY,
                    @Query("cx") cx: String = BuildConfig.SEARCH_ENGINE_CX,
                    @Query("searchType") searchType: String = "image",
                    @Query("imgSize") imgSize: String = "small"): Single<SearchItem.Payload>

}