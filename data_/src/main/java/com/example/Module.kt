package com.example

import com.example.data_.ApiNetworkCurrency
import com.example.data_.apiKey
import com.example.data_.baseUrl
import com.example.data_.getInstanseRetrofit
import com.example.data_.repozitory.ApiRepozytory
import com.example.data_.repozitory.Repozitory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleData {
    @Provides
    @Singleton
    internal fun apiNetworkCurrency(): ApiNetworkCurrency {
        return getInstanseRetrofit(baseUrl, apiKey,ApiNetworkCurrency::class.java)
    }

    @Provides
    @Singleton
    fun instanseRepozitory(repozitory: Repozitory): ApiRepozytory {
        return repozitory
    }
}







