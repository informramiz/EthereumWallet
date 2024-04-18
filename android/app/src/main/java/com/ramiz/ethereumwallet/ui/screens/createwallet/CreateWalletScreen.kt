package com.ramiz.ethereumwallet.ui.screens.createwallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.createwallet.CreateWalletViewModel
import com.ramiz.ethereumwallet.presentation.createwallet.CreateWalletViewState
import com.ramiz.ethereumwallet.ui.components.AppScaffold
import com.ramiz.ethereumwallet.ui.components.ScreenNavigation
import com.ramiz.ethereumwallet.ui.components.ScreenNotification

@Composable
fun CreateWalletScreen(
    onNavigateUp: () -> Unit,
    viewModel: CreateWalletViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    AppScaffold(
        title = "Create Wallet",
        navigationUp = { viewModel.navigateBack() }
    ) {
        if (viewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            ScreenUI(viewModel = viewModel, viewState = viewState)
        }
    }

    val notificationCommand by viewModel.notificationCommand.collectAsState()
    ScreenNotification(
        viewModel = viewModel,
        notificationCommand = notificationCommand
    )

    val navigationCommand by viewModel.navigationCommand.collectAsState()
    ScreenNavigation(
        viewModel = viewModel,
        onNavigateUp = onNavigateUp,
        navigationCommand = navigationCommand) { destination ->
        // TODO: Handle navigation
    }
}

@Composable
private fun ScreenUI(viewModel: CreateWalletViewModel, viewState: CreateWalletViewState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "New Wallet Created. Please copy the following phrases and store them in a safe place."
        )
        SelectionContainer {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewState.mnemonic.orEmpty(),
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
