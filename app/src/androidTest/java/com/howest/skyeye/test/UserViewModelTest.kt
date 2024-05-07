package com.howest.skyeye.test

import androidx.test.core.app.ApplicationProvider
import com.howest.skyeye.SkyEyeApplication
import com.howest.skyeye.data.AppDataContainer
import com.howest.skyeye.data.useraccounts.User
import com.howest.skyeye.ui.user.UserUiState
import com.howest.skyeye.ui.user.UserViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserViewModelTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var application: SkyEyeApplication
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()
        application.container = AppDataContainer(application)
        userViewModel = UserViewModel(application.container.usersRepositoryInterface)
        testScope = TestScope()
    }

    @Test
    fun login_with_correct_credentials_should_update_state() {
        val email = "test@test.com"
        val password = "password"
        val hashedPassword = userViewModel.hashPassword(password)
        val user = User(email = email, password = hashedPassword)

        testScope.launch {
            application.container.usersRepositoryInterface.addUser(user)
            userViewModel.login(email, password)



            assertEquals(UserUiState(email = email, password = hashedPassword, isLoggedIn = true, isLoading = false), userViewModel.userUiState.value)
        }
    }

    @Test
    fun register_with_valid_credentials_should_update_state() {
        val email = "test2@test.com"
        val password = "password"
        val hashedPassword = userViewModel.hashPassword(password)

        testScope.launch {
            userViewModel.register(email, password)



            assertEquals(UserUiState(email = email, password = hashedPassword, isLoggedIn = true, isLoading = false), userViewModel.userUiState.value)
        }
    }

    @Test
    fun register_with_existing_account_should_update_state_with_error() {
        val email = "test3@test.com"
        val password = "password"
        val hashedPassword = userViewModel.hashPassword(password)
        val user = User(email = email, password = hashedPassword)

        testScope.launch {
            // Add the user first to simulate an existing account
            application.container.usersRepositoryInterface.addUser(user)
            // Try to register again with the same email and password
            userViewModel.register(email, password)


            // Check if an error message is set in the UI state
            assertTrue(userViewModel.userUiState.value.error.isNotEmpty())
        }
    }

    @Test
    fun login_with_non_existing_account_should_update_state_with_error() {
        val email = "nonexisting@test.com"
        val password = "password"

        testScope.launch {
            // Try to login with a non-existing account
            userViewModel.login(email, password)



            // Check if an error message is set in the UI state
            assertTrue(userViewModel.userUiState.value.error.isNotEmpty())
        }
    }

    @Test
    fun login_with_wrong_password_should_update_state_with_error() {
        val email = "test4@test.com"
        val password = "password"
        val wrongPassword = "wrongpassword"
        val hashedPassword = userViewModel.hashPassword(password)
        val user = User(email = email, password = hashedPassword)

        testScope.launch {
            // Add the user first
            application.container.usersRepositoryInterface.addUser(user)
            // Try to login with a wrong password
            userViewModel.login(email, wrongPassword)



            assertTrue(userViewModel.userUiState.value.error.isNotEmpty())
        }
    }
}