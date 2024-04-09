package com.example.internshiptask.view.components.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.view.components.home.HorizontalScrollableComponent

@Composable
fun WatchingRightNowArea(anime: List<MAnime>, navController: NavController) {
    HorizontalScrollableComponent(anime) {}
//    ListCard()
}