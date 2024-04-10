package com.abhranilnxt.kokorolist.view.components.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens

@Composable
fun WatchingRightNowArea(anime: List<MAnime>, navController: NavController) {
    val watchingNowList = anime.filter {
        it.startedWatching != null && it.finishedWatching == null
    }
    HorizontalScrollableComponent(watchingNowList) {
        navController.navigate(KokoroListScreens.UpdateScreen.route +"/$it")
    }
}