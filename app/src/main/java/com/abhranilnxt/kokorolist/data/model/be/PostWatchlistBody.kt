package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class PostWatchlistBody(
    @SerializedName("personal_rating")
    val personalRating: Int?,
    @SerializedName("started_watching")
    val startedWatching: String?,
    @SerializedName("finished_watching")
    val finishedWatching: String?,
    @SerializedName("notes")
    val notes: String?
)
