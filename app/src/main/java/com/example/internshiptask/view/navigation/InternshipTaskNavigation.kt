package com.example.internshiptask.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internshiptask.view.screens.CreateAccountScreen
import com.example.internshiptask.view.screens.DetailsScreen
import com.example.internshiptask.view.screens.HomeScreen
import com.example.internshiptask.view.screens.LoginScreen
import com.example.internshiptask.view.screens.SearchScreen
import com.example.internshiptask.view.screens.SplashScreen
import com.example.internshiptask.view.screens.StatsScreen
import com.example.internshiptask.view.screens.UpdateScreen

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
            HomeScreen(navController = navController)
        }

        composable(InternshipTaskScreens.CreateAccountScreen.route) {
            CreateAccountScreen(navController = navController)
        }

        composable(InternshipTaskScreens.SearchScreen.route) {
            SearchScreen(navController = navController)
        }

        composable(InternshipTaskScreens.DetailsScreen.route) {
            DetailsScreen(navController = navController)
        }

        composable(InternshipTaskScreens.UpdateScreen.route) {
            UpdateScreen(navController = navController)
        }
        
        composable(InternshipTaskScreens.StatsScreen.route) {
            StatsScreen(navController = navController)
        }
    }
}