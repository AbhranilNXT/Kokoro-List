package com.example.internshiptask.view.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.internshiptask.data.model.main.MAnime

@Composable
fun WatchingRightNowArea(anime: List<MAnime>, navController: NavController) {
    ListCard()
}