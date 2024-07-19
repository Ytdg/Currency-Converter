package com.example

import com.example.data_.ApiNetworkCurrency
import com.example.data_.apiKey
import com.example.data_.baseUrl
import com.example.data_.getInstanseApiNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ModuleData {
    @Provides
    @Singleton
    fun apiNetworkCurrency():ApiNetworkCurrency {
        return  getInstanseApiNetwork(baseUrl+ apiKey).create(ApiNetworkCurrency::class.java)
    }
}