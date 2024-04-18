package com.ramiz.ethereumwallet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideWeb3j(): Web3j {
        // TODO: Move this to build config and local.properties
        return Web3j.build(HttpService("https://eth-sepolia.g.alchemy.com/v2/EZayKWiz3QIslpoQeZn5I_hLaIrcfjr8"))
    }
}
