package com.abhranilnxt.kokorolist.view.components.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.data.utils.formatDate
import com.abhranilnxt.kokorolist.data.utils.showToast
import com.abhranilnxt.kokorolist.view.components.core.RoundedButton
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ShowSimpleForm(anime: MAnime, navController: NavController) {

    val context = LocalContext.current

    val notesText = remember {
        mutableStateOf("")
    }

    val isStartedWatching = remember {
        mutableStateOf(false)
    }
    val isFinishedWatching = remember {
        mutableStateOf(false)
    }

    val ratingVal = remember {
        mutableStateOf(0)
    }

    SimpleForm(modifier = Modifier,
        defaultValue = if(anime.notes.toString().isNotEmpty()) anime.notes.toString()
        else "No comments available.") {note ->
        notesText.value = note
    }
    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        TextButton(onClick = {
            isStartedWatching.value = !isStartedWatching.value
        },
            enabled = anime.startedWatching == null) {
            if (anime.startedWatching == null) {
                if(!isStartedWatching.value)
                    Text(text = "Start Watching")
                else {
                    Text(
                        text = "Started Watching!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(0.5f)
                    )
                }
            }
            else {
                Text(text = "Started on: ${formatDate(anime.startedWatching!!)}")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = { isFinishedWatching.value = !isFinishedWatching.value },
            enabled = anime.finishedWatching == null && anime.startedWatching != null) {
            if (anime.finishedWatching == null) {
                if(!isFinishedWatching.value)
                    Text(text = "Mark as Watched")
                else {
                    Text(
                        text = "Finished Watching!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(0.5f)
                    )
                }
            } else {
                Text(text = "Finished on: ${formatDate(anime.finishedWatching!!)}")
            }
        }
    }
    Text(text = "Rating", modifier = Modifier.padding(bottom = 4.dp))

    anime.rating?.toInt().let {
        RatingBar(rating = it!!) {
            ratingVal.value = it
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    Row(horizontalArrangement = Arrangement.SpaceBetween) {

        val changedNotes = anime.notes != notesText.value
        val changedRating = anime.rating?.toInt() != ratingVal.value
        val isFinishedTimeStamp = if(isFinishedWatching.value) Timestamp.now() else anime.finishedWatching
        val isStartedTimeStamp = if(isStartedWatching.value) Timestamp.now() else anime.startedWatching
        val animeUpdate = changedNotes || changedRating || isFinishedWatching.value || isStartedWatching.value
        val animeToUpdate = hashMapOf(
            "finished_watching" to isFinishedTimeStamp,
            "started_watching" to isStartedTimeStamp,
            "notes" to notesText.value,
            "rating" to ratingVal.value
        ).toMap()

        RoundedButton(label = "Update") {
            if(animeUpdate) {
                FirebaseFirestore.getInstance()
                    .collection("anime")
                    .document(anime.id!!)
                    .update(animeToUpdate)
                    .addOnCompleteListener {
                        showToast(context, "Anime updated successfully!")
                        navController.navigate(KokoroListScreens.HomeScreen.route){
                            popUpTo(KokoroListScreens.UpdateScreen.route){
                                inclusive = true
                                saveState = true
                            }
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Error", "Failed to update anime")
                    }
            }
        }
        Spacer(modifier = Modifier.padding(100.dp))
        val openDialog = remember {
            mutableStateOf(false)
        }
        if(openDialog.value) {
            ShowAlertDialog(message = stringResource(id = R.string.sure) + "\n"+
            stringResource(id = R.string.action), openDialog) {
                FirebaseFirestore.getInstance().collection("anime")
                    .document(anime.id!!)
                    .delete()
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            openDialog.value = false
                            navController.navigate(KokoroListScreens.HomeScreen.route){
                                popUpTo(KokoroListScreens.UpdateScreen.route){
                                    inclusive = true
                                    saveState = true
                                }
                            }
                        }
                    }
            }
        }
        RoundedButton(label = "Delete") {
            openDialog.value = true
        }
    }
}