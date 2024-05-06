package com.howest.skyeye.data.userAccounts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.howest.skyeye.data.userPreferences.UserPreferences

@Database(entities = [UserPreferences::class], version = 2)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userAccountsDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getDatabase(context: Context):  UsersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java,
                    "users_database"
                )
                    .fallbackToDestructiveMigration() // Allow for destructive migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}