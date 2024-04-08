package com.example.skyeye.settings

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
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.skyeye.buildVersion

@Composable
fun AboutSettingsScreen(navController: NavController) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        val items = listOf(
            "Build version",
            "Privacy policy",
            "Content policy",
            "User Agreement"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navController, "About settings")
            AboutSettingsItems(navController, items)
        }
    }
}

@Composable
fun AboutSettingsItems(navController: NavController, items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {

        items.forEachIndexed { index, item ->
            AboutSettingsItem(navController,index, item)
        }
    }
}

@Composable
fun AboutSettingsItem(navController: NavController, index: Int, item: String) {
    val isClickable = item != "Build version"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isClickable) {
                    Modifier.clickable(onClick = {
                        //navController.navigate(item.lowercase())
                    })
                } else Modifier
            )
    ) {
        SettingsItemRow(item = item, isClickable = isClickable)
        if (index > 0) {
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun SettingsItemRow(item: String, isClickable: Boolean) {
    Row(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (item == "Build version") {
            BuildVersionItem(item = item)
        } else {
            RegularSettingsItem(item = item, isClickable = isClickable)
        }
    }
}

@Composable
fun BuildVersionItem(item: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 30.sp,
            modifier = Modifier
                .weight(2f)
                .padding(start = 16.dp)
        )
        Text(
            text = buildVersion,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            fontSize = 30.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)

        )
    }
}

@Composable
fun RegularSettingsItem(item: String, isClickable: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 30.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        if (isClickable) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "End Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}