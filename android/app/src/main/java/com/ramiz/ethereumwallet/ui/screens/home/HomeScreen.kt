package com.ramiz.ethereumwallet.ui.screens.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.home.HomeViewModel
import com.ramiz.ethereumwallet.ui.components.AppScaffold
import com.ramiz.ethereumwallet.ui.components.ScreenNavigation
import com.ramiz.ethereumwallet.ui.components.ScreenNotification

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    AppScaffold(
        title = "Ethereum Wallet",
        snackbarHostState = snackbarHostState
    ) {
        Text(text = "Is Loading ${viewState.isLoading}")
    }

    val notificationCommand by viewModel.notificationCommand.collectAsState()
    ScreenNotification(
        viewModel = viewModel,
        snackbarHostState = snackbarHostState,
        notificationCommand = notificationCommand
    )

    val navigationCommand by viewModel.navigationCommand.collectAsState()
    ScreenNavigation(
        viewModel = viewModel,
        navigationCommand = navigationCommand) {
        // TODO
    }
}
