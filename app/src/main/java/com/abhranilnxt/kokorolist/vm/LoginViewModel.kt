package com.abhranilnxt.kokorolist.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhranilnxt.kokorolist.data.model.main.DataUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailPass(email: String, password: String,error: (String) -> Unit, home: () -> Unit) = viewModelScope.launch {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    home()
                }
            }
            .addOnFailureListener {
                Log.d("FB", "signInWithEmailAndPass: ${it.localizedMessage}")
                it.localizedMessage?.let { it1 -> error(it1) }
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
        val user = DataUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "I Love Kotlin",
            profession = "Android Developer", id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)

//        FirebaseDatabase.getInstance().getReference("users")
//            .setValue("Hello World")
    }
}