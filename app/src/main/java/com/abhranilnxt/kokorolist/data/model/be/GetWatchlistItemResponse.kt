package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class GetWatchlistItemResponse(
    @SerializedName("watchlistId")
    val watchlistId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("studio")
    val studio: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("genres")
    val genres: String,
    @SerializedName("episodes")
    val episodes: String,
    @SerializedName("mal_score")
    val malScore: Double,
    @SerializedName("status")
    val status: String,
    @SerializedName("personal_rating")
    val personalRating: Int,
    @SerializedName("started_watching")
    val startedWatching: String?,
    @SerializedName("finished_watching")
    val finishedWatching: String?,
    @SerializedName("notes")
    val notes: String?
)
