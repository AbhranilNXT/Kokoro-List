package com.abhranilnxt.kokorolist.view.navigation

sealed class KokoroListScreens(val route: String) {
    object SplashScreen : KokoroListScreens("splash_screen")
    object LoginScreen : KokoroListScreens("login_screen")
    object ResetPasswordScreen : KokoroListScreens("reset_password_screen")
    object HomeScreen : KokoroListScreens("home_screen")
    object SearchScreen : KokoroListScreens("search_screen")
    object DetailsScreen : KokoroListScreens("details_screen")
    object UpdateScreen : KokoroListScreens("update_screen")
    object StatsScreen : KokoroListScreens("stats_screen")
}