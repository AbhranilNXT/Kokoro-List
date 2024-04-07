package com.example.internshiptask.data.repo

import com.example.internshiptask.data.local.UiState
import com.example.internshiptask.data.model.main.Data
import com.example.internshiptask.data.model.main.JikanApi
import com.example.internshiptask.data.model.details.Details
import com.example.internshiptask.data.remote.AnimeApi
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