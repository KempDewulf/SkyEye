package com.howest.skyeye

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModal(onDismissRequest: () -> Unit){
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier.height(550.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height(700.dp)
                .padding(bottom = 50.dp)
        ) {
            item {
                Text(
                    text = "Filters",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
            item {
                Text(
                    text = "Aircraft type",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 32.dp, top = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 48.dp, bottom = 32.dp, top = 16.dp)
                ) {
                    val checkedState1 = remember { mutableStateOf(true) }
                    CheckboxItem("General Aviation", checkedState1)

                    val checkedState2 = remember { mutableStateOf(true) }
                    CheckboxItem("Commercial", checkedState2)

                    val checkedState3 = remember { mutableStateOf(true) }
                    CheckboxItem("Military", checkedState3)
                }
            }
            item {
                HorizontalDivider(color = MaterialTheme.colorScheme.inverseOnSurface)
                val sliderState1 = remember { mutableStateOf(0f) }
                SliderItem("Min. altitude", sliderState1, 0f..50000f, trailingText = "ft")
                HorizontalDivider(color = MaterialTheme.colorScheme.inverseOnSurface)
                val sliderState2 = remember { mutableStateOf(0f) }
                SliderItem("Min. airspeed", sliderState2, 0f..800f, trailingText = "knots")
            }
        }
    }
}

@Composable
fun CheckboxItem(text: String, checkedState: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxHeight()
                .align(Alignment.CenterVertically),
        )
    }
}

@Composable
fun SliderItem(text: String, sliderState: MutableState<Float>, range: ClosedFloatingPointRange<Float>, trailingText: String) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp)
            .padding(top = 16.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "${(sliderState.value).roundToInt()} $trailingText",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Slider(
            value = sliderState.value,
            onValueChange = { sliderState.value = it },
            valueRange = range,
        )
    }
}