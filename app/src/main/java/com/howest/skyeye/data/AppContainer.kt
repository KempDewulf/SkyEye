package com.howest.skyeye.data

import android.content.Context
import com.howest.skyeye.data.useraccounts.UserRepository
import com.howest.skyeye.data.useraccounts.UserRepositoryInterface
import com.howest.skyeye.data.useraccounts.UsersDatabase

interface AppContainer {
    val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface
    val userRepositoryInterface: UserRepositoryInterface
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface by lazy {
        UserPreferencesRepository(UserPreferencesDatabase.getDatabase(context).userPreferencesDao())
    }
    override val userRepositoryInterface: UserRepositoryInterface by lazy {
        UserRepository(UsersDatabase.getDatabase(context).userAccountsDao())
    }
}
