package com.example.skyeye.settings

import android.widget.ToggleButton
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppearanceSettingsScreen(navController: NavController) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navController, "Appearance settings")
            AppearanceSettingsItems(navController, isDarkMode) { isDarkMode = it }
        }
    }
}

@Composable
fun AppearanceSettingsItems(navController: NavController, isDarkMode: Boolean, onDarkModeChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DarkModeSwitch(isDarkMode = isDarkMode, onDarkModeChange = onDarkModeChange)
        }
    }
}

@Composable
fun DarkModeSwitch(isDarkMode: Boolean, onDarkModeChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text(
                text = "Darkmode",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)

            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = onDarkModeChange,
                modifier = Modifier.size(30.dp).padding(top = 5.dp)
            )
        }
    }
}