package com.ramiz.ethereumwallet.presentation.createwallet

import com.ramiz.ethereumwallet.data.EthereumRepository
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateWalletViewModel @Inject constructor(
    private val ethereumRepository: EthereumRepository
) : ScreenBaseViewModel<CreateWalletViewState>() {
    override fun defaultViewState() = CreateWalletViewState()

    init {
        launchWithDefaultErrorHandling(
            onEndWithError = {
                updateState { it.copy(isLoading = false) }
            }
        ) {
            val wallet = ethereumRepository.generateNewWallet()
            updateState { it.copy(mnemonic = wallet.mnemonic, isLoading = false) }
        }
    }
}
