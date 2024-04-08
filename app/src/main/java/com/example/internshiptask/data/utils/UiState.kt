package com.example.internshiptask.data.utils

sealed interface UiState<out T> {
    object Idle : UiState<Nothing>
    object Loading : UiState<Nothing>
    class Success<T>(val data: T) : UiState<T>
    class Error(val message: String): UiState<Nothing>
}