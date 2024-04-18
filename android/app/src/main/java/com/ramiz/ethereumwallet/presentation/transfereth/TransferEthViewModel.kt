package com.ramiz.ethereumwallet.presentation.transfereth

import androidx.lifecycle.viewModelScope
import com.ramiz.ethereumwallet.data.EthereumRepository
import com.ramiz.ethereumwallet.data.UserRepository
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.web3j.crypto.Credentials
import org.web3j.utils.Convert
import javax.inject.Inject

@HiltViewModel
class TransferEthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val ethereumRepository: EthereumRepository
) : ScreenBaseViewModel<TransferEthViewState>() {
    private lateinit var myCredentials: Credentials

    override fun defaultViewState() = TransferEthViewState()

    init {
        fetchData()
    }

    private fun fetchData() {
        updateState { it.copy(isLoading = true) }
        userRepository.getSavedWalletCredentials()
            .catchWithDefaultErrorHandler()
            .onEach { credentials ->
                updateState {
                    it.copy(
                        senderAddress = credentials!!.address
                    )
                }
                credentials?.let {
                    myCredentials = it
                    fetchWalletBalance(it)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchWalletBalance(credentials: Credentials) {
        launchWithDefaultErrorHandling {
            val balance = ethereumRepository.getEthBalance(credentials.address)
            updateState {
                it.copy(
                    currentBalance = Convert.fromWei(balance.toBigDecimal(), Convert.Unit.ETHER)
                        .toDouble(),
                    isLoading = false
                )
            }
        }
    }

    fun onTransferEthAction(receiverAddress: String, amount: Double) {
        if (!ethereumRepository.isValidWalletAddress(receiverAddress)) {
            notify("Invalid receiver address")
            return
        } else if (amount <= 0) {
            notify("Invalid amount")
            return
        } else if (amount >= currentViewState().currentBalance) {
            notify("Insufficient balance")
            return
        }

        updateState { it.copy(isTransactionInProgress = true) }
        launchWithDefaultErrorHandling(
            onEndWithError = {
                updateState { it.copy(isTransactionInProgress = false) }
            }
        ) {
            val transactionHash = ethereumRepository.transferEth(myCredentials, receiverAddress, amount)
            if (transactionHash != null) {
                notify("Transaction successful")
                updateState { it.copy(isTransactionInProgress = false, transactionHash = transactionHash) }
                fetchWalletBalance(myCredentials)
            } else {
                notify("Transaction failed")
                updateState { it.copy(isTransactionInProgress = false) }
            }
        }
    }
}
