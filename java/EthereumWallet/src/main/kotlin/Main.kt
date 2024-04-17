package org.example

import org.web3j.crypto.Bip39Wallet
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger

/**
 * NOTE: All calls made in this code are synchronous (not async) because this code is purely for
 * for learning purposes and async calls add boilerplate code to handle callbacks making code look
 * more complex
 */

private const val WALLET1_MNEMONIC = "suggest fence two upon hard use leg ankle garment mouse skin age"
private const val WALLET2_MNEMONIC = "collect library uphold filter step chef poverty whip purpose erupt hat popular"

private fun generateWallet(): Bip39Wallet {
    return WalletUtils.generateBip39Wallet("", File("./"))
}

private fun loadWalletCredentials(filePath: String, password: String = ""): Credentials {
    return WalletUtils.loadCredentials(password, filePath)
}

private fun loadBip39WalletCredentials(mnemonic: String, password: String = ""): Credentials {
    return WalletUtils.loadBip39Credentials(password, mnemonic)
}

private fun Web3j.ethBalanceByLatestBlock(address: String): BigInteger {
    return ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance ?: BigInteger.ZERO
}

private fun Web3j.sendFunds(sendCredentials: Credentials, receiverAddress: String, ethAmount: Double): TransactionReceipt {
    return Transfer.sendFundsEIP1559(
        /* web3j = */ this,
        /* credentials = */ sendCredentials,
        /* toAddress = */ receiverAddress,
        /* value = */ BigDecimal.valueOf(ethAmount),
        /* unit = */ Convert.Unit.ETHER,
        /* gasLimit = */ BigInteger.valueOf(8_000_000),
        /* maxPriorityFeePerGas = */ DefaultGasProvider.GAS_LIMIT,
        /* maxFeePerGas = */ BigInteger.valueOf(3_100_000_000L)
    ).send()
}

fun main() {
    val web3Client = Web3j.build(HttpService("https://eth-sepolia.g.alchemy.com/v2/EZayKWiz3QIslpoQeZn5I_hLaIrcfjr8"))

//    val createdWallet = generateWallet()
//    println("----> New Wallet Generated with following details")
//    println("FileName: ${createdWallet.filename}")
//    println("Mnemonic phrases: ${createdWallet.mnemonic}")

    val wallet1 = loadBip39WalletCredentials(mnemonic = WALLET1_MNEMONIC)
    println("Wallet1 Address: ${wallet1.address}, Balance: ${web3Client.ethBalanceByLatestBlock(wallet1.address).toEth()} Eth")

    val wallet2 = loadBip39WalletCredentials(mnemonic = WALLET2_MNEMONIC)
    println("Wallet2 Address: ${wallet2.address}, Balance: ${web3Client.ethBalanceByLatestBlock(wallet2.address).toEth()} Eth")

    println("Sending eth from wallet1 to Wallet2...")
    val transactionReceipt = web3Client.sendFunds(wallet1, wallet2.address,0.01)
    println(
        """
            ----> Transaction Successful
            Transaction id: ${transactionReceipt.transactionIndex}
            Transaction Hash: ${transactionReceipt.transactionHash}
            Transaction block Hash: ${transactionReceipt.blockHash}
            Transaction block number: ${transactionReceipt.blockNumber}
        """.trimIndent()
    )
    println("----> Updated Balances are below")
    println("Wallet1 Address: ${wallet1.address}, Balance: ${web3Client.ethBalanceByLatestBlock(wallet1.address).toEth()} Eth")
    println("Wallet2 Address: ${wallet2.address}, Balance: ${web3Client.ethBalanceByLatestBlock(wallet2.address).toEth()} Eth")
}

/**
 * Utility Kotlin extension functions
 */

private fun BigInteger.toEth() = Convert.fromWei(this.toBigDecimal(), Convert.Unit.ETHER)
private fun BigDecimal.toWei() = Convert.toWei(this, Convert.Unit.ETHER)