package com.abhranilnxt.kokorolist.view.components.update

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.be.GetWatchlistItemResponse
import com.abhranilnxt.kokorolist.data.model.be.PostWatchlistBody
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.utils.formatDate
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.components.core.RoundedButton
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.BackendViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import java.time.LocalDateTime

@Composable
fun ShowSimpleForm(anime: GetWatchlistItemResponse, navController: NavController,
                   viewModel: BackendViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val updateWatchlistItemState = viewModel.updateWatchlistItemState.collectAsState().value
    val deleteWatchlistItemState = viewModel.deleteWatchlistItemState.collectAsState().value
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    var updateLoadingActive by remember { mutableStateOf(false) }
    var deleteLoadingActive by remember { mutableStateOf(false) }

//    val notesText = rememberSaveable {
//        mutableStateOf("")
//    }

    val isStartedWatching = remember {
        mutableStateOf(false)
    }
    val isFinishedWatching = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(updateWatchlistItemState) {
        when(updateWatchlistItemState) {
            is UiState.Success -> {
                Toast.makeText(context, "Watchlist entry updated!", Toast.LENGTH_SHORT).show()
                navController.navigate(KokoroListScreens.HomeScreen.route) {
                    popUpTo(KokoroListScreens.UpdateScreen.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(context, updateWatchlistItemState.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.Loading -> {
                updateLoadingActive = true
            }
            else -> Unit
        }
    }

    LaunchedEffect(deleteWatchlistItemState) {
        when(deleteWatchlistItemState) {
            is UiState.Success -> {
                Toast.makeText(context, "Watchlist entry deleted!", Toast.LENGTH_SHORT).show()
                navController.navigate(KokoroListScreens.HomeScreen.route) {
                    popUpTo(KokoroListScreens.UpdateScreen.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(context, deleteWatchlistItemState.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.Loading -> {
                deleteLoadingActive = true
            }
            else -> Unit
        }
    }

//    SimpleForm(modifier = Modifier,
//        defaultValue = if(!anime.notes.isNullOrEmpty())
//            anime.notes.toString()
//        else "No notes available.") {note ->
//        notesText.value = note
//    }

    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {

        TextButton(onClick = {
            isStartedWatching.value = !isStartedWatching.value
        },
            colors = ButtonDefaults.textButtonColors(contentColor = highlightColor,
                disabledContentColor = highlightColor.copy(0.6f)),
            enabled = anime.startedWatching == null) {
            if (anime.startedWatching == null) {
                if(!isStartedWatching.value)
                    Text(text = "Start Watching",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal)
                else {
                    Text(
                        text = "Started Watching!",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
            }
            else {
                Text(text = "Started on: ${formatDate(anime.startedWatching) }",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = { isFinishedWatching.value = !isFinishedWatching.value },
            colors = ButtonDefaults.textButtonColors(contentColor = highlightColor,
                disabledContentColor = highlightColor.copy(0.6f)),
            enabled = anime.finishedWatching == null && anime.startedWatching != null) {
            if (anime.finishedWatching == null) {
                if(!isFinishedWatching.value)
                    Text(text = "Mark as Watched",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal)
                else {
                    Text(
                        text = "Finished Watching!",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
            } else {
                Text(text = "Finished on: ${formatDate(anime.finishedWatching)}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.White)
            }
        }
    }
    Text(text = "Rating",
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 4.dp))

    val ratingVal = remember {
        mutableStateOf(anime.personalRating)
    }

    anime.personalRating.toInt().let {
        RatingBar(rating = it) {
            ratingVal.value = it
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    Row(horizontalArrangement = Arrangement.SpaceBetween) {

//        val changedNotes = anime.notes != notesText.value
        val changedRating = anime.personalRating != ratingVal.value
        val isFinishedTimeStamp = if(isFinishedWatching.value) LocalDateTime.now() else null
        val isStartedTimeStamp = if(isStartedWatching.value) LocalDateTime.now() else null
        val animeUpdate = changedRating || isFinishedWatching.value || isStartedWatching.value

        val watchlistUpdate = PostWatchlistBody(
            personalRating = ratingVal.value,
            startedWatching = if(isStartedWatching.value) isStartedTimeStamp.toString() else null,
            finishedWatching = if(isFinishedWatching.value) isFinishedTimeStamp.toString() else null
        )

        RoundedButton(label = "Update") {
            if(animeUpdate) {
                viewModel.updateWatchlistItem(
                    anime.watchlistId,
                    watchlistUpdate
                )
            }
            else Toast.makeText(context, "Nothing to update!", Toast.LENGTH_SHORT).show()
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.4f))
        val openDialog = remember {
            mutableStateOf(false)
        }
        if(openDialog.value) {
            ShowAlertDialog(message = stringResource(id = R.string.sure) + "\n"+
            stringResource(id = R.string.action), openDialog) {
                viewModel.deleteWatchlistItem(
                    anime.watchlistId
                )
            }
        }
        RoundedButton(label = "Delete") {
            openDialog.value = true
        }
    }
    if(updateLoadingActive || deleteLoadingActive){
        LottieAnimation(
            composition = loadingLottie,
            modifier = Modifier.size(180.dp),
            contentScale = ContentScale.Fit,
            iterations = LottieConstants.IterateForever
        )
        Text(
            text = "Updating your watchlist...",
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}