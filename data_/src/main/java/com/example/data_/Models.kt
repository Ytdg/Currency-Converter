package com.example.data_

import com.google.gson.annotations.SerializedName

data class Currencies(
    @SerializedName("result") val result: String,
    @SerializedName("documentation") val documentation: String,
    @SerializedName("terms_of_use") val termsOfUse: String,
    @SerializedName("supported_codes") val supportedCodes: List<List<String>>,
)