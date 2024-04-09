package com.example.skyeye.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AppearanceSettingsScreen(navController: NavController) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navController, "Appearance settings")

        }
    }
}