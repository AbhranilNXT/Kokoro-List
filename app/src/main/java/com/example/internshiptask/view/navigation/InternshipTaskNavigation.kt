package com.example.internshiptask.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.internshiptask.view.screens.CreateAccountScreen
import com.example.internshiptask.view.screens.DetailsScreen
import com.example.internshiptask.view.screens.HomeScreen
import com.example.internshiptask.view.screens.LoginScreen
import com.example.internshiptask.view.screens.SearchScreen
import com.example.internshiptask.view.screens.SplashScreen
import com.example.internshiptask.view.screens.StatsScreen
import com.example.internshiptask.view.screens.UpdateScreen
import com.example.internshiptask.vm.AnimeSearchViewModel
import com.example.internshiptask.vm.HomeScreenViewModel

@Composable
fun InternshipTaskNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = InternshipTaskScreens.SplashScreen.route) {

        composable(InternshipTaskScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(InternshipTaskScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(InternshipTaskScreens.HomeScreen.route) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(InternshipTaskScreens.CreateAccountScreen.route) {
            CreateAccountScreen(navController = navController)
        }

        composable(InternshipTaskScreens.SearchScreen.route) {
            val searchViewModel = hiltViewModel<AnimeSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailName = InternshipTaskScreens.DetailsScreen.route
        composable("$detailName/{animeID}", arguments = listOf(navArgument("animeID"){
            type = NavType.IntType
        })) {backStackEntry ->
            backStackEntry.arguments?.getInt("animeID").let {
                if (it != null) {
                    DetailsScreen(navController = navController, animeId = it)
                }
            }
        }

        val updateName = InternshipTaskScreens.UpdateScreen.route
        composable("$updateName/{malId}", arguments = listOf(navArgument("malId"){
            type = NavType.StringType
        })) {backStackEntry ->
            backStackEntry.arguments?.getString("malId").let {
                if (it != null) {
                    UpdateScreen(navController = navController, malId = it.toString())
                }
            }
        }
        
        composable(InternshipTaskScreens.StatsScreen.route) {
            StatsScreen(navController = navController)
        }
    }
}