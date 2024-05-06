package com.howest.skyeye.ui


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.howest.skyeye.SkyEyeApplication
import com.howest.skyeye.ui.core.MainViewModel
import com.howest.skyeye.ui.home.modals.weather.WeatherViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(SkyEyeApplication().container.userPreferencesRepositoryInterface)
        }
        initializer {
            WeatherViewModel()
        }
    }
}

fun CreationExtras.SkyEyeApplication(): SkyEyeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as SkyEyeApplication)
