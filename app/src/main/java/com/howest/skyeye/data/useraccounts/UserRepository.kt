package com.howest.skyeye.data.userAccounts

class UserRepository(private  var usersDao: UsersDao){

    suspend fun addUser(users:Users): Long {
        return usersDao.insertUser(users)
    }

    suspend fun addUserList(users:List<Users>): List<Long> {
        return usersDao.insertUserAll(users)
    }

    suspend fun deleteUser(users: Users) {
        //TODO("Not yet implemented")
    }

    suspend fun verifyLoginUser(email:String,password:String): Users {
        return usersDao.readLoginData(email = email, password = password )
    }

    suspend fun getUserDataDetails(id:Long): Users {
        return usersDao.getUserDataDetails(id)
    }
}