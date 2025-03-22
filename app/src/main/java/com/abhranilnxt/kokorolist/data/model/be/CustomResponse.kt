package com.abhranilnxt.kokorolist.data.model.be

import com.google.gson.annotations.SerializedName

data class CustomResponse<T>(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T?
)
