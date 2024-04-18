package com.ramiz.ethereumwallet.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ScreenNavigation(
    navigationCommand: NavigationDestination?,
    onNavigateUp: () -> Unit,
    onNavigateDone: () -> Unit,
    onNavigateToDestination: (NavigationDestination) -> Unit
) {
    LaunchedEffect(key1 = navigationCommand) {
        when (navigationCommand) {
            null -> {}
            is GeneralNavigationDestination.Back -> { onNavigateUp() }
            else -> { onNavigateToDestination(navigationCommand) }
        }
        onNavigateDone()
    }
}
