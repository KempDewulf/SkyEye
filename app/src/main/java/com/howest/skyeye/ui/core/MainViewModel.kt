package com.howest.skyeye.ui.core

import androidx.lifecycle.ViewModel
import com.howest.skyeye.data.UserPreferences
import com.howest.skyeye.data.UserPreferencesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface) : ViewModel() {
    private val _mainUiState = MutableStateFlow(MainUiState(isDarkMode = false))
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    suspend fun toggleDarkMode() {
        val current = _mainUiState.value
        _mainUiState.value = current.copy(isDarkMode = !current.isDarkMode)

        val id = userPreferencesRepositoryInterface.getLastInsertedId() ?: 0
        val newUserPreferences = UserPreferences(id = id, is_dark_mode = _mainUiState.value.isDarkMode)

        if (id == 0) {
            userPreferencesRepositoryInterface.insertUserPreferences(newUserPreferences)
        } else {
            userPreferencesRepositoryInterface.updateUserPreferences(newUserPreferences)
        }
    }
}