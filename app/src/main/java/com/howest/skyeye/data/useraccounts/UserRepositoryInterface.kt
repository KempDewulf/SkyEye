package com.howest.skyeye.data.useraccounts

interface UserRepositoryInterface {
    suspend fun addUser(users: Users): Long

    suspend fun addUserList(users: List<Users>): List<Long>

    suspend fun deleteUser(users: Users)

    suspend fun verifyLoginUser(email: String, password: String): Users
    suspend fun getUserDataDetails(id: Long): Users
}