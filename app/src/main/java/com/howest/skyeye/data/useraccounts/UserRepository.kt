package com.howest.skyeye.data.useraccounts

class UserRepository(private var usersDao: UsersDao) : UserRepositoryInterface {

    override suspend fun addUser(users: Users): Long {
        return usersDao.insertUser(users)
    }

    override suspend fun addUserList(users:List<Users>): List<Long> {
        return usersDao.insertUserAll(users)
    }

    override suspend fun deleteUser(users: Users) {
        //TODO("Not yet implemented")
    }

    override suspend fun verifyLoginUser(email:String, password:String): Users {
        return usersDao.readLoginData(email = email, password = password )
    }

    override suspend fun getUserDataDetails(id:Long): Users {
        return usersDao.getUserDataDetails(id)
    }
}