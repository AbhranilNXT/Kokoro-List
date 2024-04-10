package com.abhranilnxt.kokorolist.data.remote

import com.abhranilnxt.kokorolist.data.model.main.JikanApi
import com.abhranilnxt.kokorolist.data.model.details.Details
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface AnimeApi {
    @GET(value = "anime")
    suspend fun getAllAnime(
        @Query("q") query: String) : Response<JikanApi>

    @GET("anime/{animeID}")
    suspend fun getAnimeInfo(@Path("animeID") animeID: Int) : Response<Details>
}