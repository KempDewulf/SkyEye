package com.example.skyeye.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }
    var isUserLoggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        val items = listOf(
            if (isUserLoggedIn) Triple("Account", "account",Icons.Rounded.AccountCircle)
            else Triple("Login or Sign up", "login" ,Icons.Rounded.AccountCircle),
            Triple("Appearance", "appearance",Icons.Rounded.Star),
            Triple("Support", "support",Icons.Rounded.Build),
            Triple("About", "about",Icons.Rounded.Info)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navController)
            SettingsItems(navController, items)
        }
    }
}

@Composable
fun SettingsTopBar(navController: NavController, title: String = "Settings") {
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
                text = title,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun SettingsItems(navController: NavController, items: List<Triple<String, String, ImageVector>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {

        items.forEachIndexed { index, (item, label, icon) ->
            SettingsItem(navController,index, item, label, icon)
        }
    }
}

@Composable
fun SettingsItem(navController: NavController, index: Int, item: String, label: String, icon: ImageVector) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(label)
            })
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = item,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = item,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 30.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "End Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(30.dp).padding(top = 5.dp)
            )
        }
        if (index > 0) {
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.fillMaxWidth())
        }
    }
}

























