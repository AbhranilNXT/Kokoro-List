package com.example.internshiptask.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptask.data.utils.UiState
import com.example.internshiptask.data.model.details.Details
import com.example.internshiptask.data.repo.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            }
            catch (e: Exception) {
                //Todo: Handle error
            }
        }
    }
}