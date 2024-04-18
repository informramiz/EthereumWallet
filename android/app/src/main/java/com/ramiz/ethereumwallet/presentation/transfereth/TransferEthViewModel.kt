package com.ramiz.ethereumwallet.presentation.transfereth

import com.ramiz.ethereumwallet.data.EthereumRepository
import com.ramiz.ethereumwallet.data.UserRepository
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransferEthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val ethereumRepository: EthereumRepository
) : ScreenBaseViewModel<TransferEthViewState>() {
    override fun defaultViewState() = TransferEthViewState()
}
