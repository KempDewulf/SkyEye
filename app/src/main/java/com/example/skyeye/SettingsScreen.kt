package com.example.skyeye

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            // Add the top bar here
        }
    ) {paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            // Add your main content here
            Text("Settings Screen Content")
        }
    }
}