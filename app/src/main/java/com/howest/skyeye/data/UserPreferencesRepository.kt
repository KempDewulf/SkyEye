package com.howest.skyeye.data

import kotlinx.coroutines.flow.Flow

class UserPreferencesRepository(private val userPreferencesDao: UserPreferencesDao) {
    val userPreferences: Flow<UserPreferences> = userPreferencesDao.getUserPreferences(0)

    suspend fun insertOrUpdateUserPreferences(userPreferences: UserPreferences) {
        userPreferencesDao.insertOrUpdateUserPreferences(userPreferences)
    }
}