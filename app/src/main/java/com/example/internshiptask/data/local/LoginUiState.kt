package com.example.internshiptask.data.local

sealed interface LoginUiState<out T> {
    object Idle: LoginUiState<Nothing>
    object Loading: LoginUiState<Nothing>
    data class Success<out T>(val data: T): LoginUiState<T>
    data class Error(val message: String): LoginUiState<Nothing>

}