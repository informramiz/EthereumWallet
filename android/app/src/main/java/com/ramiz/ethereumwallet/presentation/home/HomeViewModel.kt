package com.ramiz.ethereumwallet.presentation.home

import androidx.lifecycle.viewModelScope
import com.ramiz.ethereumwallet.data.EthereumRepository
import com.ramiz.ethereumwallet.data.UserRepository
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import com.ramiz.ethereumwallet.ui.screens.importwallet.ImportWalletNavigationDestination
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
            }
            .launchIn(viewModelScope)

    }

    private fun fetchWalletBalance(credentials: Credentials) {
        launchWithErrorHandling {
            val balance = ethereumRepository.getEthBalance(credentials.address)
            updateState {
                it.copy(
                    walletBalance = Convert.fromWei(balance.toBigDecimal(), Convert.Unit.ETHER)
                        .toDouble()
                )
            }
        }
    }

    fun onAddExistingWalletAction() {
        navigate(ImportWalletNavigationDestination)
    }

    fun onSendEthAction() {
        // TODO
    }

    fun onLogoutAction() {
        launchWithErrorHandling {
            userRepository.logout()
        }
    }
}
