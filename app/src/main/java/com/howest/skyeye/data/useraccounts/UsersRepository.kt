package com.howest.skyeye.data.useraccounts

class UsersRepository(private var userDao: UserDao) : UsersRepositoryInterface {

    override suspend fun addUser(user: User): Long {
        return userDao.insertUser(user)
    }

    override suspend fun addUserList(users:List<User>): List<Long> {
        return userDao.insertUserAll(users)
    }

    override suspend fun deleteUser(user: User) {
        //TODO("Not yet implemented")
    }

    override suspend fun verifyLoginUser(email:String, password:String): User {
        return userDao.readLoginData(email = email, password = password )
    }

    override suspend fun getUserDataDetails(id:Long): User {
        return userDao.getUserDataDetails(id)
    }
}