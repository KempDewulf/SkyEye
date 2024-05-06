package com.howest.skyeye.data.userAccounts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {
    //for single user insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: Users): Long

    //for list of users insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserAll(users: List<Users>): List<Long>

    //checking user exist or not in our db
    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    fun readLoginData(email: String, password: String):Users


    //getting user data details
    @Query("select * from users where id Like :id")
    fun getUserDataDetails(id:Long):Users

    //deleting all user from db
    @Query("DELETE FROM users")
    fun deleteAll()


}