package com.ramiz.ethereumwallet.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.home.HomeViewModel
import com.ramiz.ethereumwallet.presentation.home.HomeViewState
import com.ramiz.ethereumwallet.ui.components.AppScaffold
import com.ramiz.ethereumwallet.ui.components.ScreenNavigation
import com.ramiz.ethereumwallet.ui.components.ScreenNotification
import com.ramiz.ethereumwallet.ui.screens.importwallet.ImportWalletNavigationDestination

@Composable
fun HomeScreen(
    onNavigateToImportWallet: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    AppScaffold(
        title = "Ethereum Wallet",
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
        navigationCommand = navigationCommand) { destination ->
        when (destination) {
            is ImportWalletNavigationDestination -> onNavigateToImportWallet()
        }
    }
}

@Composable
private fun ScreenUI(viewModel: HomeViewModel, viewState: HomeViewState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (!viewState.isWalletActive) {
            Button(onClick = { viewModel.onAddExistingWalletAction() }) {
                Text(text = "Add Existing Wallet")
            }
        } else {
            Text(
                text = """
                    Wallet Address: ${viewState.walletAddress}
                    Balance: ${viewState.walletBalance} Eth
                """.trimIndent()
            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Send Eth")
            }
            Button(onClick = { viewModel.onLogoutAction() }) {
                Text(text = "Logout")
            }
        }
    }
}
