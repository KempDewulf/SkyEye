package com.example.skyeye

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AirportsScreen(navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // Move the isExpanded variable here
    var expandedCountries by remember { mutableStateOf(setOf<String>()) }

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
                    text = "Airports information",
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
                    "EBBR",
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            },
            onValueChange = { searchText = it},
            label = { Text("Search ICAO code") },
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

        AirportList(navController, expandedCountries, searchText) { country -> expandedCountries = toggleCountry(expandedCountries, country) }
    }
}

@Composable
fun AirportList(navController: NavController, expandedCountries: Set<String>, searchText: String, onToggle: (String) -> Unit) {
    val airportsByCountry = mapOf(
        "Afghanistan" to Pair("AFG", listOf(
            AirportData("Bost Airport", "BST / OABT"),
            AirportData("Chaghcaran Airport", "CCN / OACC")
        )),
        "Albania" to Pair("ALB", listOf(
            AirportData("Rinas Airport", "LAT / LATI"),
            AirportData("Novsele Airport", "LAK / LAKU"),
            AirportData("Shtiqen Airport", "LVL / LAVL")
        )),
        "Algeria" to Pair("DZA", listOf(
            AirportData("Houari Boumediene Airport", "ALG / ALGA"),
            AirportData("Es Senia Airport", "ORN / ORNA"),
            AirportData("Ain Arnat Airport", "QSF / QSFA")
        ))
        // Add more countries and airports as needed
    )

    // Filter countries based on search text
    val filteredCountries = airportsByCountry.filterKeys { country ->
        country.contains(searchText, ignoreCase = true) ||
                airportsByCountry[country]?.second?.any { it.ICAO.contains(searchText, ignoreCase = true) } == true
    }

    LazyColumn {
        filteredCountries.forEach { (country, data) ->
            val (countryCode, airports) = data
            item {
                // Pass both countryCode and ICAO for each CountryItem
                CountryItem(country, countryCode, onClick = { onToggle(country) }, isExpanded = expandedCountries.contains(country))
            }

            if (expandedCountries.contains(country)) {
                // Filter airports based on search text
                val filteredAirports = airports.filter { it.ICAO.contains(searchText, ignoreCase = true) }
                items(filteredAirports) { airport ->
                    // Pass the NavController and the ICAO for each AirportItem
                    AirportItem(navController, airport.name, airport.ICAO.split(" / ").last())
                }
            }
        }
    }
}

data class AirportData(val name: String, val ICAO: String)

@Composable
fun CountryItem(country: String, countryCode: String, onClick: () -> Unit, isExpanded: Boolean) {
    // Clickable row for the country
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = countryCode, style = MaterialTheme.typography.labelSmall)

        // Content (Country name and toggle icon)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = country, style = MaterialTheme.typography.bodyMedium)
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
fun AirportItem(navController: NavController, airportName: String, ICAO: String) {
    // Each airport item under the country
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(start = 16.dp) // Add left padding for child items
            .clickable {
                navController.navigate("AirportDetailScreen/$ICAO/$airportName")
            },
    ) {
        // Label on top
        Text(text = ICAO, style = MaterialTheme.typography.labelSmall)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = airportName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun toggleCountry(expandedCountries: Set<String>, country: String): Set<String> {
    return if (expandedCountries.contains(country)) {
        expandedCountries - country
    } else {
        expandedCountries + country
    }
}