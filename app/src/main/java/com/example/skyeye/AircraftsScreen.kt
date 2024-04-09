package com.example.skyeye

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AircraftsScreen(navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // Move the isExpanded variable here
    var expandedAircrafts by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "Airframe information",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        var searchText by remember { mutableStateOf("") }

        // Search bar
        OutlinedTextField(
            value = searchText,
            placeholder = {
                Text(
                    "A320",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            },
            onValueChange = { searchText = it},
            label = { Text("Search airplane type") },
            leadingIcon = {
                Icon(Icons.Rounded.Search, contentDescription = "Filter")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    // Here we have to filter the text etc.
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        AircraftList(navController, expandedAircrafts, searchText) { aircraft -> expandedAircrafts = toggleAircraftType(expandedAircrafts, aircraft) }
    }
}

@Composable
fun AircraftList(navController: NavController, expandedAircrafts: Set<String>, searchText: String, onToggle: (String) -> Unit) {
    val aircraftsByType = mapOf(
        "A220" to ManufacturerAndAircraft("Airbus", listOf(
            AircraftData("A220-100"),
            AircraftData("A220-300")
        )),
        "A300" to ManufacturerAndAircraft("Airbus", listOf(
            AircraftData("A300-600"),
        )),
        "A320" to ManufacturerAndAircraft("Airbus", listOf(
            AircraftData("A320-200"),
        ))
    )

    // Filter countries based on search text
    val filteredAircrafts = aircraftsByType.filterKeys { aircraftType ->
        aircraftType.contains(searchText, ignoreCase = true) ||
                aircraftsByType[aircraftType]?.aircraft?.any { it.name.contains(searchText, ignoreCase = true) } == true
    }

    LazyColumn {
        filteredAircrafts.forEach { (type, data) ->
            val manufacturer = data.manufacturer

            item {
                AircraftTypeItem(manufacturer, type, onClick = { onToggle(type) }, isExpanded = expandedAircrafts.contains(type))
            }

            if (expandedAircrafts.contains(type)) {
                val filteredAircrafts = data.aircraft.filter { it.name.contains(searchText, ignoreCase = true) }
                items(filteredAircrafts) { aircraft ->
                    AircraftItem(navController, aircraft.name)
                }
            }
        }
    }
}

data class AircraftData(val name: String)
data class ManufacturerAndAircraft(
    val manufacturer: String,
    val aircraft: List<AircraftData>
)

@Composable
fun AircraftTypeItem(manufacturer: String, aircraftType: String, onClick: () -> Unit, isExpanded: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = manufacturer, style = MaterialTheme.typography.labelSmall)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = aircraftType, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun AircraftItem(navController: NavController, aircraftType: String) {
    // Each airport item under the country
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(start = 16.dp) // Add left padding for child items
            .clickable {
                navController.navigate("AircraftDetailScreen/$aircraftType")
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = aircraftType, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

private fun toggleAircraftType(expandedAircrafts: Set<String>, type: String): Set<String> {
    return if (expandedAircrafts.contains(type)) {
        expandedAircrafts - type
    } else {
        expandedAircrafts + type
    }
}