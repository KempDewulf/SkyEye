package com.example.skyeye

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.skyeye.apirequest.ui.APIUiState
import com.example.skyeye.apirequest.ui.APIViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AirportDetailScreen(icao: String, airportName: String, navController: NavController) {
    val apiViewModel: APIViewModel = viewModel()
    val apiUiState : APIUiState = apiViewModel.apiUiState

    /*LaunchedEffect(key1 = icao) {
        apiViewModel.getAirportData(icao)
    }*/

    /*when(apiUiState)
        {
            is APIUiState.Success -> Text(text = apiUiState.quote, style = MaterialTheme.typography.bodyMedium)
            is APIUiState.Loading -> Text(text = "Airport information loading...", style = MaterialTheme.typography.bodyMedium)
            is APIUiState.Error -> Text(text = "Couldn't find the airport data, try again later!")
        }*/

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
                    text = "$airportName information",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            item {
                Section(title = airportName) {}
            }

            val details = listOf(
                "ICAO Code" to "OABT", //VARIABLE VALUE
                "IATA Code" to "BST", //VARIABLE VALUE
                "ICAO Region" to "APAC", //VARIABLE VALUE
                "ICAO Territory" to "Afghanistan", //VARIABLE VALUE
                "Location" to "Lashkar Gah, Helmand", //VARIABLE VALUE
                "Serving" to "Lashkar Gah", //VARIABLE VALUE
                "Elevation" to "2464 ft", //VARIABLE VALUE
                "Coordinates" to "31° 33' 37\" N , 64° 21' 53\" E", //VARIABLE VALUE
                "KCC" to "Bwh" //VARIABLE VALUE
            )

            items(details) { detail ->
                TitleValueComponent(title = detail.first, value = detail.second)
            }

            item {
                Section(title = "METAR") {
                    Text(
                        text = "METAR OIZB 091600Z 00000KT CAVOK 25/01 Q1014", //VARIABLE METAR
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
                    )
                }
            }

            item {
                Section(title = "MAP") {
                    MapView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        latitude = 31.56, //VARIABLE COORDS
                        longitude = 64.36, //VARIABLE COORDS
                        showCompass = false,
                        userInteractionEnabled = false,
                        zoomValue = 13.5,
                        styleUrl = "https://api.maptiler.com/maps/cadastre-satellite/style.json"
                    )
                }
            }
        }
    }
}

@Composable
fun TitleValueComponent(title: String, value: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
    )
    Text(
        text = value,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
    )
}

@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 20.dp))
        content()
    }
}