package com.ramiz.ethereumwallet.presentation.importwallet

import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

data class ImportWalletViewState(
    val isLoading: Boolean = false,
    val isImportWalletSuccessful: Boolean = false,
    val walletAddress: String? = null
) : ViewState
