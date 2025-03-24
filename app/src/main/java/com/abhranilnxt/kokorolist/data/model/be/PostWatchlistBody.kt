package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class PostWatchlistBody(
    @SerializedName("personalRating")
    val personalRating: Int? = null,
    @SerializedName("startedWatching")
    val startedWatching: String? = null,
    @SerializedName("finishedWatching")
    val finishedWatching: String? = null,
    @SerializedName("notes")
    val notes: String? = null
)
