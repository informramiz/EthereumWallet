package com.ramiz.ethereumwallet.presentation.home

import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

data class HomeViewState(
    val isLoading: Boolean = false,
    val isWalletActive: Boolean = false,
    val walletAddress: String? = null,
    val walletBalance: Double? = 0.0
) : ViewState
