package com.example.data_

data class Currency(
    val result: String,
    val documentation: String,
    val supportedCodes: List<List<String>>,
    val termsOfUse: String
)