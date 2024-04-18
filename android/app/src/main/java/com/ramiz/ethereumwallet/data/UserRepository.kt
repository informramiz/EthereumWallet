package com.ramiz.ethereumwallet.data

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appSettings: AppSettings,
    private val ethereumRepository: EthereumRepository
) {
    suspend fun saveWallet(mnemonic: String) {
        appSettings.saveMnemonic(mnemonic)
    }

    fun getSavedWalletCredentials() = appSettings.mnemonic
        .map { mnemonic ->
            mnemonic?.let {
                ethereumRepository.importWallet(it)
            }
        }

    fun isWalletActive() = appSettings.isWalletActive

    suspend fun logout() = appSettings.clearSettings()
}
