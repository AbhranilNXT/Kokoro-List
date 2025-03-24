package com.abhranilnxt.kokorolist.data.remote

import com.abhranilnxt.kokorolist.data.model.be.CustomResponse
import com.abhranilnxt.kokorolist.data.model.be.GetStatsResponse
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistItemResponse
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistResponse
import com.abhranilnxt.kokorolist.data.model.be.PostAnimeBody
import com.abhranilnxt.kokorolist.data.model.be.PostWatchlistBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendApi {

    @POST("auth/login")
    suspend fun loginUser(
        @Header("Authorization") token: String
    ) : Response<CustomResponse<Unit>>

    @POST("anime/add")
    suspend fun addAnimeToWatchlist(
        @Header("Authorization") token: String,
        @Body postAnimeBody: PostAnimeBody
    ) : Response<CustomResponse<Unit>>

    @GET("watchlist/items")
    suspend fun getWatchlistItems(
        @Header("Authorization") token: String
    ) : Response<CustomResponse<GetWatchlistResponse>>

    @GET("watchlist/item/{id}")
    suspend fun getWatchlistItem(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<CustomResponse<GetWatchlistItemResponse>>

    @PATCH("watchlist/item/{id}")
    suspend fun updateWatchlistItem(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body postWatchlistBody: PostWatchlistBody
    ) : Response<CustomResponse<Unit>>

    @GET("anime/stats")
    suspend fun getUserStats(
        @Header("Authorization") token: String
    ) : Response<CustomResponse<GetStatsResponse>>

    @DELETE("watchlist/item/{id}")
    suspend fun deleteWatchlistItem(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<CustomResponse<Unit>>

    @DELETE("auth/delete/{id}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<CustomResponse<Unit>>
}