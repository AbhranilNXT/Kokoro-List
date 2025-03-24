package com.abhranilnxt.kokorolist.data.repo

import android.util.Log
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.model.main.JikanApi
import com.abhranilnxt.kokorolist.data.model.details.Details
import com.abhranilnxt.kokorolist.data.remote.AnimeApi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api: AnimeApi) {
    suspend fun getAnime(searchQuery : String): UiState<JikanApi> {
        val response = api.getAllAnime(searchQuery)
        try {
            if(response.isSuccessful){
                return UiState.Success(data = response.body()!!)
                Log.d("AnimeRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
            else{
                return UiState.Error(message = "Error")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun getAnimeInfo(id: Int) : UiState<Details> {
        val response = api.getAnimeInfo(animeID = id)
        try {
            if(response.isSuccessful){
                Log.d("AnimeRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            }
            else{
                return UiState.Error(message = "Error")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }
}