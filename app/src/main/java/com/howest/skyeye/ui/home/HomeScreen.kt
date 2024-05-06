package com.howest.skyeye.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.home.bottombar.BottomBar
import com.howest.skyeye.ui.home.topbar.TopBar
import com.howest.skyeye.ui.map.MapView
import com.mapbox.mapboxsdk.camera.CameraPosition
import kotlinx.coroutines.CoroutineScope

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val title: String = "Home"
}

@Composable
fun HomeScreen(drawerState: DrawerState, scope: CoroutineScope, navController: NavController) {
    val selectedMapTypeSetting = remember { mutableStateOf("normal") }
    val cameraPositionState = remember { mutableStateOf<CameraPosition?>(null) }

    Scaffold(
        topBar = {
            TopBar(navController, drawerState, scope)
        },
        bottomBar = {
            BottomBar(navController, selectedMapTypeSetting)
        }
    ) { paddingValues ->
        MapView(
            modifier = Modifier.padding(paddingValues),
            latitude = 50.5,
            longitude = 4.47,
            context = LocalContext.current,
            showAirports = true,
            selectedMapTypeSetting = selectedMapTypeSetting,
            cameraPositionState = cameraPositionState,
            navController = navController
        )
    }
}
