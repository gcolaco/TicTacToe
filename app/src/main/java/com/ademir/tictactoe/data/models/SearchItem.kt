package com.ademir.tictactoe.data.models

import com.squareup.moshi.Json

/**
 * Created by ademir on 25/03/18.
 */
class SearchItem(val image: SearchItem.Image) {

    class Image(@Json(name = "thumbnailLink") val thumb: String)

    class Payload(val items: List<SearchItem>)

}