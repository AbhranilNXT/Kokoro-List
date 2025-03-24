package com.abhranilnxt.kokorolist.view.screens


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.be.StatsAnime
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.ui.theme.primaryColor
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.core.RoundedButton
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage
import com.abhranilnxt.kokorolist.view.components.update.ShowAlertDialog
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.BackendViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@Composable
fun StatsScreen(navController: NavController, viewModel: BackendViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet_anim))
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val notFoundLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.not_found_anim))
    var loadingActive by remember { mutableStateOf(false) }

    val deleteUserState = viewModel.deleteUserState.collectAsState().value

    LaunchedEffect(deleteUserState) {
        when(deleteUserState) {
            is UiState.Success -> {
                Toast.makeText(context, "Account Deleted Successfully!", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(KokoroListScreens.LoginScreen.route){
                        popUpTo(KokoroListScreens.HomeScreen.route){
                            inclusive = true
                            saveState = true
                        }
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(context, deleteUserState.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.Loading -> {
                loadingActive = true
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            AppBar(title = "Your Stats",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController) {
                navController.popBackStack()
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            shape = RectangleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Surface(modifier = Modifier.padding(it)
            .then(if (loadingActive) Modifier.blur(12.dp) else Modifier),
            color = Color.Transparent) {
            val userStats = viewModel.getUserStatsState.collectAsState().value
            when(userStats)
            {
                is UiState.Idle -> {
                    viewModel.getUserStats()
                }
                is UiState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimation(
                            composition = loadingLottie,
                            modifier = Modifier.size(220.dp),
                            contentScale = ContentScale.Fit,
                            iterations = LottieConstants.IterateForever
                        )
                        Text(
                            text = "Fetching your stats...",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
                is UiState.Success -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Box(modifier = Modifier
                                .size(45.dp)
                                .padding(start = 16.dp)) {
                                Icon(imageVector = Icons.Sharp.Person,
                                    tint = highlightColor,
                                    contentDescription = "icon")
                            }
                            Text(text = "Hi, ${
                                currentUser?.email.toString().split("@")[0]
                                    .uppercase(Locale.ROOT)}",
                                color = highlightColor,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold)
                        }
                        OutlinedCard(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
                            colors = CardDefaults.cardColors(primaryColor),
                            border = BorderStroke(1.dp, highlightColor),
                            shape = RoundedCornerShape(24.dp),
                            elevation = CardDefaults.cardElevation(5.dp)) {

                            Column(modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Your Stats",
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineSmall)

                                HorizontalDivider(color = highlightColor,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(2.dp))

                                Text(text = "You're watching: ${userStats.data.data!!.currentlyWatchingCount} anime",
                                    fontFamily = poppinsFamily,
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White)
                                Text(text = "You've watched: ${userStats.data.data.finishedWatchingCount} anime",
                                    fontFamily = poppinsFamily,
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            val logOutDialog = remember {
                                mutableStateOf(false)
                            }
                            val deleteDialog = remember {
                                mutableStateOf(false)
                            }
                            RoundedButton(label = "Log Out") {
                                logOutDialog.value = true
                            }
                            Spacer(modifier = Modifier.fillMaxWidth(0.4f))
                            if(logOutDialog.value) {
                                ShowAlertDialog(title = "Log Out", message = stringResource(id = R.string.log_out_sure) + "\n"+
                                        stringResource(id = R.string.action), logOutDialog) {
                                    Toast.makeText(context, "Logged Out Successfully!", Toast.LENGTH_SHORT).show()
                                    FirebaseAuth.getInstance().signOut().run {
                                        navController.navigate(KokoroListScreens.LoginScreen.route){
                                            popUpTo(KokoroListScreens.HomeScreen.route){
                                                inclusive = true
                                                saveState = true
                                            }
                                        }
                                    }
                                }
                            }
                            if(deleteDialog.value) {
                                ShowAlertDialog(title = "Delete Account Permanently", message = stringResource(id = R.string.sure) + "\n"+
                                        stringResource(id = R.string.action), deleteDialog) {
                                    viewModel.deleteUser(currentUser!!.uid)
                                }
                            }
                            RoundedButton(label = "Delete Account") {
                                deleteDialog.value = true
                            }
                        }

                        HorizontalDivider(color = highlightColor,
                            thickness = 1.dp,
                            modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp))
                        if(userStats.data.data!!.finishedAnimeList.isNullOrEmpty()){
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                LottieAnimation(
                                    composition = notFoundLottie,
                                    modifier = Modifier.size(200.dp),
                                    contentScale = ContentScale.Fit,
                                    iterations = LottieConstants.IterateForever
                                )
                                Text(text = "No anime completed yet",
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }else {
                            LazyColumn(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(userStats.data.data.finishedAnimeList.size) {
                                    AnimeRowStats(animeData = userStats.data.data.finishedAnimeList[it])
                                }
                            }
                        }

                    }
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimation(
                            composition = noInternetLottie,
                            modifier = Modifier.size(220.dp),
                            contentScale = ContentScale.Fit,
                            iterations = LottieConstants.IterateForever
                        )
                        Text(
                            text = userStats.message.toString(),
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }
            }

        }
        if (loadingActive) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimation(
                        composition = loadingLottie,
                        modifier = Modifier.size(220.dp),
                        contentScale = ContentScale.Fit,
                        iterations = LottieConstants.IterateForever
                    )
                    Text(
                        text = "Deleting user...",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AnimeRowStats(animeData: StatsAnime) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .height(196.dp)
        .padding(8.dp),
        colors = CardDefaults.cardColors(primaryColor),
        border = BorderStroke(1.dp, highlightColor),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)) {

        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top) {

            val imgUrl = if(!animeData.imageUrl.isNullOrEmpty())
                animeData.imageUrl
            else R.string.img404url

            ShimmerImage(imgUrl = imgUrl.toString(), modifier = Modifier
                .fillMaxWidth(0.35f)
                .fillMaxHeight()
                .padding(start = 6.dp, end = 4.dp, top = 4.dp, bottom = 4.dp))

            Column {
                Text(
                    text = animeData.title.toString(),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Studio: ${animeData.studio}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Started: ${animeData.startedWatching!!}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Finished: ${animeData.finishedWatching!!}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "MAL Score: ${animeData.malScore!!}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Genres: ${animeData.genres} ",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}