package com.abhranilnxt.kokorolist.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhranilnxt.kokorolist.data.model.be.CustomResponse
import com.abhranilnxt.kokorolist.data.model.be.GetStatsResponse
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistItemResponse
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistResponse
import com.abhranilnxt.kokorolist.data.model.be.PostAnimeBody
import com.abhranilnxt.kokorolist.data.model.be.PostWatchlistBody
import com.abhranilnxt.kokorolist.data.repo.BackendRepository
import com.abhranilnxt.kokorolist.data.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackendViewModel @Inject constructor(private val repository: BackendRepository): ViewModel() {

    private val _verifyTokenState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val verifyTokenState = _verifyTokenState.asStateFlow()

    fun resetState() {
        _verifyTokenState.value = UiState.Idle
    }

    fun loginUser(token: String) {
        verifyToken(token)
    }

    private fun verifyToken(token: String) {
        _verifyTokenState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _verifyTokenState.value = repository.loginUser(token)
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }

    private val _addAnimeToWatchlistState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val addAnimeToWatchlistState = _addAnimeToWatchlistState.asStateFlow()

    fun addAnimeToWatchlist(postAnimeBody: PostAnimeBody) {
        addAnime(postAnimeBody)
    }

    private fun addAnime(postAnimeBody: PostAnimeBody) {
        _addAnimeToWatchlistState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _addAnimeToWatchlistState.value = repository.addAnimeToWatchlist(repository.getIdToken(), postAnimeBody)
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }

    private val _getWatchlistItemsState: MutableStateFlow<UiState<CustomResponse<GetWatchlistResponse>>> =
        MutableStateFlow(UiState.Idle)
    val getWatchlistItemsState = _getWatchlistItemsState.asStateFlow()

    fun getWatchlistItems() {
        getList()
    }

    private fun getList() {
        _getWatchlistItemsState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _getWatchlistItemsState.value = repository.getWatchlistItems(repository.getIdToken())
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }

    private val _getWatchlistItemState: MutableStateFlow<UiState<CustomResponse<GetWatchlistItemResponse>>> =
        MutableStateFlow(UiState.Idle)
    val getWatchlistItemState = _getWatchlistItemState.asStateFlow()

    fun getWatchlistItem(id: String) {
        getItem(id)
    }

    private fun getItem(id: String) {
        _getWatchlistItemState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _getWatchlistItemState.value =
                    repository.getWatchlistItem(repository.getIdToken(), id)
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }

    private val _updateWatchlistItemState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val updateWatchlistItemState = _updateWatchlistItemState.asStateFlow()

    fun updateWatchlistItem(id: String, postWatchlistBody: PostWatchlistBody) {
        updateWatchlist(id, postWatchlistBody)
    }

    private fun updateWatchlist(id: String, postWatchlistBody: PostWatchlistBody) {
        _updateWatchlistItemState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _updateWatchlistItemState.value =
                    repository.updateWatchlistItem(repository.getIdToken(), id, postWatchlistBody)
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }

    private val _getUserStatsState: MutableStateFlow<UiState<CustomResponse<GetStatsResponse>>> =
        MutableStateFlow(UiState.Idle)
    val getUserStatsState = _getUserStatsState.asStateFlow()

    fun getUserStats() {
        getStats()
    }

    private fun getStats() {
        _getUserStatsState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _getUserStatsState.value = repository.getUserStats(repository.getIdToken())
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }

    private val _deleteWatchlistItemState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val deleteWatchlistItemState = _deleteWatchlistItemState.asStateFlow()

    fun deleteWatchlistItem(id: String) {
        deleteWatchlist(id)
    }

    private fun deleteWatchlist(id: String) {
        _deleteWatchlistItemState.value = UiState.Loading

        viewModelScope.launch {
            try {
                _deleteWatchlistItemState.value = repository.deleteWatchlistItem(repository.getIdToken(), id)
            } catch (e: Exception) {
                Log.e("BackendViewModel", e.message.toString())
            }
        }
    }
}