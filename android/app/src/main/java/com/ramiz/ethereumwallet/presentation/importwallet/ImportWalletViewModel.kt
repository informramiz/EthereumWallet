package com.ramiz.ethereumwallet.presentation.importwallet

import com.ramiz.ethereumwallet.data.EthereumRepository
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImportWalletViewModel @Inject constructor(
    private val ethereumRepository: EthereumRepository
) : ScreenBaseViewModel<ImportWalletViewState>() {
    override fun defaultViewState() = ImportWalletViewState()

    fun onImportWallet(mnemonic: String) {
        if (mnemonic.isEmpty()) {
            notify("Invalid wallet input")
            return
        }

        launchWithErrorHandling {
            val wallet = ethereumRepository.importWallet(mnemonic)
            updateState { currentViewState ->
                currentViewState.copy(
                    isImportWalletSuccessful = true,
                    walletAddress = wallet.address
                )
            }
        }
    }
}
