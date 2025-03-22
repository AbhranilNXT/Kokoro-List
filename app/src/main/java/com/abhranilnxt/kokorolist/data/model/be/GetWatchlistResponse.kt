package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class GetWatchlistResponse(
    @SerializedName("plan_to_watch")
    val planToWatch: List<WatchlistItem>,
    @SerializedName("currently_watching")
    val currentlyWatching: List<WatchlistItem>
)
