package com.abhranilnxt.kokorolist.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.model.main.JikanApi
import com.abhranilnxt.kokorolist.data.repo.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            }
            catch (e: Exception) {
                //Todo: Handle error
            }
        }
    }

}