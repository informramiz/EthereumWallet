package com.ramiz.ethereumwallet.ui.screens.transfereth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.transfereth.TransferEthViewModel
import com.ramiz.ethereumwallet.presentation.transfereth.TransferEthViewState
import com.ramiz.ethereumwallet.ui.components.AppScaffold
import com.ramiz.ethereumwallet.ui.components.ScreenNavigation
import com.ramiz.ethereumwallet.ui.components.ScreenNotification

@Composable
fun TransferEthScreen(onNavigateUp: () -> Unit, viewModel: TransferEthViewModel= hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()
    AppScaffold(
        title = "Transfer Eth",
    ) {
        ScreenUI(viewModel = viewModel, viewState = viewState)
    }

    val notificationCommand by viewModel.notificationCommand.collectAsState()
    ScreenNotification(
        viewModel = viewModel,
        notificationCommand = notificationCommand
    )

    val navigationCommand by viewModel.navigationCommand.collectAsState()
    ScreenNavigation(
        viewModel = viewModel,
        navigationCommand = navigationCommand) { destination ->
        // TODO: Handle navigation
    }
}

@Composable
private fun ScreenUI(viewModel: TransferEthViewModel, viewState: TransferEthViewState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Transfer Eth")
    }
}
