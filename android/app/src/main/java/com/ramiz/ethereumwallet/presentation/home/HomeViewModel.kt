package com.ramiz.ethereumwallet.presentation.home

import androidx.lifecycle.viewModelScope
import com.ramiz.ethereumwallet.data.EthereumRepository
import com.ramiz.ethereumwallet.data.UserRepository
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import com.ramiz.ethereumwallet.ui.screens.importwallet.ImportWalletNavigationDestination
import com.ramiz.ethereumwallet.ui.screens.transfereth.TransferEthNavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.web3j.crypto.Credentials
import org.web3j.utils.Convert
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val ethereumRepository: EthereumRepository
) : ScreenBaseViewModel<HomeViewState>() {

    override fun defaultViewState() = HomeViewState()

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
                        isWalletActive = credentials != null,
                        walletAddress = credentials?.address
                    )
                }
                credentials?.let { fetchWalletBalance(it) }
                if (credentials == null) {
                    updateState { it.copy(isLoading = false) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        updateState { it.copy(isRefreshing = true) }
        fetchData()
    }

    private fun fetchWalletBalance(credentials: Credentials) {
        launchWithDefaultErrorHandling {
            val balance = ethereumRepository.getEthBalance(credentials.address)
            updateState {
                it.copy(
                    walletBalance = Convert.fromWei(balance.toBigDecimal(), Convert.Unit.ETHER)
                        .toDouble(),
                    isLoading = false,
                    isRefreshing = false
                )
            }
        }
    }

    fun onAddExistingWalletAction() {
        navigate(ImportWalletNavigationDestination)
    }

    fun onSendEthAction() {
        navigate(TransferEthNavigationDestination)
    }

    fun onLogoutAction() {
        launchWithErrorHandling {
            userRepository.logout()
        }
    }
}
