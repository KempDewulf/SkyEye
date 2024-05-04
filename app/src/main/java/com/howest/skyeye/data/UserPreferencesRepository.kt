package com.howest.skyeye.data

import androidx.lifecycle.LiveData

class UserPreferencesRepository(private val userPreferencesDao: UserPreferencesDao) {
    val userPreferences: LiveData<UserPreferences> = userPreferencesDao.getUserPreferences(0)

    suspend fun insertUserPreferences(userPreferences: UserPreferences) {
        userPreferencesDao.insertUserPreferences(userPreferences)
    }
}