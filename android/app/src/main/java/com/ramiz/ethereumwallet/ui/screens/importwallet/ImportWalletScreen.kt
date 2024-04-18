package com.ramiz.ethereumwallet.ui.screens.importwallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState
import com.ramiz.ethereumwallet.presentation.importwallet.ImportWalletViewModel
import com.ramiz.ethereumwallet.ui.components.AppScaffold
import com.ramiz.ethereumwallet.ui.components.ScreenNavigation
import com.ramiz.ethereumwallet.ui.components.ScreenNotification

@Composable
fun ImportWalletScreen(
    onNavigateUp: () -> Unit,
    viewModel: ImportWalletViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    AppScaffold(
        title = "Import Wallet",
        snackbarHostState = snackbarHostState
    ) {
        ScreenUI(viewModel = viewModel, viewState = viewState)
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
        onNavigateUp = onNavigateUp,
        navigationCommand = navigationCommand) { destination ->
        // TODO
    }
}

@Composable
private fun ScreenUI(viewModel: ImportWalletViewModel, viewState: ViewState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {  }) {
            Text(text = "Import")
        }
    }
}
