package com.example.currencyconverter.presentation.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.presentation.Events
import com.example.currencyconverter.presentation.util.toStateCurrencies
import com.example.data_.repozitory.ApiRepozytory
import com.example.data_.repozitory.ResponseRepozitory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelCurrency @Inject constructor(private val apiRepozytory: ApiRepozytory) :
    ViewModel() {

    private val _stateCurrency: MutableState<CurrencyState> = mutableStateOf(
        CurrencyState()
    )
    val stateCurrency: State<CurrencyState> = _stateCurrency
    private var job: Job = Job()


    private fun <T> ResponseRepozitory<T>.action(
        success: ((T?) -> Unit)? = null,
        error: (() -> Unit)? = null
    ) {
        when (this) {
            is ResponseRepozitory.Successfully -> {

                success?.let { it(value) }
            }

            is ResponseRepozitory.Error -> {
                error?.let { it() }
            }
        }
    }

    fun onEvent(events: Events) {
        job.cancel()
        when (events) {
            is Events.GetCurrencies -> {
                _stateCurrency.value = CurrencyState(load = Load.Load)
                job = viewModelScope.launch {
                    delay(500)
                    val response = apiRepozytory.getAllCurrencies()
                    response.action(success = { currency ->
                        currency?.let {
                            _stateCurrency.value =
                                it.toStateCurrencies().copy(load = Load.Successfully)
                        }

                    }, error = {
                        _stateCurrency.value = CurrencyState(load = Load.Error)
                    })
                }
            }

            is Events.SelectCurrency -> {
                _stateCurrency.value =
                    _stateCurrency.value.copy(selectCurrency = events.currencyState)
            }
        }
    }
}