package com.ramiz.ethereumwallet.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ramiz.ethereumwallet.presentation.core.navigation.GeneralNavigationDestination
import com.ramiz.ethereumwallet.presentation.core.navigation.NavigationDestination
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

@Composable
fun ScreenNavigation(
    navigationCommand: NavigationDestination?,
    onNavigateUp: () -> Unit = {},
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

@Composable
fun <V: ViewState> ScreenNavigation(
    viewModel: ScreenBaseViewModel<V>,
    navigationCommand: NavigationDestination?,
    onNavigateUp: () -> Unit = {},
    onNavigateToDestination: (NavigationDestination) -> Unit
) {
    LaunchedEffect(key1 = navigationCommand) {
        when (navigationCommand) {
            null -> {}
            is GeneralNavigationDestination.Back -> { onNavigateUp() }
            else -> { onNavigateToDestination(navigationCommand) }
        }
        viewModel.onNavigationDone()
    }
}
