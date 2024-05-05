package com.howest.skyeye.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val userPreferencesDao: UserPreferencesDao) {
    val userPreferences: Flow<UserPreferences> = userPreferencesDao.getLastUserPreferences().map { it ?: UserPreferences(is_dark_mode = false) }


    suspend fun insertUserPreferences(userPreferences: UserPreferences): Long {
        return userPreferencesDao.insertUserPreferences(userPreferences)
    }

    suspend fun updateUserPreferences(userPreferences: UserPreferences) {
        userPreferencesDao.updateUserPreferences(userPreferences)
    }

    suspend fun getLastInsertedId(): Int? {
        return userPreferencesDao.getLastInsertedId()
    }
}