package com.example.internshiptask.view.components.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.data.utils.UiState
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.example.internshiptask.vm.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {

    val currentUserName = if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    else "N/A"

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


    Column(modifier = Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {

        Row {
            TitleSection(label = "Your watching\n"+"activity right now..")

            Column {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile Icon",
                    Modifier
                        .clickable {
                            navController.navigate(InternshipTaskScreens.StatsScreen.route)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.secondary)
                Text(text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                HorizontalDivider(Modifier.padding(end = 27.dp))
            }

        }

        WatchingRightNowArea(anime = listOfAnime, navController = navController)
        
        TitleSection(label = "Watching List")

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
        navController.navigate(InternshipTaskScreens.UpdateScreen.route +"/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(listOfAnime: List<MAnime>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit) {
    val scrollableState = rememberScrollState()
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollableState)) {

        if(listOfAnime.isNullOrEmpty()) {
            Surface(modifier = Modifier.padding(23.dp)) {
                Text(text = "No Anime Found. Add an Anime",
                    style = TextStyle(
                        color = Color.Red.copy(0.4f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
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
