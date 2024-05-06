package com.howest.skyeye.ui.home.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.howest.skyeye.ui.home.HomeScreen
import howest.nma.skyeye.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(
        Triple(R.drawable.weather, "Check the weather", "weather"),
        Triple(R.drawable.camera, "Search airplanes with AR", "camera"),
        Triple(R.drawable.airplane, "See all aircraft types", "seeAllAircraftTypes"),
        Triple(R.drawable.runway, "See all airports", "seeAllAirports"),
        Triple(R.drawable.settings, "Settings", "settings")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = { DrawerContent(drawerState, scope, items, navController) },
        scrimColor = Color.Black.copy(alpha = 0.8f),
        content = { HomeScreen(drawerState = drawerState, scope = scope, navController = navController) }
    )
}

@Composable
fun DrawerContent(drawerState: DrawerState, scope: CoroutineScope, items: List<Triple<Int, String, String>>, navController: NavController) {
    ModalDrawerSheet(
        drawerShape = RectangleShape,
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
    ) {
        DrawerHeader(drawerState, scope, navController)
        DrawerItems(items, drawerState, scope, navController)
    }
}

@Composable
fun DrawerHeader(drawerState: DrawerState, scope: CoroutineScope, navController: NavController) {
    Spacer(Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { scope.launch { drawerState.close() } }) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("register")
                } }) {
                Text(text = "Register", fontSize = 22.sp)
            }
            Text(text = "or", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 22.sp)
            TextButton(onClick = {
                scope.launch {
                    drawerState.close()
                    navController.navigate("login")
                } }) {
                Text(text = "Log in", fontSize = 22.sp)
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 5.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
    Spacer(Modifier.height(18.dp))
}

@Composable
fun DrawerItem(
    iconId: Int,
    label: String,
    destination: String,
    navController: NavController,
    onClick: () -> Unit,
    bottomPadding: Dp = 0.dp // Default padding is 0.dp
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                painterResource(iconId),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        },
        label = {
            Text(
                label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        selected = false,
        onClick = {
            onClick()
            navController.navigate(destination)
        },
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
            .then(
                if (bottomPadding > 0.dp) {
                    Modifier.padding(bottom = bottomPadding)
                } else {
                    Modifier
                }
            )
    )
}

@Composable
fun DrawerItems(items: List<Triple<Int, String, String>>, drawerState: DrawerState, scope: CoroutineScope, navController: NavController) {
    Column {
        // Top items
        items.filter { it.second != "Settings" }.forEach { item ->
            DrawerItem(
                iconId = item.first,
                label = item.second,
                destination = item.third,
                navController = navController,
                onClick = { scope.launch { drawerState.close() } }
            )
        }

        // Spacer to separate top and bottom items
        Spacer(modifier = Modifier.weight(1f))

        // "Settings" item at the bottom with bottom padding
        items.find { it.second == "Settings" }?.let { settingsItem ->
            DrawerItem(
                iconId = settingsItem.first,
                label = settingsItem.second,
                destination = settingsItem.third,
                navController = navController,
                onClick = { scope.launch { drawerState.close() } },
                bottomPadding = 16.dp // Adjust the padding as needed
            )
        }
    }
}
