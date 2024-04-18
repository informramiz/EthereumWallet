package com.ramiz.ethereumwallet.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Bip39Wallet
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import java.math.BigInteger
import javax.inject.Inject

class EthereumRepository @Inject constructor(
    private val web3j: Web3j,
    @ApplicationContext
    private val context: Context
) {
    suspend fun importWallet(mnemonic: String): Credentials {
        return withContext(Dispatchers.IO) {
            WalletUtils.loadBip39Credentials("", mnemonic)
        }
    }

    suspend fun getEthBalance(walletAddress: String): BigInteger {
        return withContext(Dispatchers.IO) {
            web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).send().balance
                ?: BigInteger.ZERO
        }
    }

    suspend fun generateNewWallet(): Bip39Wallet {
        return withContext(Dispatchers.IO) {
            WalletUtils.generateBip39Wallet("", context.filesDir)
        }
    }
}
