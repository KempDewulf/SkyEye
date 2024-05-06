package com.howest.skyeye.data

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepositoryInterface {
    val userPreferences: Flow<UserPreferences>
    suspend fun insertUserPreferences(userPreferences: UserPreferences): Long
    suspend fun updateUserPreferences(userPreferences: UserPreferences)
    suspend fun getLastInsertedId(): Int?
}