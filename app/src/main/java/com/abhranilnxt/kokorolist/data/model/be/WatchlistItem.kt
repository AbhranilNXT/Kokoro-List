package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class WatchlistItem(
    @SerializedName("watchlistId")
    val watchlistId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("studio")
    val studio: String,
    @SerializedName("personalRating")
    val personalRating: Int,
    @SerializedName("startedWatching")
    val startedWatching: String?,
    @SerializedName("finishedWatching")
    val finishedWatching: String?
)
