package com.example.internshiptask.view.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeContent(navController: NavController) {

    val currentUserName = if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    else "N/A"

    val listOfAnime = listOf(
        MAnime("saa11w", "Jujutsu Kaisen", "Mappa", null),
        MAnime("saa11w", "Kaisen", "Mappa", null),
        MAnime("saa11w", "Jujutsu", "Mappa", null),
        MAnime("saa11w", "Jujutsu Kaisen", "Mappa", null),
        MAnime("saa11w", "Jujutsu Kaisen", "Mappa", null)
    )


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

        WatchingRightNowArea(anime = listOf(), navController = navController)
        
        TitleSection(label = "Reading List")

        BookListArea(listOfAnime = listOfAnime, navController)
    }
}

@Composable
fun BookListArea(listOfAnime: List<MAnime>,
                 navController: NavController) {
    HorizontalScrollableComponent(listOfAnime) {
        //TODO: Navigate to detail screen
    }
}

@Composable
fun HorizontalScrollableComponent(listOfAnime: List<MAnime>, onCardPressed: (String) -> Unit) {
    val scrollableState = rememberScrollState()
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollableState)) {
        for (anime in listOfAnime) {
            ListCard(anime) {
                onCardPressed(it)
            }
        }
    }
}
