package com.abhranilnxt.kokorolist.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.model.details.Details
import com.abhranilnxt.kokorolist.data.repo.AnimeRepository
import com.google.firebase.FirebaseNetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: AnimeRepository) : ViewModel() {

    private val _animeInfo: MutableStateFlow<UiState<Details>> = MutableStateFlow(UiState.Idle)
    val animeInfo = _animeInfo.asStateFlow()

    fun animeInfo(animeId: Int) {
        getAnimeInfo(animeId = animeId)
    }

    private fun getAnimeInfo(animeId: Int) {
        _animeInfo.value = UiState.Loading

        viewModelScope.launch {
            try {
                _animeInfo.value = repository.getAnimeInfo(id = animeId)
            }catch (e: IOException) {
                Log.e("BackendViewModel", "Network error: ${e.message}")
                _animeInfo.value = UiState.Error("No internet connection or network error")
            } catch (e: FirebaseNetworkException) {
                Log.e("BackendViewModel", "Firebase Network error: ${e.message}")
                _animeInfo.value = UiState.Error("No internet connection or network error")
            } catch (e: Exception) {
                Log.e("BackendViewModel", "Unexpected error: ${e.message}")
                _animeInfo.value = UiState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}