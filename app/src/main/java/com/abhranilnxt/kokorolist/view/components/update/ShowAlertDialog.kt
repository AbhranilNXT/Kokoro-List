package com.abhranilnxt.kokorolist.view.components.update

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ShowAlertDialog(
    message: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit
) {
    if(openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            confirmButton = {
                            TextButton(onClick = { onYesPressed.invoke() }) {
                                Text(text = "Yes")
                            }
            },
            title = { Text(text = "Delete Anime")},
            text = { Text(text = message)},
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(text = "No")
                }
            })
    }
}