package com.abhranilnxt.kokorolist.data.utils

import android.content.Context
import android.widget.Toast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDate(dateTimeString: String, outputPattern: String = "dd MMM yyyy"): String {
    return try {
        val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern(outputPattern)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        "Invalid date format"
    }
}

fun showToast(context: Context,
              message: String) {
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}