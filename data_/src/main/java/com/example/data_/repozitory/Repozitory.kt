package com.example.data_.repozitory

import android.util.Log
import com.example.data_.ApiNetworkCurrency
import com.example.data_.Currencies
import com.google.gson.JsonObject
import kotlinx.coroutines.internal.resumeCancellableWith
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Repozitory @Inject constructor(private val apiNetworkCurrency: ApiNetworkCurrency) :
    ApiRepozytory {


    private fun Response<*>.getMessageErrorBody(): String? {
        val jsonObject = errorBody()?.string()?.let { JSONObject(it) }
        var message: String? = null
        jsonObject.takeIf { it != null }?.let {
            message = it.getString("error-type")
        }
        return message
    }


    private suspend fun <T> Call<T>.getResponse(): ResponseRepozitory<T> =

        suspendCoroutine { continuation ->

            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {

                    response.takeIf { it.isSuccessful }?.let { responseType ->
                        responseType.body()?.let {
                            continuation.resume(ResponseRepozitory.Successfully(value = it))
                            return
                        }
                        continuation.resume(ResponseRepozitory.Successfully(value = null))
                        return
                    }
                    continuation.resume(ResponseRepozitory.Error(message = response.getMessageErrorBody()))
                    return
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resume(ResponseRepozitory.Error(message = t.message))
                    return
                }
            })

        }

    override suspend fun getAllCurrencies(): ResponseRepozitory<Currencies> {
        val call = apiNetworkCurrency.getCodes()
        return call.getResponse()
    }
}


sealed class ResponseRepozitory<T> {
    data class Successfully<T>(val value: T?) : ResponseRepozitory<T>()
    data class Error<T>(val message: String?) : ResponseRepozitory<T>()
}

