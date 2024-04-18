package com.ramiz.ethereumwallet.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.ramiz.ethereumwallet.ui.screens.createwallet.CreateWalletNavigationDestination
import com.ramiz.ethereumwallet.ui.screens.importwallet.ImportWalletNavigationDestination
import com.ramiz.ethereumwallet.ui.screens.transfereth.TransferEthNavigationDestination
import java.util.Locale

@Composable
fun HomeScreen(
    onNavigateToImportWallet: () -> Unit,
    onNavigateToTransferEth: () -> Unit,
    onNavigateToCreateWallet: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    AppScaffold(
        title = "Sepolia Ethereum Wallet",
        snackbarHostState = snackbarHostState
    ) {
        if (viewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            ScreenUI(
                viewModel = viewModel,
                viewState = viewState
            )
        }
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
            is TransferEthNavigationDestination -> onNavigateToTransferEth()
            is CreateWalletNavigationDestination -> onNavigateToCreateWallet()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenUI(viewModel: HomeViewModel, viewState: HomeViewState) {
    val pullToRefreshState = rememberPullRefreshState(refreshing = viewState.isRefreshing, onRefresh = { viewModel.refresh() })
    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullToRefreshState)
        .verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (!viewState.isWalletActive) {
                Button(onClick = { viewModel.onAddExistingWalletAction() }) {
                    Text(text = "Add Existing Wallet")
                }

                Button(onClick = { viewModel.onCreateNewWalletAction() }) {
                    Text(text = "Create New Wallet")
                }
            } else {
                WalletInfo(viewState)
                Button(onClick = { viewModel.onSendEthAction() }) {
                    Text(text = "Send Eth")
                }
                Button(onClick = { viewModel.onLogoutAction() }) {
                    Text(text = "Logout")
                }
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = viewState.isRefreshing,
            state = pullToRefreshState
        )
    }
}

@Composable
private fun WalletInfo(viewState: HomeViewState) {
    SelectionContainer {
        Column {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Wallet Address: ${viewState.walletAddress}",
            )
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Balance: ${
                    String.format(
                        Locale.US,
                        "%.5f",
                        viewState.walletBalance
                    )
                } Eth"
            )
        }
    }
}
