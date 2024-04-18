package com.ramiz.ethereumwallet.presentation.core.navigation

sealed class GeneralNavigationDestination : NavigationDestination {
    object Back : GeneralNavigationDestination()
    object Profile: GeneralNavigationDestination()
}
