package com.howest.skyeye.ui.airport.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.howest.skyeye.apirequest.ui.APIUiStateAirportApiData
import com.howest.skyeye.apirequest.ui.APIViewModel
import com.howest.skyeye.ui.map.MapView
import howest.nma.skyeye.R


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AirportDetailScreen(icao: String, airportName: String, navController: NavController) {
    val apiViewModel: APIViewModel = viewModel()
    val apiUiState : APIUiStateAirportApiData = apiViewModel.apiUiState
    var details = emptyList<Pair<String, Any>>()
    val context = LocalContext.current
    var airportData: AirportApiData? = null
    var toast: Toast? = null
    val airportMapTypeSetting = remember { mutableStateOf("satellite") }

    LaunchedEffect(key1 = icao) {
        apiViewModel.getAirportData(icao)
    }
    when(apiUiState) {
        is APIUiStateAirportApiData.Success -> {
            airportData = apiUiState.data
            details = listOf(
                "ICAO Code" to airportData.ident,
                "IATA Code" to airportData.iata_code,
                "Continent" to airportData.continent,
                "Municipality" to airportData.municipality,
                "Location" to airportData.country.name,
                "Runway Count" to airportData.runways.size,
                "Elevation" to airportData.elevation_ft,
                "Coordinates" to "${airportData.latitude_deg} ${airportData.longitude_deg}"
            )
        }
        is APIUiStateAirportApiData.Loading -> Text(text = "Airport information loading...", style = MaterialTheme.typography.bodyMedium)
        is APIUiStateAirportApiData.Error -> Text(text = "Couldn't find the airport data, try again later!")
    }

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
                Section(title = "MAP", trailing = {
                    IconButton(onClick = {
                        val gmmIntentUri = Uri.parse("geo:${airportData?.latitude_deg},${airportData?.longitude_deg}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: ActivityNotFoundException) {
                            if (toast == null) {
                                toast = Toast.makeText(context, "No maps app available", Toast.LENGTH_SHORT)
                            }
                            toast?.show()
                        }
                    }) {
                        Icon(painterResource(id = R.drawable.google_maps_tile), contentDescription = "Open in Maps", modifier = Modifier.size(32.dp), tint = Color.Unspecified)
                    }
                }) {
                    if (airportData != null) {
                        MapView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            latitude = airportData.latitude_deg.toDouble(),
                            longitude = airportData.longitude_deg.toDouble(),
                            showCompass = false,
                            userInteractionEnabled = false,
                            zoomValue = 11.5,
                            selectedMapTypeSetting = airportMapTypeSetting,
                            showAirports = false,
                            context = LocalContext.current,
                            cameraPositionState = remember { mutableStateOf(null) },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TitleValueComponent(title: String, value: Any) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
    )
    Text(
        text = value.toString(),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 12.dp)
    )
}

@Composable
fun Section(title: String, trailing: @Composable (() -> Unit)? = null, content: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        trailing?.invoke()
    }
    HorizontalDivider(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 20.dp))
    content()
}

data class AirportApiData(
    val ident: String,
    val type: String,
    val name: String,
    val latitude_deg: Float,
    val longitude_deg: Float,
    val elevation_ft: String,
    val continent: String,
    val iso_country: String,
    val iso_region: String,
    val municipality: String,
    val scheduled_service: String,
    val gps_code: String,
    val iata_code: String,
    val local_code: String,
    val home_link: String,
    val wikipedia_link: String,
    val keywords: String,
    val icao_code: String,
    val runways: List<Runway>,
    val freqs: List<Freq>,
    val country: Country,
    val region: Region,
    val navaids: List<Navaid>,
    val updatedAt: String,
    val station: Station
)

data class Runway(
    val id: String,
    val airport_ref: String,
    val airport_ident: String,
    val length_ft: String,
    val width_ft: String,
    val surface: String,
    val lighted: String,
    val closed: String,
    val le_ident: String,
    val le_latitude_deg: String,
    val le_longitude_deg: String,
    val le_elevation_ft: String,
    val le_heading_degT: String,
    val le_displaced_threshold_ft: String,
    val he_ident: String,
    val he_latitude_deg: String,
    val he_longitude_deg: String,
    val he_elevation_ft: String,
    val he_heading_degT: String,
    val he_displaced_threshold_ft: String,
    val he_ils: Ils,
    val le_ils: Ils
)

data class Ils(
    val freq: Float,
    val course: Int
)

data class Freq(
    val id: String,
    val airport_ref: String,
    val airport_ident: String,
    val type: String,
    val description: String,
    val frequency_mhz: String
)

data class Country(
    val id: String,
    val code: String,
    val name: String,
    val continent: String,
    val wikipedia_link: String,
    val keywords: String
)

data class Region(
    val id: String,
    val code: String,
    val local_code: String,
    val name: String,
    val continent: String,
    val iso_country: String,
    val wikipedia_link: String,
    val keywords: String
)

data class Navaid(
    val id: String,
    val filename: String,
    val ident: String,
    val name: String,
    val type: String,
    val frequency_khz: String,
    val latitude_deg: String,
    val longitude_deg: String,
    val elevation_ft: String,
    val iso_country: String,
    val dme_frequency_khz: String,
    val dme_channel: String,
    val dme_latitude_deg: String,
    val dme_longitude_deg: String,
    val dme_elevation_ft: String,
    val slaved_variation_deg: String,
    val magnetic_variation_deg: String,
    val usageType: String,
    val power: String,
    val associated_airport: String
)

data class Station(
    val icao_code: String,
    val distance: Double
)