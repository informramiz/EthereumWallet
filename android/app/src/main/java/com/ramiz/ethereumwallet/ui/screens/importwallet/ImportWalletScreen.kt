package com.ramiz.ethereumwallet.ui.screens.importwallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.importwallet.ImportWalletViewModel
import com.ramiz.ethereumwallet.presentation.importwallet.ImportWalletViewState
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
        snackbarHostState = snackbarHostState,
        navigationUp = { viewModel.navigateBack() }
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
private fun ScreenUI(viewModel: ImportWalletViewModel, viewState: ImportWalletViewState) {
    var mnemonicValue by remember {
        mutableStateOf("")
    }

    val onMnemonicValueChange = { newValue: String ->
        mnemonicValue = newValue
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = mnemonicValue,
            onValueChange = onMnemonicValueChange,
            minLines = 2,
            maxLines = 6
        )
        Button(onClick = { viewModel.onImportWallet(mnemonicValue) }) {
            Text(text = "Import")
        }

        if (viewState.isImportWalletSuccessful) {
            Text(text = "Import Wallet Successful")
            Text(text = "Wallet Address: ${viewState.walletAddress}")
        }
    }
}
