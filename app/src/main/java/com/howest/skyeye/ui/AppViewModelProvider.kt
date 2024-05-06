package com.howest.skyeye.ui


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.howest.skyeye.SkyEyeApplication
import com.howest.skyeye.ui.core.MainViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(SkyEyeApplication().container.userPreferencesRepositoryInterface)
        }
    }
}

fun CreationExtras.SkyEyeApplication(): SkyEyeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as SkyEyeApplication)
