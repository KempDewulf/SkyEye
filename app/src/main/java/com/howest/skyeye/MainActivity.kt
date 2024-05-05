package com.howest.skyeye

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import howest.nma.skyeye.R
import androidx.navigation.navigation
import com.howest.skyeye.settings.AboutSettingsScreen
import com.howest.skyeye.settings.AccountSettingsScreen
import com.howest.skyeye.settings.AppearanceSettingsScreen
import com.howest.skyeye.settings.SettingsScreen
import com.howest.skyeye.settings.SupportSettingsScreen
import com.howest.skyeye.ui.theme.SkyEyeTheme
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.Manifest
import android.app.UiModeManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import com.howest.skyeye.workers.ReminderWorker
import androidx.work.WorkManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import java.util.concurrent.TimeUnit

var buildVersion = "0.2.0"

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                navController.navigate("camera")
            } else {
                navController.navigate("home")
            }
        }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            var isDarkMode by remember { mutableStateOf(uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES) }
            val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(2, TimeUnit.DAYS)
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniqueWork(
                "skyEyeReminder",
                ExistingWorkPolicy.REPLACE,
                reminderWorkRequest
            )

            SkyEyeTheme(darkTheme = isDarkMode) {
                navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController as NavHostController,
                        startDestination = "home",
                        enterTransition = {EnterTransition.None},
                        exitTransition = {ExitTransition.None}
                    ) {
                        composable("home") {
                            Drawer(navController = navController)
                        }
                        composable("login") {
                            LoginAndRegisterScreen(navController = navController, false, isDarkMode)
                        }
                        composable("register") {
                            LoginAndRegisterScreen(navController = navController, true, isDarkMode)
                        }
                        composable("forgotPassword") {
                            ForgotPasswordScreen(navController = navController, isDarkMode)
                        }
                        composable("camera") {
                            OpenCamera(navController)
                        }
                        composable("seeAllAircraftTypes") {
                            AircraftsScreen(navController)
                        }
                        composable("seeAllAirports") {
                            AirportsScreen(navController)
                        }
                        navigation(startDestination = "main", route = "settings") {
                            composable("main") {
                                SettingsScreen(navController)
                            }
                            composable("account") {
                                AccountSettingsScreen(navController)
                            }
                            composable("appearance") {
                                AppearanceSettingsScreen(navController, isDarkMode) { isDarkMode = it }
                            }
                            composable("support") {
                                SupportSettingsScreen(navController)
                            }
                            composable("About") {
                                AboutSettingsScreen(navController)
                            }
                        }
                        composable(
                            route = "AirportDetailScreen/{icao}/{airportName}",
                            arguments = listOf(
                                navArgument("icao") { type = NavType.StringType },
                                navArgument("airportName") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val icao = backStackEntry.arguments?.getString("icao")
                            val airportName = backStackEntry.arguments?.getString("airportName")
                            if (icao != null && airportName != null) {
                                AirportDetailScreen(icao, airportName, navController = navController)
                            }
                        }
                        composable(
                            route = "AircraftDetailScreen/{aircraftType}",
                            arguments = listOf(
                                navArgument("aircraftType") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getString("aircraftType")?.let { aircraftType ->
                                AircraftDetailScreen(aircraftType, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MainActivity.OpenCamera(navController: NavController) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) -> {
            CameraScreen(navController)
        } else -> {
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        }
    }
}



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
        content = { Homescreen(drawerState, scope, navController) }
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
                Icons.Rounded.ArrowBack,
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
    Divider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp,
        modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 5.dp)
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

@Composable
fun Homescreen(drawerState: DrawerState, scope: CoroutineScope, navController: NavController) {
    Scaffold(
        topBar = {
            TopBar(navController, drawerState, scope)
        },
        bottomBar = {
            BottomAppBar(navController)
        }
    ) { paddingValues ->
        // Use the contentPadding parameter to apply padding to the content
        MapView(
            modifier = Modifier.padding(paddingValues),
            latitude = 50.5,
            longitude = 4.47,
            context = LocalContext.current,
            showAirports = true
        )
    }
}

@Composable
fun TopBar(navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(Icons.Rounded.Menu, contentDescription = "menu", modifier = Modifier.size(32.dp))
        }
        IconButton(onClick = { navController.navigate("account") }) {
            Icon(Icons.Rounded.AccountCircle, contentDescription = "avatar", modifier = Modifier.size(36.dp))
        }
    }
}

@Composable
fun BottomAppBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(-1) }
    var activeModal by remember { mutableStateOf("") }
    val items = listOf(
        Pair(R.drawable.settings, "Settings"),
        Pair(R.drawable.weather, "Weather"),
        Pair(R.drawable.camera, "Camera"),
        Pair(R.drawable.map, "MapType"),
        Pair(R.drawable.filter, "Filters")
    )
    NavigationBar {
        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = icon), contentDescription = label) },
                label = { Text(label) },
                selected = activeModal == label,
                onClick = {
                    selectedItem = index
                    if (label in listOf("Weather", "MapType", "Filters")) {
                        activeModal = label
                    } else {
                        navController.navigate(label.lowercase())
                    }
                }
            )
        }
    }
    when (activeModal) {
        "Weather" -> WeatherModal { activeModal = "" }
        //"MapType" -> MapTypeModal { activeModal = "" }
        //"Filters" -> FiltersModal { activeModal = "" }
    }
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    latitude: Double,
    longitude: Double,
    showCompass: Boolean = true,
    userInteractionEnabled: Boolean = true,
    zoomValue: Double = 3.5,
    styleUrl: String = "https://api.maptiler.com/maps/basic-v2/style.json",
    context: Context,
    showAirports: Boolean = false
) {
    val airportData = remember { mutableStateOf<List<AirportMarkerData>?>(null) }

    LaunchedEffect(Unit) {
        airportData.value = readAirportData(context)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            Mapbox.getInstance(context)
            val mapView = com.mapbox.mapboxsdk.maps.MapView(context)
            mapView.onCreate(null)
            mapView.getMapAsync { map ->
                map.setStyle("$styleUrl?key=OZkqnFxcrUbHDpJQ5a3K") { style ->
                    map.uiSettings.isScrollGesturesEnabled = userInteractionEnabled
                    map.uiSettings.isZoomGesturesEnabled = userInteractionEnabled
                    map.uiSettings.isTiltGesturesEnabled = userInteractionEnabled
                    map.uiSettings.isRotateGesturesEnabled = userInteractionEnabled
                    map.uiSettings.setAttributionMargins(15, 0, 0, 15)
                    map.uiSettings.isCompassEnabled = showCompass
                    map.uiSettings.compassGravity = Gravity.BOTTOM or Gravity.START
                    map.uiSettings.setCompassMargins(40, 0, 0, 40)
                    map.uiSettings.isAttributionEnabled = false
                    map.uiSettings.isLogoEnabled = false
                    map.uiSettings.setCompassFadeFacingNorth(false)
                    map.cameraPosition = CameraPosition.Builder()
                        .target(LatLng(latitude, longitude))
                        .zoom(zoomValue)
                        .bearing(2.0)
                        .build()

                    if (showAirports) {
                        val drawable = ContextCompat.getDrawable(context, R.drawable.airport_marker)
                        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(bitmap)
                        drawable.setBounds(0, 0, canvas.width, canvas.height)
                        drawable.draw(canvas)
                        style.addImage("airport-icon", bitmap)

                        val featureCollection = FeatureCollection.fromFeatures(airportData.value?.map {
                            Feature.fromGeometry(Point.fromLngLat(it.longitude, it.latitude))
                        } ?: emptyList())

                        val source = GeoJsonSource("airport-source", featureCollection, GeoJsonOptions().withCluster(true))
                        style.addSource(source)

                        val unclustered = SymbolLayer("unclustered-points", "airport-source")
                        unclustered.setProperties(
                            PropertyFactory.iconImage("airport-icon"),
                            PropertyFactory.iconAllowOverlap(true),
                            PropertyFactory.iconIgnorePlacement(true)
                        )
                        style.addLayer(unclustered)

                        val countLayer = SymbolLayer("clustered-points-count", "airport-source")
                        countLayer.setProperties(
                            textField(Expression.toString(Expression.get("point_count"))),
                            textSize(12f),
                            textColor("white"),
                            textIgnorePlacement(true),
                            textAllowOverlap(true)
                        )
                        style.addLayer(countLayer)

                        val clustered = SymbolLayer("clustered-points", "airport-source")
                        clustered.setProperties(
                            PropertyFactory.iconImage("airport-icon"),
                            PropertyFactory.iconAllowOverlap(true),
                            PropertyFactory.iconIgnorePlacement(true)
                        )
                        clustered.setFilter(Expression.has("point_count"))
                        style.addLayer(clustered)
                    }
                }
            }
            mapView
        }
    )
}

fun readAirportData(context: Context): List<AirportMarkerData> {
    val airportData = mutableListOf<AirportMarkerData>()
    val inputStream = context.assets.open("airports.csv")
    val reader = inputStream.bufferedReader()
    reader.readLine()
    reader.forEachLine { line ->
        val fields = line.split(",")
        val latitude = fields[4].toDoubleOrNull()
        val longitude = fields[5].toDoubleOrNull()
        val name = fields[3]
        if (latitude != null && longitude != null) {
            airportData.add(AirportMarkerData(name, latitude, longitude))
        }
    }
    return airportData
}

data class AirportMarkerData(val name: String, val latitude: Double, val longitude: Double)