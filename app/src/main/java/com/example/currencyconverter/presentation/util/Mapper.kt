package com.example.currencyconverter.presentation.util

import com.example.currencyconverter.presentation.models.CurrencyState
import com.example.currencyconverter.presentation.models.SingleCurrencyState
import com.example.data_.Currencies
import kotlinx.collections.immutable.toImmutableList

fun Currencies.toStateCurrencies(): CurrencyState {
    val listCurrencies = this.supportedCodes.mapIndexed { index, item ->
        SingleCurrencyState(
            value = item.first(),
            id = index
        )
    }.toImmutableList()
    return CurrencyState(currencies = listCurrencies)
}