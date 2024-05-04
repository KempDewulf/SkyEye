package com.howest.skyeye.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isDarkMode: Boolean
)