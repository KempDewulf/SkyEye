package com.example.skyeye

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
                Text(
                    text = airportName,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Divider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 20.dp))
            }

            item { TitleValueComponent(title = "ICAO Code", value = "OABT") }
            item { TitleValueComponent(title = "IATA Code", value = "BST") }
            item { TitleValueComponent(title = "ICAO Region", value = "APAC") }
            item { TitleValueComponent(title = "ICAO Territory", value = "Afghanistan") }
            item { TitleValueComponent(title = "Location", value = "Lashkar Gah, Helmand") }
            item { TitleValueComponent(title = "Serving", value = "Lashkar Gah") }
            item { TitleValueComponent(title = "Elevation", value = "2464 ft") }
            item { TitleValueComponent(title = "Coordinates", value = "31° 33' 37\" N , 64° 21' 53\" E") }
            item { TitleValueComponent(title = "KCC", value = "Bwh") }

            item {
                Text(
                    text = "METAR",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Divider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 20.dp))
                Text(
                    text = "METAR OIZB 091600Z 00000KT CAVOK 25/01 Q1014",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
                )
            }

            item {
                Text(
                    text = "MAP",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Divider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 20.dp))
                Text(
                    text = "MAP HERE",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
                )
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