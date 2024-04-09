package com.example.internshiptask.data.repo

import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.data.utils.UiState
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryAnime: Query)  {

    suspend fun getAllAnimeFromFireStore(): UiState<List<MAnime>> {
        val response = queryAnime.get().await()
        return if (response.isEmpty) {
            UiState.Error(message = "Firestore Loading Error")
        } else {
            UiState.Success(response.documents.map {
                it.toObject(MAnime::class.java)!!
            })
        }
    }
}