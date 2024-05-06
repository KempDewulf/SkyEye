package com.howest.skyeye.data

import android.content.Context
import com.howest.skyeye.data.userpreferences.UserPreferencesDatabase
import com.howest.skyeye.data.userpreferences.UserPreferencesRepository
import com.howest.skyeye.data.userpreferences.UserPreferencesRepositoryInterface

interface AppContainer {
    val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface by lazy {
        UserPreferencesRepository(UserPreferencesDatabase.getDatabase(context).userPreferencesDao())
    }
}
