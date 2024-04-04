package com.example.internshiptask.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptask.data.model.DataUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailPass(email: String, password: String, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("FB", "signInWithEmailAndPass: Success ${task.result}")
                        home()
                    } else {
                        Log.d("FB", "signInWithEmailAndPass: ${task.result}")
                    }
                }
        }
        catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPass: ${ex.message}")
        }
    }

    fun createUserWithEmailPass(email: String, password: String, home: () -> Unit) {
        if(_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if( task.isSuccessful) {
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                        addUserToFirestore(displayName)
                        home()
                    }
                    else {
                        Log.d("FB", "createUserWithEmailPass: ${task.result}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun addUserToFirestore(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = DataUser(userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "I Love Kotlin",
            profession = "Android Developer", id = null).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)

//        FirebaseDatabase.getInstance().getReference("users")
//            .setValue("Hello World")
    }
}