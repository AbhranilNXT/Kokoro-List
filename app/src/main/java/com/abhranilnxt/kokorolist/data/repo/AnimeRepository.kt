package com.abhranilnxt.kokorolist.data.repo

import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.model.main.JikanApi
import com.abhranilnxt.kokorolist.data.model.details.Details
import com.abhranilnxt.kokorolist.data.remote.AnimeApi
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api: AnimeApi) {
    suspend fun getAnime(searchQuery : String): UiState<JikanApi> {
        val response = api.getAllAnime(searchQuery)
        if(response.isSuccessful)
            return UiState.Success(data = response.body()!!)
        else return UiState.Error(message = "Error")
    }

    suspend fun getAnimeInfo(id: Int) : UiState<Details> {
        val response = api.getAnimeInfo(animeID = id)
        if(response.isSuccessful)
            return UiState.Success(data = response.body()!!)
        else return UiState.Error(message = "Error")
    }
}