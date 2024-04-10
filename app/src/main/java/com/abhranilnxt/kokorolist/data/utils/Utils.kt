package com.abhranilnxt.kokorolist.data.utils

import android.content.Context
import android.icu.text.DateFormat
import android.widget.Toast
import com.google.firebase.Timestamp

fun formatDate(timestamp: Timestamp): String {
    val date = DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString().split(",")[0]
    return date
}

fun showToast(context: Context,
              message: String) {
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}