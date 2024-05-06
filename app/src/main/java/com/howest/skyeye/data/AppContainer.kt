package com.howest.skyeye.data

import android.content.Context

interface AppContainer {
    val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface by lazy {
        UserPreferencesRepository(UserPreferencesDatabase.getDatabase(context).userPreferencesDao())
    }
}
