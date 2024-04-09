package com.example.skyeye.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AccountSettingsScreen(navController: NavController) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navController, "Account settings")
            AccountPage(navController)
        }
    }
}

@Composable
fun AccountPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
    ) {
        Text(
            text = "Not logged in",
            fontSize = 34.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 300.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .size(150.dp, 50.dp)
                    .padding(end = 25.dp)
            ) {
                Text(
                    text = "Log in",
                    fontSize = 18.sp
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .size(150.dp, 50.dp)
                    .padding(start = 25.dp)
            ) {
                Text(
                    text = "Sign up",
                    fontSize = 18.sp
                )
            }
        }
    }
}