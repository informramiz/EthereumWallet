package com.ramiz.ethereumwallet.presentation.transfereth

import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

data class TransferEthViewState(
    val isLoading: Boolean = true,
    val isTransactionInProgress: Boolean = false,
    val senderAddress: String = "",
    val currentBalance: Double = 0.0,
    val transactionHash: String? = null
) : ViewState
