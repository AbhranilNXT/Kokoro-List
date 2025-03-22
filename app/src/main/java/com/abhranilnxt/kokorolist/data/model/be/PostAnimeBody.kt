package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class PostAnimeBody(
    @SerializedName("malId")
    val malId: Int,
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
    @SerializedName("malScore")
    val malScore: Double,
    @SerializedName("status")
    val status: String
)
