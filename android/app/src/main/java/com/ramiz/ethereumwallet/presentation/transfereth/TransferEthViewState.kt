package com.ramiz.ethereumwallet.presentation.transfereth

import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

data class TransferEthViewState(
    val isLoading: Boolean = false,
    val senderAddress: String = "",
    val transactionHash: String? = null
) : ViewState
