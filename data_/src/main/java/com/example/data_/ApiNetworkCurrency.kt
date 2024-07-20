package com.example.data_

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiNetworkCurrency {
    @GET("codes")
    fun getCodes(): Call<Currencies>
}

internal fun <T> getInstanseRetrofit(
    baseUrl: String,
    apiKey: String?,
    apiInterface: Class<T>
): T {
    val resUrl = if (apiKey == null) baseUrl else "$baseUrl$apiKey/"
    return Retrofit.Builder().baseUrl(resUrl).addConverterFactory(GsonConverterFactory.create())
        .build().create(apiInterface)

}




