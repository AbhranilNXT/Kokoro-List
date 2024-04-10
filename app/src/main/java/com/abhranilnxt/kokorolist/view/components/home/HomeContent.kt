package com.abhranilnxt.kokorolist.view.components.home

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {

    var listOfAnime = emptyList<MAnime>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    val animeData = viewModel.data.collectAsState().value
    when(animeData)
    {
        is UiState.Idle -> {
            viewModel.getAllAnime()
        }
        is UiState.Loading -> {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        is UiState.Success -> {
            listOfAnime = animeData.data.toList().filter {
                it.userId == currentUser?.uid.toString()
            }
            Log.d("Anime" ,"Home Content: ${listOfAnime}")
        }
        else -> {}
    }


    Column(modifier = Modifier.padding(top = 100.dp, bottom = 2.dp, start = 16.dp, end = 2.dp),
        verticalArrangement = Arrangement.Top) {

        TitleSection(label = "Currently Watching...")

        WatchingRightNowArea(anime = listOfAnime, navController = navController)
        
        Spacer(modifier = Modifier.height(30.dp))
        
        TitleSection(label = "Plan to Watch")

        AnimeListArea(listOfAnime = listOfAnime, navController)
    }
}

@Composable
fun AnimeListArea(listOfAnime: List<MAnime>,
                  navController: NavController) {

    val addedAnime = listOfAnime.filter { mAnime->
        mAnime.startedWatching == null && mAnime.finishedWatching == null
    }

    HorizontalScrollableComponent(addedAnime) {
        navController.navigate(KokoroListScreens.UpdateScreen.route +"/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(listOfAnime: List<MAnime>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit) {
    val scrollableState = rememberScrollState()
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(320.dp)
            .horizontalScroll(scrollableState)) {

        if(listOfAnime.isNullOrEmpty()) {
            Surface(modifier = Modifier.padding(start = 32.dp,top = 32.dp), color = Color.Transparent) {
                Text(text = "No Anime Found.\nAdd an Anime",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        } else {
            for (anime in listOfAnime) {
                ListCard(anime) {
                    onCardPressed(anime.malId.toString())
                }
            }
        }
    }
}
