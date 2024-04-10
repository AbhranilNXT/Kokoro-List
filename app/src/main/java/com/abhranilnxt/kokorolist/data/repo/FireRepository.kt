package com.abhranilnxt.kokorolist.data.repo

import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.data.utils.UiState
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