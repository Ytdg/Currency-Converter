package com.example.data_.repozitory

import com.example.data_.Currencies


interface ApiRepozytory {
    suspend fun getAllCurrencies(): ResponseRepozitory<Currencies>


}








