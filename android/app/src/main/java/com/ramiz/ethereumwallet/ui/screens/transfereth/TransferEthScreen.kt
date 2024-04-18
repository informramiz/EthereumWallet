package com.ramiz.ethereumwallet.ui.screens.transfereth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
private fun ScreenUI(viewModel: TransferEthViewModel, viewState: TransferEthViewState) {
    var receiverAddress by remember {
        mutableStateOf("")
    }
    var amount by remember {
        mutableDoubleStateOf(0.0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Your Wallet Address: ${viewState.senderAddress}")
        Text(text = "Your Current Balance: ${viewState.currentBalance}")

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = receiverAddress,
            onValueChange = { receiverAddress = it },
            label = { Text(text = "Receiver Address") },
            maxLines = 4,
            minLines = 2
        )

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = amount.toString(),
            onValueChange = { amount = it.toDouble() },
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            maxLines = 1
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { viewModel.onTransferEthAction(receiverAddress, amount) },
            enabled = !viewState.isTransactionInProgress
        ) {
            Text(text = "Transfer")
        }

        if (viewState.isTransactionInProgress) {
            Text(text = "Transaction in progress...")
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (viewState.transactionHash != null) {
            SelectionContainer {
                Text(text = "Transaction Successful with Hash: ${viewState.transactionHash}")
            }
        }
    }
}
