package com.abhranilnxt.kokorolist.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.model.main.JikanApi
import com.abhranilnxt.kokorolist.data.repo.AnimeRepository
import com.google.firebase.FirebaseNetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(private val repository: AnimeRepository): ViewModel() {

    private val _listOfAnime: MutableStateFlow<UiState<JikanApi>> = MutableStateFlow(UiState.Idle)
    val listOfAnime = _listOfAnime.asStateFlow()

    fun loadAnime(query: String){
        searchAnime(query)
    }
    private fun searchAnime(query: String) {
        _listOfAnime.value = UiState.Loading

        viewModelScope.launch {
            try {
                _listOfAnime.value = repository.getAnime(searchQuery = query)
            }catch (e: IOException) {
                Log.e("BackendViewModel", "Network error: ${e.message}")
                _listOfAnime.value = UiState.Error("No internet connection or network error")
            } catch (e: FirebaseNetworkException) {
                Log.e("BackendViewModel", "Firebase Network error: ${e.message}")
                _listOfAnime.value = UiState.Error("No internet connection or network error")
            } catch (e: Exception) {
                Log.e("BackendViewModel", "Unexpected error: ${e.message}")
                _listOfAnime.value = UiState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

}