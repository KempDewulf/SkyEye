package com.example.skyeye

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.skyeye.apirequest.ui.APIUiState
import com.example.skyeye.apirequest.ui.APIViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AirportDetailScreen(icao: String) {
    val apiViewModel: APIViewModel = viewModel()
    val apiUiState : APIUiState = apiViewModel.apiUiState

    LaunchedEffect(key1 = icao) {
        apiViewModel.getAirportData(icao)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = icao, style = MaterialTheme.typography.titleMedium)
        when(apiUiState)
        {
            is APIUiState.Success -> Text(text = apiUiState.quote, style = MaterialTheme.typography.bodyMedium)
            is APIUiState.Loading -> Text(text = "Airport information loading...", style = MaterialTheme.typography.bodyMedium)
            is APIUiState.Error -> Text(text = "Error!")
        }
    }
}