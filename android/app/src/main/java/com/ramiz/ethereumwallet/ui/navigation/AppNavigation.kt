package com.ramiz.ethereumwallet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramiz.ethereumwallet.ui.screens.AppScreen
import com.ramiz.ethereumwallet.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
        composable(route = AppScreen.Home.route) {
            HomeScreen()
        }
    }
}
