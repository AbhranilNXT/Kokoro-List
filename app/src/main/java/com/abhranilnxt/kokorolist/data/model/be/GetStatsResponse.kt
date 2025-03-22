package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class GetStatsResponse(
    @SerializedName("currentlyWatchingCount")
    val currentlyWatchingCount: Int,
    @SerializedName("finishedWatchingCount")
    val finishedWatchingCount: Int,
    @SerializedName("finishedAnimeList")
    val finishedAnimeList: List<StatsAnime>
)
