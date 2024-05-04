package com.howest.skyeye.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserPreferences::class], version = 1)
abstract class UserPreferencesDatabase : RoomDatabase() {

    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        @Volatile
        private var INSTANCE: UserPreferencesDatabase? = null

        fun getDatabase(context: Context): UserPreferencesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserPreferencesDatabase::class.java,
                    "user_preferences_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}