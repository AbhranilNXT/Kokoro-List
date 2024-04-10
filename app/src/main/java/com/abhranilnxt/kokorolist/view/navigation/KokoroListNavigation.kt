package com.abhranilnxt.kokorolist.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abhranilnxt.kokorolist.view.screens.ResetPasswordScreen
import com.abhranilnxt.kokorolist.view.screens.DetailsScreen
import com.abhranilnxt.kokorolist.view.screens.HomeScreen
import com.abhranilnxt.kokorolist.view.screens.LoginScreen
import com.abhranilnxt.kokorolist.view.screens.SearchScreen
import com.abhranilnxt.kokorolist.view.screens.SplashScreen
import com.abhranilnxt.kokorolist.view.screens.StatsScreen
import com.abhranilnxt.kokorolist.view.screens.UpdateScreen
import com.abhranilnxt.kokorolist.vm.AnimeSearchViewModel
import com.abhranilnxt.kokorolist.vm.HomeScreenViewModel

@Composable
fun KokoroListNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = KokoroListScreens.SplashScreen.route) {

        composable(KokoroListScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(KokoroListScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(KokoroListScreens.HomeScreen.route) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(KokoroListScreens.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }

        composable(KokoroListScreens.SearchScreen.route) {
            val searchViewModel = hiltViewModel<AnimeSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailName = KokoroListScreens.DetailsScreen.route
        composable("$detailName/{animeID}", arguments = listOf(navArgument("animeID"){
            type = NavType.IntType
        })) {backStackEntry ->
            backStackEntry.arguments?.getInt("animeID").let {
                if (it != null) {
                    DetailsScreen(navController = navController, animeId = it)
                }
            }
        }

        val updateName = KokoroListScreens.UpdateScreen.route
        composable("$updateName/{malId}", arguments = listOf(navArgument("malId"){
            type = NavType.StringType
        })) {backStackEntry ->
            backStackEntry.arguments?.getString("malId").let {
                if (it != null) {
                    UpdateScreen(navController = navController, malId = it.toString())
                }
            }
        }
        
        composable(KokoroListScreens.StatsScreen.route) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            StatsScreen(navController = navController, viewModel = homeViewModel)
        }
    }
}