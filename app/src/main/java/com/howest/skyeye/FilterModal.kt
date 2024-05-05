package com.howest.skyeye

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModal(onDismissRequest: () -> Unit){
    var expandedList by remember { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier.height(700.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
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
                ExpandableList(title = "Airline", expandedList = expandedList, onExpandedChange = { expandedList = it }, airlineItems)
                HorizontalDivider(color = MaterialTheme.colorScheme.inverseOnSurface)
                ExpandableList(title = "Aircraft", expandedList = expandedList, onExpandedChange = { expandedList = it }, aircraftItems)
                HorizontalDivider(color = MaterialTheme.colorScheme.inverseOnSurface)
                ExpandableList(title = "Airport", expandedList = expandedList, onExpandedChange = { expandedList = it }, airportItems)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableList(title: String, expandedList: String, onExpandedChange: (String) -> Unit, listItems: List<String>) {
    val expanded = expandedList == title
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .clickable { onExpandedChange(if (expanded) "" else title) }
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(expandFrom = Alignment.Top),
            exit = slideOutVertically() + shrinkVertically()
        ) {
            Column {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = {
                            Text(
                                text = "Search",
                                modifier = Modifier.padding(start = 12.dp))
                            },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Box(modifier = Modifier.heightIn(max = 150.dp)) {
                    LazyColumn {
                        val filteredItems = listItems.filter { it.contains(searchText, ignoreCase = true) }
                        items(filteredItems) { listItem ->
                            var checked by remember { mutableStateOf(false) }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { checked = !checked}
                                    .padding(horizontal = 16.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = { checked = it },
                                )
                                Text(text = listItem)
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
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

val airlineItems = listOf(
    "Brussels Airlines",
    "Lufthansa",
    "KLM",
    "Emirates",
    "British Airways",
    "Delta Air Lines"
)

val aircraftItems = listOf(
    "Boeing 737",
    "Airbus A320",
    "Boeing 787",
    "Airbus A380",
    "Embraer E190",
    "Bombardier CRJ900"
)

val airportItems = listOf(
    "KJFK",
    "EGLL",
    "OMDB",
    "KLAX",
    "LFPG",
    "WSSS"
)