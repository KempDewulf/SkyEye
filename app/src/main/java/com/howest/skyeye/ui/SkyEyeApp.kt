package com.howest.skyeye.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.howest.skyeye.ui.aircraft.detail.AircraftDetailScreen
import com.howest.skyeye.ui.aircraft.AircraftsScreen
import com.howest.skyeye.ui.airport.detail.AirportDetailScreen
import com.howest.skyeye.ui.airport.AirportsScreen
import com.howest.skyeye.ui.user.ForgotPasswordScreen
import com.howest.skyeye.ui.user.LoginAndRegisterScreen
import com.howest.skyeye.ui.camera.OpenCamera
import com.howest.skyeye.ui.home.HomeDestination
import com.howest.skyeye.ui.home.drawer.Drawer
import com.howest.skyeye.ui.settings.SettingsScreen
import com.howest.skyeye.ui.settings.about.AboutSettingsScreen
import com.howest.skyeye.ui.settings.account.AccountSettingsScreen
import com.howest.skyeye.ui.settings.appearance.AppearanceSettingsScreen
import com.howest.skyeye.ui.settings.support.SupportSettingsScreen
import com.howest.skyeye.ui.user.UserViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SkyEyeApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    SkyEyeNavHost(
        navController = navController,
        context = context
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SkyEyeNavHost(
    navController: NavHostController,
    context: Context
) {
    val userViewModel : UserViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
    ) {
        composable(HomeDestination.route) {
            Drawer(userViewModel = userViewModel, navController)
        }
        composable("login") {
            LoginAndRegisterScreen(userViewModel = userViewModel, navController = navController, isRegister = false)
        }
        composable("register") {
            LoginAndRegisterScreen(userViewModel = userViewModel, navController = navController, isRegister = true)
        }
        composable("forgotPassword") {
            ForgotPasswordScreen(navController = navController)
        }
        composable("camera") {
            OpenCamera(context, navController)
        }
        composable("seeAllAircraftTypes") {
            AircraftsScreen(navController)
        }
        composable("seeAllAirports") {
            AirportsScreen(navController)
        }
        navigation(startDestination = "main", route = "settings") {
            composable("main") {
                SettingsScreen(userViewModel = userViewModel, navController = navController)
            }
            composable("account") {
                AccountSettingsScreen(userViewModel = userViewModel, navController)
            }
            composable("appearance") {
                AppearanceSettingsScreen(navController)
            }
            composable("com/howest/skyeye/ui/settings/support") {
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