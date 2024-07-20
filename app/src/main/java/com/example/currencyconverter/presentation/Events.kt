package com.example.currencyconverter.presentation

import androidx.compose.runtime.Immutable
import com.example.currencyconverter.presentation.models.SingleCurrencyState

@Immutable
sealed class Events {
    data object GetCurrencies : Events()
    data class SelectCurrency(val currencyState: SingleCurrencyState) : Events()
}