package com.example.currencyconverter.presentation.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Immutable
data class SingleCurrencyState(
    val value: String,
    val id: Int
)

enum class Load {
    Nothing, Load, Error, Successfully
}

@Immutable
data class CurrencyState(
    val currencies: ImmutableList<SingleCurrencyState> = emptyList<SingleCurrencyState>().toImmutableList(),
    val selectCurrency: SingleCurrencyState? = null,
    val load: Load = Load.Nothing
)

