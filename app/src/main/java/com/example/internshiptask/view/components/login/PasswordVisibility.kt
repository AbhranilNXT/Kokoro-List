package com.example.internshiptask.view.components.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        if (visible) Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = "toggle visibility off")
        else Icon(imageVector = Icons.Default.Visibility, contentDescription = "toggle visibility on")
    }
}