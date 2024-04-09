package com.example.internshiptask.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.data.repo.FireRepository
import com.example.internshiptask.data.utils.UiState
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