package com.example.data_

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



 internal interface ApiNetworkCurrency {
    @GET("/codes")
    fun getCodes(): Response<Currency>
}

internal fun getInstanseApiNetwork(baseUrl: String): Retrofit {

    return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
        .build()

}




