package com.howest.skyeye.settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.howest.skyeye.data.UserPreferences
import com.howest.skyeye.data.UserPreferencesRepository
import kotlinx.coroutines.launch

@Composable
fun AppearanceSettingsScreen(
    navController: NavController,
    userPreferencesRepository: UserPreferencesRepository,
    onDarkModeChange: (Boolean) -> Unit
) {
    val userPreferencesFlow = userPreferencesRepository.userPreferences
    val userPreferences by userPreferencesFlow.collectAsState(initial = UserPreferences(is_dark_mode = false))
    var isDarkMode by remember { mutableStateOf(userPreferences.is_dark_mode) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userPreferencesFlow) {
        userPreferencesFlow.collect { userPreferences ->
            isDarkMode = userPreferences?.is_dark_mode ?: false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingsTopBar(navController, "Appearance settings")
        AppearanceSettingsItems(
            navController = navController,
            isDarkMode = userPreferences?.is_dark_mode ?: false,
            onDarkModeChange = { newIsDarkMode ->
                isDarkMode = newIsDarkMode
                onDarkModeChange(newIsDarkMode)
                coroutineScope.launch {
                    val id = userPreferencesRepository.getLastInsertedId() ?: 0
                    val newUserPreferences = UserPreferences(id = id, is_dark_mode = newIsDarkMode)
                    if (id == 0) {
                        userPreferencesRepository.insertUserPreferences(newUserPreferences)
                    } else {
                        userPreferencesRepository.updateUserPreferences(newUserPreferences)
                    }
                }
            }
        )
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
                onCheckedChange = { isChecked ->
                    onDarkModeChange(isChecked)
                },
                modifier = Modifier.size(30.dp).padding(top = 5.dp)
            )
        }
    }
}