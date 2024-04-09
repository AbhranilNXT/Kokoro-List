package com.example.internshiptask.view.components.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.view.navigation.InternshipTaskScreens

@Composable
fun WatchingRightNowArea(anime: List<MAnime>, navController: NavController) {
    val watchingNowList = anime.filter {
        it.startedWatching != null && it.finishedWatching == null
    }
    HorizontalScrollableComponent(watchingNowList) {
        navController.navigate(InternshipTaskScreens.UpdateScreen.route +"/$it")
    }
}