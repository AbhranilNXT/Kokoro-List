package com.example.internshiptask.view.navigation

sealed class InternshipTaskScreens(val route: String) {
    object SplashScreen : InternshipTaskScreens("splash_screen")
    object LoginScreen : InternshipTaskScreens("login_screen")
    object CreateAccountScreen : InternshipTaskScreens("create_account_screen")
    object HomeScreen : InternshipTaskScreens("home_screen")
    object SearchScreen : InternshipTaskScreens("search_screen")
    object DetailsScreen : InternshipTaskScreens("details_screen")
    object UpdateScreen : InternshipTaskScreens("update_screen")
    object StatsScreen : InternshipTaskScreens("stats_screen")
}