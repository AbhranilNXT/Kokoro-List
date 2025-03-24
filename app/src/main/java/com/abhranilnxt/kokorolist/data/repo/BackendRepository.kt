package com.abhranilnxt.kokorolist.data.repo

import android.util.Log
import com.abhranilnxt.kokorolist.data.model.be.CustomResponse
import com.abhranilnxt.kokorolist.data.model.be.GetStatsResponse
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistItemResponse
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistResponse
import com.abhranilnxt.kokorolist.data.model.be.PostAnimeBody
import com.abhranilnxt.kokorolist.data.model.be.PostWatchlistBody
import com.abhranilnxt.kokorolist.data.remote.BackendApi
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class BackendRepository @Inject constructor(private val api: BackendApi,
    private val auth: FirebaseAuth) {
    suspend fun loginUser(token: String): UiState<CustomResponse<Unit>> {
        val response = api.loginUser(token)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun getIdToken(): String {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val idTokenResult = currentUser.getIdToken(true).await()
                val token = idTokenResult?.token ?: "No Token Found"
                Log.d("AuthRepo", token)
                "Bearer $token"
            } else {
                "No Token Found"
            }
        }catch (e: FirebaseNetworkException) {
            throw IOException("No internet connection or network error", e)
        }
        catch (e: Exception) {
            throw Exception("Error getting token: ${e.message}", e)
        }
    }

    suspend fun addAnimeToWatchlist(token: String, postAnimeBody: PostAnimeBody): UiState<CustomResponse<Unit>> {
        val response = api.addAnimeToWatchlist(token, postAnimeBody)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            }
            else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun getWatchlistItems(token: String): UiState<CustomResponse<GetWatchlistResponse>> {
        val response = api.getWatchlistItems(token)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun getWatchlistItem(token: String, id: String): UiState<CustomResponse<GetWatchlistItemResponse>> {
        val response = api.getWatchlistItem(token, id)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun updateWatchlistItem(token: String, id: String, postWatchlistBody: PostWatchlistBody): UiState<CustomResponse<Unit>> {
        val response = api.updateWatchlistItem(token, id, postWatchlistBody)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun getUserStats(token: String): UiState<CustomResponse<GetStatsResponse>> {
        val response = api.getUserStats(token)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun deleteWatchlistItem(token: String, id: String): UiState<CustomResponse<Unit>> {
        val response = api.deleteWatchlistItem(token, id)
        try {
            if (response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }

    suspend fun deleteUser(token: String, id: String): UiState<CustomResponse<Unit>> {
        val response = api.deleteUser(token, id)
        try {
            if(response.isSuccessful) {
                Log.d("BackendRepository", "HTTP ${response.code()} - ${response.errorBody()?.string()}")
                return UiState.Success(data = response.body()!!)
            } else {
                return UiState.Error(message = "HTTP ${response.code()} - ${response.errorBody()?.string()}")
            }
        }catch (e: IOException) {
            return (UiState.Error("No internet connection or network error"))
        } catch (e: HttpException) {
            return (UiState.Error("HTTP error: ${e.message ?: "Internal Server Error"}"))
        } catch (e: Exception) {
            return (UiState.Error("Unexpected error: ${e.message ?: "An unexpected error occurred"}"))
        }
    }
}