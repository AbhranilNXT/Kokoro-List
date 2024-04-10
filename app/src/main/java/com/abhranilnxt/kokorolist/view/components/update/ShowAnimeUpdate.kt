package com.abhranilnxt.kokorolist.view.components.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhranilnxt.kokorolist.data.model.main.MAnime

@Composable
fun ShowAnimeUpdate(animeInfo: List<MAnime>, animeItemId: String) {
    Row(horizontalArrangement = Arrangement.Center) {
        Column(modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center) {
            CardListItem(anime = animeInfo.first {
                it.malId.toString() == animeItemId
            }, onPressDetails = {})
        }
    }
}