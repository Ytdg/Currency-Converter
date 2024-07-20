package com.example.currencyconverter.use_cases

import com.example.data_.repozitory.ApiRepozytory
import javax.inject.Inject

class UseCaseGetCurrencies @Inject constructor(val apiRepozytory: ApiRepozytory) {
    suspend operator fun invoke() {

    }
}