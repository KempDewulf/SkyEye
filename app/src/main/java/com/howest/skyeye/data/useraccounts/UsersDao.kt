package com.howest.skyeye.data.useraccounts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.howest.skyeye.data.useraccounts.Users

@Dao
interface UsersDao {
    //for single user insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users): Long

    //for list of users insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAll(users: List<Users>): List<Long>

    //checking user exist or not in our db
    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    suspend fun readLoginData(email: String, password: String): Users


    //getting user data details
    @Query("select * from users where id Like :id")
    suspend fun getUserDataDetails(id:Long): Users

    //deleting all user from db
    suspend @Query("DELETE FROM users")
    fun deleteAll()


}