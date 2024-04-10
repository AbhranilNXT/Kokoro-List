package com.abhranilnxt.kokorolist.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.data.repo.FireRepository
import com.abhranilnxt.kokorolist.data.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository): ViewModel() {
    private val _data: MutableStateFlow<UiState<List<MAnime>>> = MutableStateFlow(UiState.Idle)
    val data = _data.asStateFlow()

    fun getAllAnime() {
        getAllAnimeFromFirestore()
    }

    private fun getAllAnimeFromFirestore() {
        _data.value = UiState.Loading

        viewModelScope.launch {
            try {
                _data.value = repository.getAllAnimeFromFireStore()
            }
            catch (e: Exception) {
                //Todo: Handle error
            }
        }
    }
}