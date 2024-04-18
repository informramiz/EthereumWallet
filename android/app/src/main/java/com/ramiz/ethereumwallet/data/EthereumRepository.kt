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
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class EthereumRepository @Inject constructor(
    private val web3j: Web3j,
    @ApplicationContext
    private val context: Context,
    private val appSettings: AppSettings
) {
    suspend fun importWallet(mnemonic: String): Credentials {
        return withContext(Dispatchers.IO) {
            val wallet = WalletUtils.loadBip39Credentials("", mnemonic)
            appSettings.saveMnemonic(mnemonic)
            wallet
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
            val wallet = WalletUtils.generateBip39Wallet("", context.filesDir)
            appSettings.saveMnemonic(wallet.mnemonic)
            wallet
        }
    }

    fun isValidWalletAddress(address: String) = WalletUtils.isValidAddress(address)

    suspend fun transferEth(sendCredentials: Credentials, receiverAddress: String, ethAmount: Double): String? {
        return withContext(Dispatchers.IO) {
            Transfer.sendFundsEIP1559(
                /* web3j = */ web3j,
                /* credentials = */ sendCredentials,
                /* toAddress = */ receiverAddress,
                /* value = */ BigDecimal.valueOf(ethAmount),
                /* unit = */ Convert.Unit.ETHER,
                /* gasLimit = */ BigInteger.valueOf(8_000_000),
                /* maxPriorityFeePerGas = */ DefaultGasProvider.GAS_LIMIT,
                /* maxFeePerGas = */ BigInteger.valueOf(3_100_000_000L)
            ).send()?.transactionHash
        }
    }
}
