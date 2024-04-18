package com.ramiz.ethereumwallet.presentation.createwallet

import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

data class CreateWalletViewState(
    val isLoading: Boolean = true,
    val mnemonic: String? = null,
) : ViewState
