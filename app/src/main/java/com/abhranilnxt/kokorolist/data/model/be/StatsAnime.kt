package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class StatsAnime(
    @SerializedName("watchlistId")
    val watchlistId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("studio")
    val studio: String,
    @SerializedName("genres")
    val genres: String,
    @SerializedName("malScore")
    val malScore: Double,
    @SerializedName("startedWatching")
    val startedWatching: String,
    @SerializedName("finishedWatching")
    val finishedWatching: String
)
