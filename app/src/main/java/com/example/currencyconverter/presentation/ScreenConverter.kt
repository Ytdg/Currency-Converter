package com.example.currencyconverter.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.presentation.models.CurrencyState
import com.example.currencyconverter.presentation.models.Load
import com.example.currencyconverter.presentation.models.SingleCurrencyState
import com.example.currencyconverter.presentation.models.ViewModelCurrency


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExposedDropdownMenuCurrency(
    currencyState: State<CurrencyState>,
    onEvents: (Events) -> Unit,
    label: @Composable () -> Unit
) {

    val expanded = remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it;onEvents(Events.GetCurrencies) },
    ) {
        TextFieldMenuCurrency(
            modifier = Modifier
                .menuAnchor()
                .width(150.dp),
            expanded = expanded,
            selectedItem = currencyState.value.selectCurrency,
            label = label
        )
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .heightIn(50.dp, max = 170.dp)
                    .width(120.dp).background(Color.White)
            ) {
                when (currencyState.value.load) {
                    Load.Load -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.Gray)
                        }
                    }

                    Load.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "An error has occurred",color=Color.Gray, modifier = Modifier.padding(horizontal = 5.dp))
                        }
                    }

                    Load.Nothing -> {}
                    Load.Successfully -> {
                        currencyState.value.currencies.forEach { currency ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = currency.value,
                                        style = TextStyle(
                                            color = Color(40, 95, 112),
                                            textAlign = TextAlign.Center
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                onClick = {
                                    onEvents(Events.SelectCurrency(currency))
                                    expanded.value = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldInputCurrency(
    modifier: Modifier,
    readOnly: Boolean,
    label: (@Composable () -> Unit)? = null
) {
    val interation = remember {
        MutableInteractionSource()
    }
    Column {
        label?.let { it() }
        BasicTextField(interactionSource = interation,
            value = "10000",
            onValueChange = {},
            modifier = modifier,
            singleLine = true, readOnly = readOnly,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Right,
                fontSize = 24.sp
            ), decorationBox = { inner ->
                TextFieldDefaults.DecorationBox(
                    value = "10000",
                    innerTextField = inner,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interation,
                    colors = colorTextField().copy(
                        unfocusedContainerColor = Color(235, 235, 235),
                        focusedContainerColor = Color(235, 235, 235)
                    ),
                    shape = RoundedCornerShape(10.dp),
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
val colorTextField = @Composable {
    ExposedDropdownMenuDefaults.textFieldColors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent, focusedLabelColor = Color.Gray
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldMenuCurrency(
    modifier: Modifier,
    expanded: State<Boolean>,
    selectedItem: SingleCurrencyState? = null,
    label: @Composable () -> Unit
) {
    TextField(
        modifier = modifier,
        value = selectedItem?.value ?: "Select",
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        label = label,
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            color = Color(40, 95, 112),
            fontSize = 24.sp
        ),
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
        colors = colorTextField(),
    )
}

@Composable
fun ScreenCurrency(modifier: Modifier, vm: ViewModelCurrency = hiltViewModel()) {
    val stateCurrency = vm.stateCurrency
    val onEvent = remember {
        { event: Events -> vm.onEvent(event) }
    }
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            item(key = 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExposedDropdownMenuCurrency(label = {
                        Text(text = "Amount", color = Color.Gray)
                    }, onEvents = onEvent, currencyState = stateCurrency)
                    TextFieldInputCurrency(
                        modifier = Modifier
                            .width(140.dp)
                            .height(60.dp), readOnly = false,
                        label = {
                            Text(
                                text = "Value for exchange",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    )
                }
            }
            item {
                HorizontalDivider()
            }
            item(key = 2) {

            }

        }
    }
}

